/*
    SUBJECT: COMP90041 PROGRAMMING AND SOFTWARE DEVELOPMENT
    PERIOD: SEMESTER 2 2020
    ASSIGNMENT: ASSIGNMENT 2
    FULL NAME: KELVIN LIM WAN
    STUDENT NUMBER: 929715
    CANVAS USERNAME: KELVINL3
*/

import java.util.Scanner;
import java.util.InputMismatchException;

public class NimGame extends NimBaseGame{

    // instance variable
    private int upperBound;

    //constructor
    public NimGame(int numStonesLeft, int upperBound, NimPlayer player1, NimPlayer player2) {
        super(numStonesLeft, player1, player2);
        this.upperBound = upperBound;
    }

    @Override
    public void play(Scanner keyboard) {

        System.out.println();
        System.out.println("Initial stone count: " + getNumStonesLeft());
        System.out.println("Maximum stone removal: " + upperBound);
        System.out.printf("Player 1: %s %s\n", getPlayer1().getGivennname(),
                getPlayer1().getFamilyname());
        System.out.printf("Player 2: %s %s\n", getPlayer2().getGivennname(),
                getPlayer2().getFamilyname());
        System.out.println();

        NimPlayer currentPlayer = getPlayer1();

        // repeat removal of stones until all stones are removed
        while (getNumStonesLeft() > 0) {

            // deduct stones to remove from number of stones left when valid number is given
            while (true) {

                // display number of stones left, represented by asterisks
                System.out.print(getNumStonesLeft() + " stones left:");
                for (int i = 0; i < getNumStonesLeft(); i++) {
                    System.out.print(" *");
                }
                System.out.println();

                System.out.println(currentPlayer.getGivennname() + "'s turn - remove how many?");

                int maxStonesToRemove = Math.min(upperBound, getNumStonesLeft());

                try {
                    int numRemove = currentPlayer.removeStone(getNumStonesLeft(),
                            maxStonesToRemove, keyboard);

                    // throw exceptions for invalid number of stones prompted
                    if (numRemove < MIN_STONES_TO_REMOVE || numRemove > maxStonesToRemove) {
                        throw new Exception("Invalid move. You must remove between " +
                                MIN_STONES_TO_REMOVE + " and " + maxStonesToRemove + " stones.");
                    }

                    setNumStonesLeft(getNumStonesLeft() - numRemove);
                    break;

                } catch (InputMismatchException e) {
                    keyboard.nextLine();  // to avoid infinite loop
                    System.out.println();
                    System.out.printf("Invalid move. You must remove between %d and %d " +
                            "stones.\n", MIN_STONES_TO_REMOVE, maxStonesToRemove);
                    System.out.println();
                } catch (Exception e) {
                    System.out.println();
                    System.out.println(e.getMessage());
                    System.out.println();
                }
            }

            System.out.println();

            // switch current player for next turn
            if (currentPlayer.equals(getPlayer1())) {
                currentPlayer = getPlayer2();
            } else {
                currentPlayer = getPlayer1();
            }

        }

        // set number of wins for winning player
        if (currentPlayer.equals(getPlayer1())) {
            getPlayer1().setWins(getPlayer1().getWins() + 1);
        } else {
            getPlayer2().setWins(getPlayer2().getWins() + 1);
        }

        // set number of games played for both players
        getPlayer1().setGames(getPlayer1().getGames() + 1);
        getPlayer2().setGames(getPlayer2().getGames() + 1);

        System.out.println("Game Over");
        System.out.printf("%s %s wins!\n", currentPlayer.getGivennname(),
                currentPlayer.getFamilyname());
    }
}
