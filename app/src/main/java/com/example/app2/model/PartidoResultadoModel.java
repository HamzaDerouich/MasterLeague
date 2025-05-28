package com.example.app2.model;

import java.io.Serializable;

public class PartidoResultadoModel implements Serializable {

    private String teamsHomeName;
    private String teamsAwayName;
    private String homeTeamLogo;
    private String awayTeamLogo;
    private int goalsHome;
    private int goalsAway;
    private String fixtureDate;
    public PartidoResultadoModel()
    {

    }

    public PartidoResultadoModel(String teamsHomeName, String teamsAwayName, String homeTeamLogo, String awayTeamLogo, int goalsHome, int goalsAway, String fixtureDate) {
        this.teamsHomeName = teamsHomeName;
        this.teamsAwayName = teamsAwayName;
        this.homeTeamLogo = homeTeamLogo;
        this.awayTeamLogo = awayTeamLogo;
        this.goalsHome = goalsHome;
        this.goalsAway = goalsAway;
        this.fixtureDate = fixtureDate;
    }

    public String getTeamsHomeName() {
        return teamsHomeName;
    }

    public void setTeamsHomeName(String teamsHomeName) {
        this.teamsHomeName = teamsHomeName;
    }

    public String getTeamsAwayName() {
        return teamsAwayName;
    }

    public void setTeamsAwayName(String teamsAwayName) {
        this.teamsAwayName = teamsAwayName;
    }

    public String getHomeTeamLogo() {
        return homeTeamLogo;
    }

    public void setHomeTeamLogo(String homeTeamLogo) {
        this.homeTeamLogo = homeTeamLogo;
    }

    public String getAwayTeamLogo() {
        return awayTeamLogo;
    }

    public void setAwayTeamLogo(String awayTeamLogo) {
        this.awayTeamLogo = awayTeamLogo;
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

    public String getFixtureDate() {
        return fixtureDate;
    }

    public void setFixtureDate(String fixtureDate) {
        this.fixtureDate = fixtureDate;
    }

    @Override
    public String toString() {
        return "PartidoResultadoModel{" +
                "teamsHomeName='" + teamsHomeName + '\'' +
                ", teamsAwayName='" + teamsAwayName + '\'' +
                ", homeTeamLogo='" + homeTeamLogo + '\'' +
                ", awayTeamLogo='" + awayTeamLogo + '\'' +
                ", goalsHome=" + goalsHome +
                ", goalsAway=" + goalsAway +
                ", fixtureDate='" + fixtureDate + '\'' +
                '}';
    }
}
