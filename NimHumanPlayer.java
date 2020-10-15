/*
    SUBJECT: COMP90041 PROGRAMMING AND SOFTWARE DEVELOPMENT
    PERIOD: SEMESTER 2 2020
    ASSIGNMENT: ASSIGNMENT 2
    FULL NAME: KELVIN LIM WAN
    STUDENT NUMBER: 929715
    CANVAS USERNAME: KELVINL3
*/

import java.util.InputMismatchException;
import java.util.Scanner;

public class NimHumanPlayer extends NimPlayer {

    // constructor
    public NimHumanPlayer(String username, String familyname, String givenname) {
        super(username, familyname, givenname);
    }

    @Override
    public int removeStone(int numStonesLeft, int maxStonesToRemove, Scanner keyboard) throws
            InputMismatchException {
        return keyboard.nextInt();
    }
}
