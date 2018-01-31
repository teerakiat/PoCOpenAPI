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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import th.co.itmx.bo.VerifySlipRequest;

@Controller
@RequestMapping(value = "/auth")
public class SignatureController {
	private static final Logger logger = LoggerFactory.getLogger(SignatureController.class);
	
	String itmxKey = "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" + 
			"PSkg0Oq0NR1Ei9YhDLl7d0K/Kohy";
	
	String bankCertificate =
			"MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEArouICxhSv/I1sUxgH/Uv" + 
			"YOZ16Bp8t/uzjloRGbgN32vWNVS6yQpVHzDnZ+nrHclJMAhzZz7q9xioMpuBTC1t" + 
			"Ya5S24VzB0yD7GIAzHmJbqlrC6Gy79nfBU9xlOs2JnHrmdsNYiV3vu6xQ5hmZxl9" + 
			"cw3futs8IXVd34MVtyB2SxVcccZm3G96Ds7WfHea20CVCIvXZ2q0zkEUSQU8s2Pv" + 
			"SJrhf/X5WnYtg18ag4VlxZPT2b3vMV4XRDwq9zUpKphAofyki4WqQDC7QTxhQBpT" + 
			"Buer3dDOsofNiisSfk3jJ3AgEbyVdvnr9igN1CiqQpewmzW520foUIsnOL7CdKfs" + 
			"RBsaWrN3Bq60nKa7gNw2Up+LQV7dmZb+Gb7Ziet5xfGhc2vhF7klMp7jK2Lm5fCj" + 
			"PZytd7Dt4SF3tuqaRVPT0VILsMnrW75dLrrd9VGmlZYZDC3fblW1hu87VYgW+ja9" + 
			"FJoVjsjTk+Vnn9YsQ3f2X7GNFxLgvP1LZPxrC2/AB5F3G4KDQmyJODNpG59zByls" + 
			"2dWU01Un8LRIEQ4t1dVkxSHhMpK7KE6GYRHJ0Qfhi/tOv5su2IV2rLrzeCvZrHaF" + 
			"p2bZwmV+bOa3MP0HBTi+7whKhgXY0u/uu5d63/JpmBUGCwtTyM5YEMzKuJ3DmdJS" + 
			"sTMkDjxxxcVfiTN0PfI5uS0CAwEAAQ==";
	String signAlgorithm = "SHA512withRSA";

	@RequestMapping(value = "/{bankId}/sign", method = RequestMethod.POST, produces = { "application/json" })
	public ResponseEntity<String> sign(@RequestBody(required = false) String content) {
		try {
			PrivateKey priKey = getPrivateKeyFromString(itmxKey);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Signature", sign(content, priKey));
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

			
			X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(Base64.getDecoder().decode(bankCertificate));
			KeyFactory kf = KeyFactory.getInstance("RSA");
			PublicKey pub = kf.generatePublic(pubSpec);

			verify(pub, signatureStr, content);
			return new ResponseEntity<String>("", HttpStatus.OK);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED);
		}
	}

	/// Verify signature
	private void verify(PublicKey publicKey, String signatureStr, String message) throws Exception {
		Signature sign = Signature.getInstance(signAlgorithm);
		sign.initVerify(publicKey);
		sign.update(message.getBytes("UTF-8"));
		sign.verify(Base64.getDecoder().decode(signatureStr.getBytes("UTF-8")));
	}

	/// Sign signature
	private RSAPrivateKey getPrivateKeyFromString(String key) throws IOException, GeneralSecurityException {
		String privateKeyPEM = key;
		byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
		RSAPrivateKey privKey = (RSAPrivateKey) kf.generatePrivate(keySpec);
		return privKey;
	}

	public String sign(String message, PrivateKey pri) throws NoSuchAlgorithmException, SignatureException, IOException,
			InvalidKeySpecException, InvalidKeyException {
		Signature signature = Signature.getInstance(signAlgorithm);
		signature.initSign(pri);
		signature.update(message.getBytes());
		byte[] signatureBytes = signature.sign();
		String encodedSignature = Base64.getEncoder().encodeToString(signatureBytes);
		return encodedSignature;
	}

}
