/*
    SUBJECT: COMP90041 PROGRAMMING AND SOFTWARE DEVELOPMENT
    PERIOD: SEMESTER 2 2020
    ASSIGNMENT: ASSIGNMENT 1
    FULL NAME: KELVIN LIM WAN
    STUDENT NUMBER: 929715
    CANVAS USERNAME: KELVINL3
*/

import java.util.Scanner;
import java.util.InputMismatchException;

public class NimGame {

    // constant variable
    private static final int MIN_STONES_TO_REMOVE = 1;

    // instance variables
    private int numStonesLeft;
    private int upperBound;
    private NimPlayer player1;
    private NimPlayer player2;

    //constructors
    public NimGame() {
        this(0, 0, null, null);
    }

    public NimGame(int numStonesLeft, int upperBound, NimPlayer player1, NimPlayer player2) {
        this.numStonesLeft = numStonesLeft;
        this.upperBound = upperBound;
        this.player1 = player1;
        this.player2 = player2;
    }

    // getters
    public int getNumStonesLeft() {
        return numStonesLeft;
    }
    public int getUpperBound() {
        return upperBound;
    }
    public NimPlayer getPlayer1() {
        return player1;
    }
    public NimPlayer getPlayer2() {
        return player2;
    }

    // setters
    public void setNumStonesLeft(int numStonesLeft) {
        this.numStonesLeft = numStonesLeft;
    }
    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }
    public void setPlayer1(NimPlayer player) {
        this.player1 = player;
    }
    public void setPlayer2(NimPlayer player) {
        this.player2 = player;
    }

    public void play(Scanner keyboard) {

        System.out.println();
        System.out.println("Initial stone count: " + numStonesLeft);
        System.out.println("Maximum stone removal: " + upperBound);
        System.out.println("Player 1: " + player1.fullname());
        System.out.println("Player 2: " + player2.fullname());

        NimPlayer currentPlayer = player1;
        // repeat removal of stones until all stones are removed
        while (numStonesLeft > 0) {
            System.out.println();

            // display number of stones left, represented by asterisks
            System.out.print(numStonesLeft + " stones left :");
            for (int i = 0; i < numStonesLeft; i++) {
                System.out.print(" *");
            }
            System.out.println();

            int maxStonesToRemove = Math.min(upperBound, numStonesLeft);

            // TODO: check if downcasting is safe
            if (currentPlayer instanceof NimAIPlayer) {
                System.out.println(currentPlayer.getGivennname() + "'s turn - remove how many?");

                ((NimAIPlayer) currentPlayer).setNumStonesLeft(numStonesLeft);
                ((NimAIPlayer) currentPlayer).setMaxStonesToRemove(maxStonesToRemove);
                numStonesLeft -= currentPlayer.removeStone();
            } else if (currentPlayer instanceof NimHumanPlayer) {
                // deduct stones to remove from numStones when valid number is given
                while (true) {
                    try {
                        System.out.println(currentPlayer.getGivennname() + "'s turn - remove how many?");
                        ((NimHumanPlayer) currentPlayer).setRemove(keyboard.nextInt()); // may throw InputMismatchException

                        if (currentPlayer.removeStone() < MIN_STONES_TO_REMOVE ||
                                currentPlayer.removeStone() > maxStonesToRemove) {
                            throw new Exception("Invalid move. You must remove between " +
                                    MIN_STONES_TO_REMOVE + " and " + maxStonesToRemove + " stones.");
                        } else {
                            numStonesLeft -= currentPlayer.removeStone();
                            break;
                        }
                    } catch (InputMismatchException ime) {
                        keyboard.nextLine(); // to avoid infinite loop
                        System.out.println();
                        System.out.println("Invalid move. You must remove between " +
                                MIN_STONES_TO_REMOVE + " and " + maxStonesToRemove + " stones.");
                        System.out.println();
                    } catch (Exception e) {
                        System.out.println();
                        System.out.println(e.getMessage());
                        System.out.println();
                    }
                }
            }

            // switch currentPlayer for next turn
            if (currentPlayer.equals(player1)) {
                currentPlayer = player2;
            } else {
                currentPlayer = player1;
            }
        }

        // set number of wins for winning player
        if (currentPlayer.equals(player1)) {
            player1.setWins(player1.getWins() + 1);
        } else {
            player2.setWins(player2.getWins() + 1);
        }

        // set number of games played for both players
        player1.setGames(player1.getGames() + 1);
        player2.setGames(player2.getGames() + 1);

        System.out.println();
        System.out.println("Game Over");
        System.out.println(currentPlayer.fullname() + " wins!");
    }
}
