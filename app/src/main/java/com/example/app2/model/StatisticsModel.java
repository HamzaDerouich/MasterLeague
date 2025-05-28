package com.example.app2.model;

public class StatisticsModel {

    private TeamModel team;
    private LeagueModel league;
    private GamesModel games;
    private SubstitutesModel substitutes;
    private ShotsModel shots;
    private GoalsModel goals;
    private PassesModel passes;
    private TacklesModel tackles;

    private DuelsModel duels;
    private DribblesModel dribbles;
    private FoulsModel fouls;
    private CardsModel cards;
    private PenaltyModel penalty;

    public StatisticsModel() {}

    // Getters and Setters


    public TeamModel getTeam() {
        return team;
    }

    public void setTeam(TeamModel team) {
        this.team = team;
    }

    public LeagueModel getLeague() {
        return league;
    }

    public void setLeague(LeagueModel league) {
        this.league = league;
    }

    public GamesModel getGames() {
        return games;
    }

    public void setGames(GamesModel games) {
        this.games = games;
    }

    public SubstitutesModel getSubstitutes() {
        return substitutes;
    }

    public void setSubstitutes(SubstitutesModel substitutes) {
        this.substitutes = substitutes;
    }

    public ShotsModel getShots() {
        return shots;
    }

    public void setShots(ShotsModel shots) {
        this.shots = shots;
    }

    public GoalsModel getGoals() {
        return goals;
    }

    public void setGoals(GoalsModel goals) {
        this.goals = goals;
    }

    public PassesModel getPasses() {
        return passes;
    }

    public void setPasses(PassesModel passes) {
        this.passes = passes;
    }

    public TacklesModel getTackles() {
        return tackles;
    }

    public void setTackles(TacklesModel tackles) {
        this.tackles = tackles;
    }

    public DribblesModel getDribbles() {
        return dribbles;
    }

    public void setDribbles(DribblesModel dribbles) {
        this.dribbles = dribbles;
    }

    public FoulsModel getFouls() {
        return fouls;
    }

    public void setFouls(FoulsModel fouls) {
        this.fouls = fouls;
    }

    public CardsModel getCards() {
        return cards;
    }

    public void setCards(CardsModel cards) {
        this.cards = cards;
    }

    public PenaltyModel getPenalty() {
        return penalty;
    }

    public void setPenalty(PenaltyModel penalty) {
        this.penalty = penalty;
    }

    public DuelsModel getDuels() {
        return duels;
    }

    public void setDuels(DuelsModel duels) {
        this.duels = duels;
    }
}
