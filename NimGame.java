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

    public NimGame(int numStonesLeft, int upperBound, NimPlayer player1, NimPlayer player2) {
        this.numStonesLeft = numStonesLeft;
        this.upperBound = upperBound;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void play(Scanner keyboard) {

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

            // deduct stones to remove from numStones when valid number is given
            while (true) {
                int maxStonesToRemove = Math.min(upperBound, numStonesLeft);
                try {
                    // TODO: check if answer should be given on same line or next line (currently next line)
                    System.out.println(currentPlayer.getGivennname() +
                            "'s turn - remove how many?");

                    currentPlayer.toRemove(keyboard.nextInt()); // may throw InputMismatchException

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
