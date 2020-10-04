/*
    SUBJECT: COMP90041 PROGRAMMING AND SOFTWARE DEVELOPMENT
    PERIOD: SEMESTER 2 2020
    ASSIGNMENT: ASSIGNMENT 1
    FULL NAME: KELVIN LIM WAN
    STUDENT NUMBER: 929715
    CANVAS USERNAME: KELVINL3
*/

public class NimAIPlayer extends NimPlayer {

    // constant variable
    private static final int MIN_STONES_TO_REMOVE = 1;

    // instance variables
    private int numStonesLeft;
    private int maxStonesToRemove;

    // constructors
    public NimAIPlayer() {
        super();
        numStonesLeft = 0;
        maxStonesToRemove = 1;
    }

    public NimAIPlayer(String username, String familyname, String givenname) {
        super(username, familyname, givenname);
        numStonesLeft = 0;
        maxStonesToRemove = 1;
    }

    // getters
    public int getNumStonesLeft() {
        return numStonesLeft;
    }
    public int getMaxStonesToRemove() {
        return maxStonesToRemove;
    }

    // setters
    public void setNumStonesLeft(int numStonesLeft) {
        this.numStonesLeft = numStonesLeft;
    }
    public void setMaxStonesToRemove(int maxStonesToRemove) {
        this.maxStonesToRemove = maxStonesToRemove;
    }

    public int removeStone() {
        if ((numStonesLeft-1) % (maxStonesToRemove+1) != 0) {
            return (numStonesLeft-1) % (maxStonesToRemove+1);
        } else {
            return MIN_STONES_TO_REMOVE;
        }
    }
}
