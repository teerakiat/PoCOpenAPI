package th.co.itmx;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import th.co.itmx.bo.VerifySlipRequest;
import th.co.itmx.bo.VerifySlipResponse;
import th.co.itmx.util.HttpUtil;

@RestController
@RequestMapping(value = "/bank")
public class BankController {

	@RequestMapping(value = "/verifyslip/pullmode", method = RequestMethod.POST , produces={"application/json"})
	//public @ResponseBody VerifySlipResponse verifySlip(@RequestBody VerifySlipRequest verifySlipRequest) {
	public ResponseEntity<String> verifySlip(@RequestBody VerifySlipRequest verifySlipRequest) {
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
		
		return new ResponseEntity<String>(HttpUtil.objectToJson(response), HttpStatus.OK);

	}
}
