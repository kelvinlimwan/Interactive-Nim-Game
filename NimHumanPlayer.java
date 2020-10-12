/*
    SUBJECT: COMP90041 PROGRAMMING AND SOFTWARE DEVELOPMENT
    PERIOD: SEMESTER 2 2020
    ASSIGNMENT: ASSIGNMENT 2
    FULL NAME: KELVIN LIM WAN
    STUDENT NUMBER: 929715
    CANVAS USERNAME: KELVINL3
*/

public class NimHumanPlayer extends NimPlayer {

    // instance variables
    private int remove;
    private int position;

    // constructors
    public NimHumanPlayer(String username, String familyname, String givenname) {
        super(username, familyname, givenname);
        remove = 0;
    }

    // setters
    public void setRemove(int remove) {
        this.remove = remove;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int removeStone() {
        return remove;
    }

    @Override
    public String advancedMove(boolean[] available, String lastMove){
        return position + " " + remove;
    }
}
