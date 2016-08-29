package eu.erbs.football.model;

public class Result {
	
	private int goalsHome;
	private int goalsAway;
	
	public Result(int goalsHome, int goalsAway){
		this.goalsHome = goalsHome;
		this.goalsAway = goalsAway;
	}
	
	public int getGoalsHome() {
		return goalsHome;
	}
	public void setGoalsHome(int goalsHome) {
		this.goalsHome = goalsHome;
	}
	public int getGoalsAway() {
		return goalsAway;
	}
	public void setGoalsAway(int goalsAway) {
		this.goalsAway = goalsAway;
	}
	
	@Override
	public String toString(){
		return goalsHome + ":" + goalsAway;
	}

}
