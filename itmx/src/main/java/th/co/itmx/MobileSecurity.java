package th.co.itmx;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import th.co.itmx.bo.RefCodeRequest;
import th.co.itmx.bo.RefCodeResponse;
import th.co.itmx.bo.VerifySlipRequest;
import th.co.itmx.util.HttpUtil;
import th.co.itmx.util.SignatureAuthen;

@RestController
@RequestMapping(value = "/mobilesecurity")
public class MobileSecurity {


	private static final Logger logger = LoggerFactory.getLogger(VerifySlipController.class);
	

	@RequestMapping(value = "/v1.0/participant/{bankId}/refcode/verification", produces={"application/json"}, method=RequestMethod.POST)
	public ResponseEntity<String> Switch(@PathVariable String bankId, 
			@RequestBody(required=false) String content,  
			HttpServletRequest request) throws Exception{
			try {
				//verify source bank signature
				String signatureStr = request.getHeader(SignatureAuthen.signatureHeaderName);
				
				if(!SignatureAuthen.verify(bankId, signatureStr, content)) {
					return new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED);
				}

				//seralize json to object
				ObjectMapper mapper = new ObjectMapper();
				RefCodeRequest refcodeRequest = mapper.readValue(content, RefCodeRequest.class);
				
				RefCodeResponse response = new RefCodeResponse();
				response.setResult(true);
				response.setId(refcodeRequest.getId());
				response.setMobileNumber(refcodeRequest.getMobileNumber());
				response.setCurrentDate(refcodeRequest.getCurrentDate());
				String responseContent = HttpUtil.objectToJson(response);
						
				String itmxSignature = SignatureAuthen.sign("itmx", responseContent);
				HttpHeaders headers = new HttpHeaders();
				headers.set(SignatureAuthen.signatureHeaderName, itmxSignature);
				return new ResponseEntity<String>(responseContent, headers, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
	}
}
