/*
    SUBJECT: COMP90041 PROGRAMMING AND SOFTWARE DEVELOPMENT
    PERIOD: SEMESTER 2 2020
    ASSIGNMENT: ASSIGNMENT 1
    FULL NAME: KELVIN LIM WAN
    STUDENT NUMBER: 929715
    CANVAS USERNAME: KELVINL3
*/

import java.io.Serializable;

public class NimState implements Serializable {

    // instance variables
    private NimPlayer[] playersCollection;
    private int playersCount;

    // constructor
    public NimState(NimPlayer[] playersCollection, int playersCount) {
        this.playersCollection = playersCollection;
        this.playersCount = playersCount;
    }

    // getters
    public NimPlayer[] getPlayersCollection() {
        return playersCollection;
    }
    public int getPlayersCount() {
        return playersCount;
    }
}
