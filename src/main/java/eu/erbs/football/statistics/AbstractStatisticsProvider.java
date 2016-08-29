package eu.erbs.football.statistics;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

public abstract class AbstractStatisticsProvider implements StatisticsProvider {

	protected HttpGet request;
	
	@Override
	public JSONArray retrieveJsonArray() throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = httpClient.execute(request);

		System.out.println("----------------------------------------");
		System.out.println(response.getStatusLine());
		System.out.println("----------------------------------------");
		HttpEntity entity = response.getEntity();

		return new JSONArray(EntityUtils.toString(entity));
	}

	
}
