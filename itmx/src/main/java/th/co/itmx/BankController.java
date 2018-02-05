package th.co.itmx;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

	@RequestMapping(value = "/verifyslip/pullmode", method = RequestMethod.POST, produces = { "application/json" })
	// public @ResponseBody VerifySlipResponse verifySlip(@RequestBody
	// VerifySlipRequest verifySlipRequest) {
	public ResponseEntity<String> verifySlip(@RequestBody VerifySlipRequest verifySlipRequest) {
		try {
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
			String content = HttpUtil.objectToJson(response);

			headers.add("Signature", SignatureAuthen.sign("bankA", content));

			return new ResponseEntity<String>(HttpUtil.objectToJson(response), headers, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
