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
