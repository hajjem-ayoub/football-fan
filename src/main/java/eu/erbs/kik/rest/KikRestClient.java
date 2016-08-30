package eu.erbs.kik.rest;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.erbs.kik.rest.model.BotConfiguration;
import eu.erbs.kik.rest.model.Features;
import eu.erbs.kik.rest.model.Message;
import eu.erbs.kik.utils.KikException;

public class KikRestClient {

	private final static String KIK_CONFIGURATION_URL = "https://api.kik.com/v1/config";
	private final static String KIK_MESSAGE_URL = "https://api.kik.com/v1/message";

	private final static String WEBHOOK = "http://localhost:8080/FootballFan/rest/message";
//	https://api.kik.com/v1/message

	private final static String KIK_BOT_USERNAME = "KIK_BOT_USERNAME";
	private final static String KIK_BOT_API_KEY = "KIK_BOT_API_KEY";

	private final static Logger logger = LoggerFactory.getLogger(KikRestClient.class);

	public void setConfiguration() throws KikException
	{
		try {
			logger.info("Set configuration");

			BotConfiguration botConfiguration = new BotConfiguration();
			botConfiguration.setWebhook(WEBHOOK);
			Features features = new Features();
			botConfiguration.setFeatures(features);

			ObjectMapper mapper = new ObjectMapper();
			HttpPost request = new HttpPost(KIK_CONFIGURATION_URL);
			request.setEntity(new StringEntity(mapper.writeValueAsString(botConfiguration)));

			getKikResponse(request);
		} catch (IOException e) {
			throw new KikException(e);
		}
	}

	public void getConfiguration() throws KikException
	{
		try {
			logger.info("Get configuration");

			HttpGet request = new HttpGet(KIK_CONFIGURATION_URL);

			HttpResponse response = getKikResponse(request);

			ObjectMapper mapper = new ObjectMapper();
			BotConfiguration botConfiguration = mapper.readValue(EntityUtils.toString(response.getEntity()), BotConfiguration.class);
			logger.info(botConfiguration.toString());
		} catch (IOException e) {
			throw new KikException(e);
		}
	}

	private HttpResponse getKikResponse(HttpPost request) throws KikException {
		try {
			addAuthentication(request);

			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(request);

			checkResponse(response);

			return response;
		} catch (IOException e) {
			throw new KikException(e);
		}
	}

	private HttpResponse getKikResponse(HttpGet request) throws KikException {
		try {
			addAuthentication(request);

			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(request);

			checkResponse(response);

			return response;
		} catch (IOException e) {
			throw new KikException(e);
		}
	}

	private void checkResponse(HttpResponse response) throws KikException {
		if (response.getStatusLine().getStatusCode() != 200)
		{
			throw new KikException("HTTP Error " + response.getStatusLine().getStatusCode()+ ": " + response.getStatusLine().getReasonPhrase());
		}
	}

	public void sendMessage(Collection<Message> messages) throws KikException
	{
		try {
			logger.info("Send " + messages.size() + " messages");

			ObjectMapper mapper = new ObjectMapper();
			logger.info("Messages: " + mapper.writeValueAsString(messages));
			HttpPost request = new HttpPost(KIK_MESSAGE_URL);
			request.setEntity(new StringEntity(mapper.writeValueAsString(messages)));

			getKikResponse(request);
		} catch (IOException e) {
			throw new KikException(e);
		}
	}

	private void addAuthentication(AbstractHttpMessage request) throws FileNotFoundException, IOException {
		Properties properties =new Properties();
		properties.load(new FileReader(new File("src/main/resources/api.properties")));

		request.addHeader(BasicScheme.authenticate(
				new UsernamePasswordCredentials(properties.getProperty(KIK_BOT_USERNAME), properties.getProperty(KIK_BOT_API_KEY)),
				"UTF-8", false));
	}

	public static void main(String[] args) throws KikException {
		KikRestClient restClient = new KikRestClient();

//		restClient.setConfiguration();
//		restClient.getConfiguration();
		
		Message message = new Message();
		message.setBody("Test message");
		message.setTo("nicoerbs");
		message.setType("text");
		message.setChatId("b3be3bc15dbe59931666c06290abd944aaa769bb2ecaaf859bfb65678880afab");
		Collection<Message> messages = new ArrayList<>();
		messages.add(message);
		restClient.sendMessage(messages);
	}
}
