package eu.erbs.kik.rest.model;

public class BotConfiguration {
	
	private String webhook;
	private Features features;
	
	public String getWebhook() {
		return webhook;
	}
	public void setWebhook(String webhook) {
		this.webhook = webhook;
	}
	public Features getFeatures() {
		return features;
	}
	public void setFeatures(Features features) {
		this.features = features;
	}
	
	@Override
	public String toString(){
		return webhook + "\t" + features.toString();
	}
}