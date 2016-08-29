package eu.erbs.football.statistics;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.erbs.football.model.Match;
import eu.erbs.football.model.Result;
import eu.erbs.football.model.Team;
import eu.erbs.football.statistics.utils.OpenLigaDateFormat;


public class OpenLigaDB extends AbstractStatisticsProvider {

	private final static String ENDPOINT = "http://www.openligadb.de/api/getmatchdata/bl1/2016/";

	public static void main(String args[]) throws ClientProtocolException, IOException {
		StatisticsProvider provider = new OpenLigaDB();
		provider.initialize();
		JSONArray json = provider.retrieveJsonArray();
		System.out.println(json);
		for(Match match : provider.extractMatches(json, "SV Darmstadt 98")){
			System.out.println(match);
		}
	}
	
	@Override
	public void initialize() throws FileNotFoundException, IOException {
		request = new HttpGet(ENDPOINT + 1);
	}

	@Override
	public List<Match> extractMatches(JSONArray jsonArray, String team) {
		List<Match> matches = new ArrayList<>();

		JSONObject matchObject;
		Match match;
		JSONArray matchResultsArray;
		JSONObject matchResultObject;
		for(int i=0; i<jsonArray.length(); i++){
			matchObject = jsonArray.getJSONObject(i);
			if(
					matchObject.getJSONObject("Team1").getString("TeamName").equals(team)
					|| matchObject.getJSONObject("Team2").getString("TeamName").equals(team)){
				match = new Match();
				try {
					match.setStartDate(new OpenLigaDateFormat().parse(matchObject.getString("MatchDateTime")));
				} catch (JSONException | ParseException e) {
					e.printStackTrace();
				}
				match.setHomeTeam(new Team(matchObject.getJSONObject("Team1").getString("TeamName")));
				match.setAwayTeam(new Team(matchObject.getJSONObject("Team2").getString("TeamName")));
				matchResultsArray = matchObject.getJSONArray("MatchResults");
				for(int n=0; n< matchResultsArray.length();n++){
					matchResultObject = matchResultsArray.getJSONObject(n);
					if(
							matchResultObject.getString("ResultName").equals("Ergebnis nach Spielende")
							|| matchResultObject.getString("ResultName").equals("Endergebnis")){
						match.setResult(new Result(
								matchResultObject.getInt("PointsTeam1"),
								matchResultObject.getInt("PointsTeam2")));
					}
				}
				
				matches.add(match);
			}
		}
		return matches;
	}
}