package th.co.itmx;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import th.co.itmx.bo.VerifySlipRequest;
import th.co.itmx.bo.VerifySlipResponse;
import th.co.itmx.util.HttpUtil;
import th.co.itmx.util.SignatureAuthen;

/*
 * Mocking bank - using the same private key
 * */
@RestController
@RequestMapping(value = "/bank")
public class BankController {

	private static final Logger logger = LoggerFactory.getLogger(BankController.class);
	
	@RequestMapping(value = "/verifyslip/pullmode", method = RequestMethod.POST, produces = { "application/json" })
	// public @ResponseBody VerifySlipResponse verifySlip(@RequestBody
	// VerifySlipRequest verifySlipRequest) {
	public ResponseEntity<String> verifySlip(
			@RequestBody String content,
			HttpServletRequest request) {
		try {
//			//verify signature
			
			String signatureStr = request.getHeader(SignatureAuthen.signatureHeaderName);
		
			if(!SignatureAuthen.verify("itmx", signatureStr, content)) {
				logger.info("reject from bank...");
				return new ResponseEntity<String>("reject from bank", HttpStatus.UNAUTHORIZED);
			}
			
			//seralize json to object
			ObjectMapper mapper = new ObjectMapper();
			VerifySlipRequest verifySlipRequest = mapper.readValue(content, VerifySlipRequest.class);
					
			//mock response
			VerifySlipResponse response = new VerifySlipResponse();
			response.setResponseCode("000");
			response.setSendingBank(verifySlipRequest.getSendingBank());
			response.setTransRef(verifySlipRequest.getTransRef());
			response.setTransDate("10:10");
			response.setFromProxyIDOrAccountNo("1234567890");
			response.setReceivingID("8888888");
			response.setProxyType("NATID");
			response.setReceivingBank(verifySlipRequest.getRoutingDestBank());
			response.setToAccountNo("12345678");
			response.setToAccountDisplayName("Teerakiat");
			response.setToAccountName("Teerakiat");
			response.setTransAmount("200");
			response.setTransFeeAmount("0.25");
			response.setBillReference1("ref1");
			response.setBillReference2("ref2");
			response.setBillReference3("ref3");

			HttpHeaders headers = new HttpHeaders();
			String responseContent = HttpUtil.objectToJson(response);
			String responseSignature = SignatureAuthen.sign("bankA", responseContent);
			
			headers.add(SignatureAuthen.signatureHeaderName, responseSignature);
			logger.info(responseContent);
			
			return new ResponseEntity<String>(responseContent, headers, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
