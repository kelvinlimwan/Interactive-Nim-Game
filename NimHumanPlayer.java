/*
    SUBJECT: COMP90041 PROGRAMMING AND SOFTWARE DEVELOPMENT
    PERIOD: SEMESTER 2 2020
    ASSIGNMENT: ASSIGNMENT 1
    FULL NAME: KELVIN LIM WAN
    STUDENT NUMBER: 929715
    CANVAS USERNAME: KELVINL3
*/

public class NimHumanPlayer extends NimPlayer {

    // instance variable
    private int remove;

    // constructors
    public NimHumanPlayer() {
        super();
        remove = 0;
    }

    public NimHumanPlayer(String username, String familyname, String givenname) {
        super(username, familyname, givenname);
        remove = 0;
    }

    // getter
    public int getRemove() {
        return remove;
    }

    // setter
    public void setRemove(int remove) {
        this.remove = remove;
    }

    public int removeStone() {
        return getRemove();
    }
}
