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
import java.util.StringTokenizer;

public class NimAdvancedGame extends NimBaseGame {

    // constants
    private final int SMALLEST_POSITION = 1;
    private final int MAX_STONES_TO_REMOVE = 2;

    // instance variables
    private int initNumStones;
    private boolean[] available;
    private String lastMove;

    // constructor
    public NimAdvancedGame(int numStonesLeft, NimPlayer player1, NimPlayer player2) {
        super(numStonesLeft, player1, player2);
        initNumStones = numStonesLeft;
        available = new boolean[numStonesLeft];
        for (int i = 0; i < numStonesLeft; i++) {
            available[i] = true;
        }
        lastMove = null;
    }

    @Override
    public void play(Scanner keyboard) {

        System.out.println();
        System.out.println("Initial stone count: " + initNumStones);
        System.out.print("Stones display:");
        for (int i = 1; i <= initNumStones; i++) {
            System.out.printf(" <%d,*>", i);
        }
        System.out.println();
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

                // display stones represented by asterisks if present and x if removed
                System.out.print(getNumStonesLeft() + " stones left:");
                for (int i = 1; i <= initNumStones; i++) {
                    String stone = "*";
                    if (!available[i-1]) {
                        stone = "x";
                    }
                    System.out.printf(" <%d,%s>", i, stone);
                }
                System.out.println();

                System.out.println(currentPlayer.getGivennname() + "'s turn - which to remove?");

                // when it is an ai's turn
                if (currentPlayer instanceof NimAIPlayer) {

                    lastMove = currentPlayer.advancedMove(available, lastMove);

                    StringTokenizer move = new StringTokenizer(lastMove);
                    int position = Integer.parseInt(move.nextToken());
                    int numToRemove = Integer.parseInt(move.nextToken());

                    // update boolean array
                    for (int i = 1; i <= numToRemove; i++) {
                        available[position+i-2] = false;
                    }

                    setNumStonesLeft(getNumStonesLeft() - numToRemove);
                    break;

                // when it is a human's turn
                } else if (currentPlayer instanceof NimHumanPlayer) {

                    try {
                        ((NimHumanPlayer) currentPlayer).setPosition(keyboard.nextInt());
                        ((NimHumanPlayer) currentPlayer).setRemove(keyboard.nextInt());

                        lastMove = currentPlayer.advancedMove(available, lastMove);

                        StringTokenizer move = new StringTokenizer(lastMove);
                        int position = Integer.parseInt(move.nextToken());
                        int numToRemove = Integer.parseInt(move.nextToken());

                        // throw exceptions for invalid position or number of stones prompted
                        if (position < SMALLEST_POSITION || position > initNumStones) {
                            throw new Exception("Invalid move.");
                        } else if (numToRemove != MIN_STONES_TO_REMOVE &&
                                numToRemove != MAX_STONES_TO_REMOVE) {
                            throw new Exception("Invalid move.");
                        } else if (position == initNumStones &&
                                numToRemove == MAX_STONES_TO_REMOVE) {
                            throw new Exception("Invalid move.");
                        } else {
                            for (int i = 1; i <= numToRemove; i++) {
                               if (!available[position+i-2]) {
                                   throw new Exception("Invalid move.");
                               }
                            }
                        }

                        // update boolean array
                        for (int i = 1; i <= numToRemove; i++) {
                            available[position+i-2] = false;
                        }

                        setNumStonesLeft(getNumStonesLeft() - numToRemove);
                        break;

                    } catch (InputMismatchException e) {
                        keyboard.nextLine(); // to avoid infinite loop
                        System.out.println();
                        System.out.println("Invalid move.");
                        System.out.println();
                    } catch (Exception e) {
                        System.out.println();
                        System.out.println(e.getMessage());
                        System.out.println();
                    }
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

        // set symmetrical and subset to false for ai players
        if (getPlayer1() instanceof NimAIPlayer) {
            ((NimAIPlayer) getPlayer1()).setSymmetrical(false);
            ((NimAIPlayer) getPlayer1()).setSubset(false);
        }
        if (getPlayer2() instanceof NimAIPlayer) {
            ((NimAIPlayer) getPlayer2()).setSymmetrical(false);
            ((NimAIPlayer) getPlayer2()).setSubset(false);
        }

        // set the winner
        NimPlayer winner = getPlayer1();
        if (currentPlayer.equals(getPlayer1())) {
            winner = getPlayer2();
        }

        // set number of wins for winner
        winner.setWins(winner.getWins() + 1);

        // set number of games played for both players
        getPlayer1().setGames(getPlayer1().getGames() + 1);
        getPlayer2().setGames(getPlayer2().getGames() + 1);

        System.out.println("Game Over");
        System.out.printf("%s %s wins!\n", winner.getGivennname(), winner.getFamilyname());
    }

}
