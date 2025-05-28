package com.example.app2.model;

import java.io.Serializable;

public class ClasificacionModel implements Serializable
{
     private int rank;
     private String teamName;

     private String teamLogo;

     private int points;

     private int goalsDiff;

     private int allPlayed;

     private int allWin;

     private int allDraw;

     private int allLose;

     private int allGoalsFor;

     private int allGoalsAgainst;

     public  ClasificacionModel()
     {

     }

    public ClasificacionModel(int rank, String teamName, String teamLogo, int points, int goalsDiff, int allPlayed, int allWin, int allDraw, int allLose, int allGoalsFor, int allGoalsAgainst) {
        this.rank = rank;
        this.teamName = teamName;
        this.teamLogo = teamLogo;
        this.points = points;
        this.goalsDiff = goalsDiff;
        this.allPlayed = allPlayed;
        this.allWin = allWin;
        this.allDraw = allDraw;
        this.allLose = allLose;
        this.allGoalsFor = allGoalsFor;
        this.allGoalsAgainst = allGoalsAgainst;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamLogo() {
        return teamLogo;
    }

    public void setTeamLogo(String teamLogo) {
        this.teamLogo = teamLogo;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGoalsDiff() {
        return goalsDiff;
    }

    public void setGoalsDiff(int goalsDiff) {
        this.goalsDiff = goalsDiff;
    }

    public int getAllPlayed() {
        return allPlayed;
    }

    public void setAllPlayed(int allPlayed) {
        this.allPlayed = allPlayed;
    }

    public int getAllWin() {
        return allWin;
    }

    public void setAllWin(int allWin) {
        this.allWin = allWin;
    }

    public int getAllDraw() {
        return allDraw;
    }

    public void setAllDraw(int allDraw) {
        this.allDraw = allDraw;
    }

    public int getAllLose() {
        return allLose;
    }

    public void setAllLose(int allLose) {
        this.allLose = allLose;
    }

    public int getAllGoalsFor() {
        return allGoalsFor;
    }

    public void setAllGoalsFor(int allGoalsFor) {
        this.allGoalsFor = allGoalsFor;
    }

    public int getAllGoalsAgainst() {
        return allGoalsAgainst;
    }

    public void setAllGoalsAgainst(int allGoalsAgainst) {
        this.allGoalsAgainst = allGoalsAgainst;
    }

    @Override
    public String toString() {
        return "ClasificacionModel{" +
                "rank=" + rank +
                ", teamName='" + teamName + '\'' +
                ", teamLogo='" + teamLogo + '\'' +
                ", points=" + points +
                ", goalsDiff=" + goalsDiff +
                ", allPlayed=" + allPlayed +
                ", allWin=" + allWin +
                ", allDraw=" + allDraw +
                ", allLose=" + allLose +
                ", allGoalsFor=" + allGoalsFor +
                ", allGoalsAgainst=" + allGoalsAgainst +
                '}';
    }
}
