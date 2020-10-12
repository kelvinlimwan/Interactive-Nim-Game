/*
    SUBJECT: COMP90041 PROGRAMMING AND SOFTWARE DEVELOPMENT
    PERIOD: SEMESTER 2 2020
    ASSIGNMENT: ASSIGNMENT 2
    FULL NAME: KELVIN LIM WAN
    STUDENT NUMBER: 929715
    CANVAS USERNAME: KELVINL3
 */

import java.io.Serializable;

public abstract class NimPlayer implements Serializable {

    // constant
    private final int MIN_FOR_TWO_DIGITS_REP = 10;

    // instance variables
    private final String username;
    private String familyname;
    private String givenname;
    private int games;
    private int wins;

    // constructors
    public NimPlayer(String username, String familyname, String givenname) {
        this.username = username;
        this.familyname = familyname;
        this.givenname = givenname;
        games = 0;
        wins = 0;
    }

    // getter methods
    public String getUsername() {
        return username;
    }
    public String getFamilyname() {
        return familyname;
    }
    public String getGivennname() {
        return givenname;
    }
    public int getGames() {
        return games;
    }
    public int getWins() {
        return wins;
    }

    // setter methods
    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }
    public void setGivenname(String givenname) {
        this.givenname = givenname;
    }
    public void setGames(int games) {
        this.games = games;
    }
    public void setWins(int wins) {
        this.wins = wins;
    }

    // return number of stones to remove in a normal game round
    public abstract int removeStone();

    // return position and number of stones to remove in an advanced game round
    public abstract String advancedMove(boolean[] available, String lastMove);

    // return win ratio of player
    public double winRatio() {
        if (games != 0) {
            return (double) wins / games * 100;
        }
        return 0;
    }

    // return rounded percentage string representation of player's win ratio
    public String winRatioRoundedRep() {
        return Math.round(winRatio()) + "%";
    }

    // return a two digit representation of player's number of games played
    public String gamesTwoDigitRep() {
        if (games < MIN_FOR_TWO_DIGITS_REP) {
            return "0" + games;
        } else {
            return Integer.toString(games);
        }
    }

    /* return true if the two players have same username, family name, given name, number of games
    played and number of wins; return false otherwise */
    public boolean equals(NimPlayer player) {
        return username.equals(player.getUsername()) && familyname.equals(player.getFamilyname()) &&
                givenname.equals(player.getGivennname()) && games == player.getGames() &&
                wins == player.getWins();
    }

    // display player's names and statistics
    public String toString() {
        return username + ", " + givenname + ", " + familyname + ", " + games + " games, " + wins +
                " wins";
    }

}
