package th.co.itmx.bo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifySlipResponse {

	@SerializedName("responseCode")
	@Expose
	private String responseCode;
	@SerializedName("sendingBank")
	@Expose
	private String sendingBank;
	@SerializedName("transRef")
	@Expose
	private String transRef;
	@SerializedName("transDate")
	@Expose
	private String transDate;
	@SerializedName("transTime")
	@Expose
	private String transTime;
	@SerializedName("fromProxyIDOrAccountNo")
	@Expose
	private String fromProxyIDOrAccountNo;
	@SerializedName("receivingID")
	@Expose
	private String receivingID;
	@SerializedName("proxyType")
	@Expose
	private String proxyType;
	@SerializedName("receivingBank")
	@Expose
	private String receivingBank;
	@SerializedName("toAccountNo")
	@Expose
	private String toAccountNo;
	@SerializedName("toAccountDisplayName")
	@Expose
	private String toAccountDisplayName;
	@SerializedName("toAccountName")
	@Expose
	private String toAccountName;
	@SerializedName("transAmount")
	@Expose
	private String transAmount;
	@SerializedName("transFeeAmount")
	@Expose
	private String transFeeAmount;
	@SerializedName("billReference1")
	@Expose
	private String billReference1;
	@SerializedName("billReference2")
	@Expose
	private String billReference2;
	@SerializedName("billReference3")
	@Expose
	private String billReference3;

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getSendingBank() {
		return sendingBank;
	}

	public void setSendingBank(String sendingBank) {
		this.sendingBank = sendingBank;
	}

	public String getTransRef() {
		return transRef;
	}

	public void setTransRef(String transRef) {
		this.transRef = transRef;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getFromProxyIDOrAccountNo() {
		return fromProxyIDOrAccountNo;
	}

	public void setFromProxyIDOrAccountNo(String fromProxyIDOrAccountNo) {
		this.fromProxyIDOrAccountNo = fromProxyIDOrAccountNo;
	}

	public String getReceivingID() {
		return receivingID;
	}

	public void setReceivingID(String receivingID) {
		this.receivingID = receivingID;
	}

	public String getProxyType() {
		return proxyType;
	}

	public void setProxyType(String proxyType) {
		this.proxyType = proxyType;
	}

	public String getReceivingBank() {
		return receivingBank;
	}

	public void setReceivingBank(String receivingBank) {
		this.receivingBank = receivingBank;
	}

	public String getToAccountNo() {
		return toAccountNo;
	}

	public void setToAccountNo(String toAccountNo) {
		this.toAccountNo = toAccountNo;
	}

	public String getToAccountDisplayName() {
		return toAccountDisplayName;
	}

	public void setToAccountDisplayName(String toAccountDisplayName) {
		this.toAccountDisplayName = toAccountDisplayName;
	}

	public String getToAccountName() {
		return toAccountName;
	}

	public void setToAccountName(String toAccountName) {
		this.toAccountName = toAccountName;
	}

	public String getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}

	public String getTransFeeAmount() {
		return transFeeAmount;
	}

	public void setTransFeeAmount(String transFeeAmount) {
		this.transFeeAmount = transFeeAmount;
	}

	public String getBillReference1() {
		return billReference1;
	}

	public void setBillReference1(String billReference1) {
		this.billReference1 = billReference1;
	}

	public String getBillReference2() {
		return billReference2;
	}

	public void setBillReference2(String billReference2) {
		this.billReference2 = billReference2;
	}

	public String getBillReference3() {
		return billReference3;
	}

	public void setBillReference3(String billReference3) {
		this.billReference3 = billReference3;
	}

}
