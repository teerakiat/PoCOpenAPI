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
			HttpServletRequest request) {
		
		String pathInfo = "/itmx/bank/verifyslip/"+HttpUtil.getWildcardPath(request);
		
		HttpMethod httpMethod = HttpMethod.resolve(request.getMethod());
		String bankUrl = appConfig.getUrls().get(bankId) + pathInfo;
		logger.info("calling to "+bankUrl);
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> targetRequest = new HttpEntity<String>(content, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(bankUrl, httpMethod, targetRequest,  String.class);
		return new ResponseEntity<String>(response.getBody(), HttpStatus.OK);
			
		//GET URL
//			ResponseEntity<String> response = restTemplate.getForEntity("https://gturnquist-quoters.cfapps.io/api/random", 
//					 String.class);
//			return new ResponseEntity<String>(response.getBody(), HttpStatus.OK);
//		
	}
	
}
