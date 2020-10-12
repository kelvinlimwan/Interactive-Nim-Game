/*
    SUBJECT: COMP90041 PROGRAMMING AND SOFTWARE DEVELOPMENT
    PERIOD: SEMESTER 2 2020
    ASSIGNMENT: ASSIGNMENT 2
    FULL NAME: KELVIN LIM WAN
    STUDENT NUMBER: 929715
    CANVAS USERNAME: KELVINL3
*/

import java.util.Scanner;

public abstract class NimBaseGame {

    // constant
    protected final int MIN_STONES_TO_REMOVE = 1;

    // instance variables
    private int numStonesLeft;
    private NimPlayer player1;
    private NimPlayer player2;

    // constructor
    public NimBaseGame(int numStonesLeft, NimPlayer player1, NimPlayer player2) {
        this.numStonesLeft = numStonesLeft;
        this.player1 = player1;
        this.player2 = player2;
    }

    // getters
    public int getNumStonesLeft() {
        return numStonesLeft;
    }
    public NimPlayer getPlayer1() {
        return player1;
    }
    public NimPlayer getPlayer2() {
        return player2;
    }

    // setter
    public void setNumStonesLeft(int numStonesLeft) {
        this.numStonesLeft = numStonesLeft;
    }

    // launch game playing mode
    public abstract void play(Scanner keyboard);

}
