package eu.erbs.football.stats;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import eu.erbs.football.model.Match;
import eu.erbs.football.model.Result;
import eu.erbs.football.model.Team;

public class FastestLiveScoresApi {

	private final static String ENDPOINT = "https://api.crowdscores.com/api/v1/matches?competition_ids=48";
	private final static String API_KEY = "FASTEST_LIVE_SCORES_API_KEY";

	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		Properties properties = new Properties();
		properties.load(new FileReader(new File("src/main/resources/api.properties")));
		
		
		HttpClient httpClient = new DefaultHttpClient();

		HttpGet request = new HttpGet(ENDPOINT);
		request.addHeader("x-crowdscores-api-key", properties.getProperty(API_KEY));

		//		request.getParams().setLongParameter("competition_ids", 48);

		System.out.println(request.getURI());

		//		System.exit(0);


		HttpResponse response = httpClient.execute(request);

		System.out.println("----------------------------------------");
		System.out.println(response.getStatusLine());
		System.out.println("----------------------------------------");
		HttpEntity entity = response.getEntity();

		JSONArray json = new JSONArray(EntityUtils.toString(entity));
		for(Match match : extractMatches(json, "Darmstadt 98")){
			System.out.println(match);
		}

	}

	private static List<Match> extractMatches(JSONArray jsonArray, String team) {
		List<Match> matches = new ArrayList<>();
		
		JSONObject matchObject;
		Match match;
		for(int i=0; i<jsonArray.length(); i++){
			 matchObject = jsonArray.getJSONObject(i);
			 if(
					 matchObject.getJSONObject("awayTeam").getString("name").equals(team)
					 || matchObject.getJSONObject("homeTeam").getString("name").equals(team)){
				 match = new Match();
				 match.setStartDate(new Date(matchObject.getLong("start")));
				 match.setHomeTeam(new Team(matchObject.getJSONObject("homeTeam").getString("name")));
				 match.setAwayTeam(new Team(matchObject.getJSONObject("awayTeam").getString("name")));
				 match.setResult(new Result(matchObject.getInt("homeGoals"),matchObject.getInt("awayGoals")));
				 matches.add(match);
			 }
		}
		return matches;
	}
}
