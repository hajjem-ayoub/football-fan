package eu.erbs.football.model;

import java.util.Date;

public class Match {
	
	private Result result;
	private Team homeTeam;
	private Team awayTeam;
	private Date startDate;
	
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public Team getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}
	public Team getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append(startDate.toString());
		buffer.append("\t");
		buffer.append(homeTeam.toString());
		buffer.append("\t");
		buffer.append(result.toString());
		buffer.append("\t");
		buffer.append(awayTeam.toString());
		return buffer.toString();
	}
}