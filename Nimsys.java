/*
    SUBJECT: COMP90041 PROGRAMMING AND SOFTWARE DEVELOPMENT
    PERIOD: SEMESTER 2 2020
    ASSIGNMENT: ASSIGNMENT 1
    FULL NAME: KELVIN LIM WAN
    STUDENT NUMBER: 929715
    CANVAS USERNAME: KELVINL3
*/

import java.util.Scanner;

public class Nimsys {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Nimsys system = new Nimsys();

        System.out.println("Welcome to Nim");
        System.out.println();
        System.out.println("Please enter a command to continue or type 'help' for more " +
                "information");

        // start the command console
        console(keyboard, system);
    }

    // constant variable
    private static final int MIN_STONES_TO_REMOVE = 1;

    // instance variables
    private NimPlayer[] playersCollection;
    private int playersCount;

    // constructor
    public Nimsys() {
        playersCollection = new NimPlayer[100];
        playersCount = 0;
    }

    // command-line input console
    public static void console(Scanner keyboard, Nimsys system) {
        // repeat until user wants to exit game
        while (true) {
            System.out.println();

            // get user command
            System.out.print("$ ");
            String command = keyboard.next();

            switch (command) {
                case "exit":
                    return;  // terminate application operations naturally
                case "addplayer":
                    system.addPlayer(keyboard);
                    break;
                case "addaiplayer":
                    system.addAIPlayer();
                    break;
                case "removeplayer":
                    system.removePlayer(keyboard);
                    break;
                case "editplayer":
                    system.editPlayer(keyboard);
                    break;
                case "resetstats":
                    system.resetStats(keyboard);
                    break;
                case "displayplayer":
                    system.displayPlayer(keyboard);
                    break;
                case "rankings":
                    system.rankings();
                    break;
                case "startgame":
                    system.startGame(keyboard);
                    break;
                case "startadvancedgame":
                    system.startAdvancedGame(keyboard);
                    break;
                case "commands":
                    system.commands();
                    break;
                case "help":
                    system.help();
                    break;
            }
        }
    }

    private void exit() {
        // for file (player.dat)
    }

    private void addPlayer(Scanner keyboard) {
        /*
        // TRACING: print players and count BEFORE
        for (int i = 0; i < playersCount; i++) {
            System.out.println(playersCollection[i].getUsername() + playersCollection[i].getFamilyname() + playersCollection[i].getGivennname());
        }
        System.out.println(playersCount);
        */

        String username = keyboard.next();
        String familyname = keyboard.next();
        String givenname = keyboard.next();
        // check if username already exists
        for (int i = 0; i < playersCount; i++) {
            if (username.equals(playersCollection[i].getUsername())) {
                System.out.println("The player already exists.");
                return;
            }
        }
        playersCollection[playersCount] = new NimPlayer(username, familyname, givenname);
        playersCount++;

        /*
        // TRACING: print player and count AFTER
        for (int i = 0; i < playersCount; i++) {
            System.out.println(playersCollection[i].getUsername() + playersCollection[i].getFamilyname() + playersCollection[i].getGivennname());
        }
        System.out.println(playersCount);
         */
    }

    private void addAIPlayer() {
        // TODO: add code
    }

    private void removePlayer(Scanner keyboard) {

        /*
        // TRACING: print players and count BEFORE
        for (int i = 0; i < playersCount; i++) {
            System.out.println(playersCollection[i].getUsername() + playersCollection[i].getFamilyname() + playersCollection[i].getGivennname());
        }
        System.out.println(playersCount);
         */

        String username = keyboard.nextLine().trim();
        if (username.isEmpty()) {
            System.out.println("Are you sure you want to remove all players? (y/n)");
            String answer = keyboard.next();
            // TODO: case sensitive? any other input other than y is no?
            if (answer.equals("y")) {
                for (int i = 0; i < playersCount; i++) {
                    playersCollection[i] = null;
                }
                playersCount = 0;
            }
        } else {
            boolean exist = false;
            for (int i = 0; i < playersCount; i++) {
                if (username.equals(playersCollection[i].getUsername())) {
                    exist = true;
                    playersCollection[i] = playersCollection[i + 1];
                } else if (exist) {
                    if (i < playersCount - 1) {
                        playersCollection[i] = playersCollection[i + 1];
                    } else {
                        playersCollection[i] = null;
                    }
                }
            }

            if (exist) {
                playersCount--;  // decrement count of players
            } else {
                System.out.println("The player does not exist.");
            }
        }

        /*
        // TRACING: print players and count AFTER
        for (int i = 0; i < playersCount; i++) {
            System.out.println(playersCollection[i].getUsername() + playersCollection[i].getFamilyname() + playersCollection[i].getGivennname());
        }
        System.out.println(playersCount);
         */
    }

    private void editPlayer(Scanner keyboard) {
        String username = keyboard.next();
        String newFamilyname = keyboard.next();
        String newGivenname = keyboard.next();

        for (int i = 0; i < playersCount; i++) {
            if (username.equals(playersCollection[i].getUsername())) {
                playersCollection[i].setFamilyname(newFamilyname);
                playersCollection[i].setGivenname(newGivenname);
                return;
            }
        }
        System.out.println("The player does not exist.");
    }

    private void resetStats(Scanner keyboard) {
        String username = keyboard.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println("Are you sure you want to reset all player statistics? (y/n)");
            String answer = keyboard.next();
            // TODO: case sensitive? any other input other than y is no?
            if (answer.equals("y")) {
                for (int i = 0; i < playersCount; i++) {
                    playersCollection[i].setGames(0);
                    playersCollection[i].setWins(0);
                }
            }
        } else {
            for (int i = 0; i < playersCount; i++) {
                if (username.equals(playersCollection[i].getUsername())) {
                    playersCollection[i].setGames(0);
                    playersCollection[i].setWins(0);
                    return;
                }
            }
            System.out.println("The player does not exist.");
        }
    }

    private void displayPlayer(Scanner keyboard) {
        String username = keyboard.nextLine().trim();

        if (username.isEmpty()) {
            for (int i = 0; i < playersCount; i++) {
                System.out.println(playersCollection[i]);
            }
        } else {
            for (int i = 0; i < playersCount; i++) {
                if (username.equals(playersCollection[i].getUsername())) {
                    System.out.println(playersCollection[i]);
                    return;
                }
            }
            System.out.println("The player does not exist.");
        }
    }

    private void rankings() {
        // TODO: add code
    }

    // TODO: modify code
    // trigger the start of the game
    private void startGame(Scanner keyboard) {
        /*
        // get player names and create respective instances of NimPlayer class
        System.out.print("Please enter Player 1's name : ");
        NimPlayer player1 = new NimPlayer(keyboard.next());
        System.out.print("Please enter Player 2's name : ");
        NimPlayer player2 = new NimPlayer(keyboard.next());

        int upperBound;
        int numStonesLeft;
        int numGamesPlayed = 0;
        int player1Wins = 0;
        int player2Wins = 0;
        // repeat game until user decides not to continue
        while (true) {
            numGamesPlayed++;  // increment number of games played

            // get round specifications
            System.out.print("Enter upper bound : ");
            upperBound = keyboard.nextInt();
            System.out.print("Enter initial number of stones : ");
            numStonesLeft = keyboard.nextInt();
            System.out.println();

            NimPlayer currentPlayer = player1;
            // repeat removal of stones until all stones are removed
            while (numStonesLeft > 0) {
                // display number of stones left, represented by asterisks
                System.out.print(numStonesLeft + " stones left :");
                for (int i = 0; i < numStonesLeft; i++) {
                    System.out.print(" *");
                }
                System.out.println();

                // deduct stones to remove from numStones when valid number is given
                while (true) {
                    System.out.print(currentPlayer.getName() +
                            "'s turn. Enter stones to remove : ");
                    currentPlayer.toRemove(keyboard.nextInt());

                    if (currentPlayer.removeStone() > upperBound) {
                        System.out.println("Upper bound limit exceed, " +
                                "upper bound maximum choice is " + upperBound + "\n");
                    } else if (currentPlayer.removeStone() > numStonesLeft ||
                            currentPlayer.removeStone() < MIN_STONES_TO_REMOVE) {
                        System.out.println("Invalid attempt, only " + numStonesLeft +
                                " stones remaining! Try again:\n");
                    } else {
                        numStonesLeft -= currentPlayer.removeStone();
                        System.out.println();
                        break;
                    }
                }

                // switch currentPlayer for next turn
                if (currentPlayer.equals(player1)) {
                    currentPlayer = player2;
                } else {
                    currentPlayer = player1;
                }
            }

            System.out.println("Game Over");
            System.out.println(currentPlayer.getName() + " wins!\n");

            // set number of wins for winning player
            if (currentPlayer.equals(player1)) {
                player1.setWins(++player1Wins);
            } else {
                player2.setWins(++player2Wins);
            }

            // set number of games played for both players
            player1.setGames(numGamesPlayed);
            player2.setGames(numGamesPlayed);

            // get user decision after round
            System.out.print("Do you want to play again (Y/N): ");
            String answer = keyboard.next();

            // when user does not want to play again, end game and display players' statistics
            if (! answer.equals("Y")) {
                System.out.println(player1);
                System.out.println(player2);
                break;
            }
        }
         */
    }

    private void startAdvancedGame(Scanner keyboard) {
        // TODO: add code
    }

    // display all commands available to user
    private void commands() {
        // create an array containing all commands of type NimCommand
        NimCommand[] commands = new NimCommand[12];
        commands[0] = new NimCommand("exit");
        commands[1] = new NimCommand("addplayer", new String[] {"username", "family name",
                "given name"});
        commands[2] = new NimCommand("addaiplayer", new String[] {"username",
                "family name", "given name"});
        commands[3] = new NimCommand("removeplayer", new String[] {"optional username"});
        commands[4] = new NimCommand("editplayer", new String[] {"username",
                "new family name", "new given name"});
        commands[5] = new NimCommand("resetstats", new String[] {"optional username"});
        commands[6] = new NimCommand("displayplayer", new String[] {"optional username"});
        commands[7] = new NimCommand("rankings", new String[] {"optional asc or desc"});
        commands[8] = new NimCommand("startgame", new String[] {"initial number of stones",
                "upper bound", "username1", "username2"});
        commands[9] = new NimCommand("startadvancedgame", new String[] {"initial number " +
                "of stones", "username 1", "username 2"});
        commands[10] = new NimCommand("commands");
        commands[11] = new NimCommand("help");

        // display commands and parameters
        for (int i = 0; i < commands.length; i++) {
            System.out.printf("%2d: %s\n", i + 1, commands[i]);
        }
    }

    // display help messages
    private void help() {
        System.out.println("Type 'commands' to list all available commands");
        System.out.println("Type 'startgame' to play game");
        System.out.println("The player that removes the last stone loses!");
    }
}
