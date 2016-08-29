package eu.erbs.football.statistics;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;

import eu.erbs.football.model.Match;

public interface StatisticsProvider {
	
	
	void initialize() throws FileNotFoundException, IOException;
	
	JSONArray retrieveJsonArray() throws ClientProtocolException, IOException;	
	
	List<Match> extractMatches(JSONArray jsonArray, String team);

}
