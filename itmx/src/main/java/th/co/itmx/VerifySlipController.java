package th.co.itmx;

import javax.servlet.http.HttpServletRequest;

import org.junit.runner.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import th.co.itmx.config.AppConfig;
import th.co.itmx.util.HttpUtil;
import th.co.itmx.util.SignatureAuthen;

/**
 * Handles requests for the application home page.
 */
@RestController
@RequestMapping(value = "/verifyslip")
public class VerifySlipController {
	
	private static final Logger logger = LoggerFactory.getLogger(VerifySlipController.class);
	
	@Autowired
	private AppConfig appConfig;
	
	@RequestMapping(value = "/v1.0/participant/{bankId}/**", produces={"application/json"})
	public ResponseEntity<String> Switch(
			@PathVariable String bankId, 
			@RequestBody(required=false) String content,  
			HttpServletRequest request) throws Exception {
		
		String pathInfo = "/bank/verifyslip/"+HttpUtil.getWildcardPath(request);
		
		HttpMethod httpMethod = HttpMethod.resolve(request.getMethod());
		
		
		String bankUrl = appConfig.getUrl(bankId) + pathInfo;
		
		//verify source bank signature
		String signatureStr = request.getHeader(SignatureAuthen.signatureHeaderName);
		try {
			if(!SignatureAuthen.verify(bankId, signatureStr, content)) {
				return new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		//sign with itmx signature
		try {
			String itmxSignature = SignatureAuthen.sign("itmx", content);
			headers.set(SignatureAuthen.signatureHeaderName, itmxSignature);
//			logger.info("itmx signature: "+ itmxSignature);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		try {
			logger.info("calling to "+bankUrl);
			HttpEntity<String> targetRequest = new HttpEntity<String>(content, headers);
			
			ResponseEntity<String> response = restTemplate.exchange(bankUrl, httpMethod, targetRequest,  String.class);
			
			//verify response signature
			signatureStr = response.getHeaders().get(SignatureAuthen.signatureHeaderName).get(0);
			String responseBody = response.getBody();
//			
			logger.info("response: " + responseBody);
			if(!SignatureAuthen.verify(bankId, signatureStr, responseBody)) {
//				logger.info("bank signature:" + signatureStr);
				return new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED);
			}
			

			HttpHeaders ResponseHeaders = new HttpHeaders();
			ResponseHeaders.add(SignatureAuthen.signatureHeaderName, SignatureAuthen.sign("itmx", responseBody));
			
			return new ResponseEntity<String>(responseBody, ResponseHeaders, HttpStatus.OK);
				
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//GET URL
//			ResponseEntity<String> response = restTemplate.getForEntity("https://gturnquist-quoters.cfapps.io/api/random", 
//					 String.class);
//			return new ResponseEntity<String>(response.getBody(), HttpStatus.OK);
//		
	}
	
}
