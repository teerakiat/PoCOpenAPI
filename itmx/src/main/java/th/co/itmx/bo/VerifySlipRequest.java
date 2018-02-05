package th.co.itmx.bo;

public class VerifySlipRequest {
	private String routingDestBank;
	private String sendingBank;
	private String transRef;
	public String getRoutingDestBank() {
		return routingDestBank;
	}
	public void setRoutingDestBank(String routingDestBank) {
		this.routingDestBank = routingDestBank;
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
	
	
}
