package th.co.itmx;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import th.co.itmx.util.SignatureAuthen;

@Controller
@RequestMapping(value = "/auth")
public class SignatureController {
	private static final Logger logger = LoggerFactory.getLogger(SignatureController.class);

	@RequestMapping(value = "/{bankId}/sign", method = RequestMethod.POST, produces = { "application/json" })
	public ResponseEntity<String> sign(@RequestBody(required = false) String content, @PathVariable String bankId) {
		try {
			
			HttpHeaders headers = new HttpHeaders();
			String signature = SignatureAuthen.sign(bankId, content);
			
			headers.add("Signature", SignatureAuthen.sign(bankId, content));
			
			logger.info("sign content: "+content);
			logger.info("sign signature: "+ signature);
			
			return new ResponseEntity<String>(headers, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/{bankId}/verify", method = RequestMethod.POST, produces = { "application/json" })
	public ResponseEntity<String> verify(@RequestBody(required = false) String content, HttpServletRequest request) {
		try {
			String signatureStr = request.getHeader("Signature");
			logger.info("verify content: "+content);
			logger.info("verify signature: "+signatureStr);
			
			if(SignatureAuthen.verify(signatureStr, content)) {
				return new ResponseEntity<String>("", HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
