package eu.erbs.kik.rest.model;

public class Features {
	
	private boolean manuallySendReadReceipts;
	private boolean receiveReadReceipts;
	private boolean receiveDeliveryReceipts;
	private boolean receiveIsTyping;
	
	public boolean isManuallySendReadReceipts() {
		return manuallySendReadReceipts;
	}
	public void setManuallySendReadReceipts(boolean manuallySendReadReceipts) {
		this.manuallySendReadReceipts = manuallySendReadReceipts;
	}
	public boolean isReceiveReadReceipts() {
		return receiveReadReceipts;
	}
	public void setReceiveReadReceipts(boolean receiveReadReceipts) {
		this.receiveReadReceipts = receiveReadReceipts;
	}
	public boolean isReceiveDeliveryReceipts() {
		return receiveDeliveryReceipts;
	}
	public void setReceiveDeliveryReceipts(boolean receiveDeliveryReceipts) {
		this.receiveDeliveryReceipts = receiveDeliveryReceipts;
	}
	public boolean isReceiveIsTyping() {
		return receiveIsTyping;
	}
	public void setReceiveIsTyping(boolean receiveIsTyping) {
		this.receiveIsTyping = receiveIsTyping;
	}
	
	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("manuallySendReadReceipts: " + manuallySendReadReceipts);
		buffer.append("\t");
		buffer.append("receiveReadReceipts: " + receiveReadReceipts);
		buffer.append("\t");
		buffer.append("receiveDeliveryReceipts: " + receiveDeliveryReceipts);
		buffer.append("\t");
		buffer.append("receiveIsTyping: " + receiveIsTyping);
		return buffer.toString();
	}
}
