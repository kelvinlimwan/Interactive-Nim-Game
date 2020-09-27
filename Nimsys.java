/*
    SUBJECT: COMP90041 PROGRAMMING AND SOFTWARE DEVELOPMENT
    PERIOD: SEMESTER 2 2020
    ASSIGNMENT: ASSIGNMENT 1
    FULL NAME: KELVIN LIM WAN
    STUDENT NUMBER: 929715
    CANVAS USERNAME: KELVINL3
*/

import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;

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
    private static final int PLAYERS_COLLECTION_CAPACITY = 100;
    private static final int MAX_PLAYERS_TO_DISPLAY = 10;

    // instance variables
    private NimPlayer[] playersCollection;
    private int playersCount;

    // constructor
    public Nimsys() {
        playersCollection = new NimPlayer[PLAYERS_COLLECTION_CAPACITY];
        playersCount = 0;
    }

    // command-line input console
    public static void console(Scanner keyboard, Nimsys system) {
        // repeat until user wants to exit game
        while (true) {
            System.out.println();

            try {
                // get user command
                System.out.print("$ ");
                String command = keyboard.next();

                switch (command) {
                    case "exit":
                        system.exit();
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
                        system.rankings(keyboard);
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
                    default:
                        throw new Exception("'" + command + "' is not a valid command.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
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
        StringTokenizer arguments = new StringTokenizer(keyboard.nextLine());
        String username = null;  // to keep compiler happy
        String familyname = null;
        String givenname = null;
        try {
            username = arguments.nextToken();
            familyname = arguments.nextToken();
            givenname = arguments.nextToken();
        } catch (NoSuchElementException nsee) {
            System.out.println("Incorrect number of arguments supplied to command.");
            return;
        }

        // check if username already exists and set index according to alphabetical order
        int index = 0;
        for (int i = 0; i < playersCount; i++) {
            if (username.equals(playersCollection[i].getUsername())) {
                System.out.println("The player already exists.");
                return;
            } else if (username.compareTo(playersCollection[i].getUsername()) > 0) {
                index++;
            } else {
                break;
            }
        }

        // rearrange players higher in alphabetical order than new player
        for (int i = playersCount; i > index; i--) {
            playersCollection[i] = playersCollection[i-1];
        }

        playersCollection[index] = new NimPlayer(username, familyname, givenname);
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

        StringTokenizer arguments = new StringTokenizer(keyboard.nextLine());
        String username = null;  // to keep compiler happy
        String newFamilyname = null;
        String newGivenname = null;
        try {
            username = arguments.nextToken();
            newFamilyname = arguments.nextToken();
            newGivenname = arguments.nextToken();
        } catch (NoSuchElementException nsee) {
            System.out.println("Incorrect number of arguments supplied to command.");
            return;
        }

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

    private void rankings(Scanner keyboard) {
        String order = keyboard.nextLine().trim();

        NimPlayer[] ranks = new NimPlayer[Math.min(playersCount, MAX_PLAYERS_TO_DISPLAY)];

        if (order.equals("asc")) {
            // loop for every entry in ranks
            for (int i = 0; i < ranks.length; i++) {
                // loop for all players in playersCollection
                NimPlayer ithPlayer = null;
                double minWinRatio = Double.MAX_VALUE;
                for (int j = 0; j < playersCount; j++) {
                    if (playersCollection[j].winRatio() < minWinRatio) {
                        // check if jth player is not in ranks
                        boolean newPlayer = true;
                        for (int k = 0; k < i; k++){
                            if (playersCollection[j].equals(ranks[k])) {
                                newPlayer = false;
                                break;
                            }
                        }
                        // if jth player is not in ranks
                        if (newPlayer) {
                            ithPlayer = playersCollection[j];
                            minWinRatio = playersCollection[j].winRatio();
                        }
                    }
                }
                ranks[i] = ithPlayer;
            }
        } else {
            // loop for every entry in ranks
            for (int i = 0; i < ranks.length; i++) {
                // loop for all players in playersCollection
                NimPlayer ithPlayer = null;
                double maxWinRatio = -Double.MAX_VALUE;
                for (int j = 0; j < playersCount; j++) {
                    if (playersCollection[j].winRatio() > maxWinRatio) {
                        // check if jth player is not in ranks
                        boolean newPlayer = true;
                        for (int k = 0; k < i; k++){
                            if (playersCollection[j].equals(ranks[k])) {
                                newPlayer = false;
                                break;
                            }
                        }
                        // if jth player is not in ranks
                        if (newPlayer) {
                            ithPlayer = playersCollection[j];
                            maxWinRatio = playersCollection[j].winRatio();
                        }
                    }
                }
                ranks[i] = ithPlayer;
            }
        }

        for (NimPlayer player: ranks) {
            System.out.printf("%-5s| %s games | %s\n", player.roundWinRatioRep(),
                    player.twoDigitGamesRep(), player.fullname());
        }
    }

    // trigger the start of the game
    private void startGame(Scanner keyboard) {

        StringTokenizer arguments = new StringTokenizer(keyboard.nextLine());
        int initNumStones = 0;  // to keep compiler happy
        int upperBound = 0;
        String username1 = null;
        String username2 = null;
        try {
            initNumStones = Integer.parseInt(arguments.nextToken());
            upperBound = Integer.parseInt(arguments.nextToken());
            username1 = arguments.nextToken();
            username2 = arguments.nextToken();
        } catch (NoSuchElementException nsee) {
            System.out.println("Incorrect number of arguments supplied to command.");
            return;
        }

        NimPlayer player1 = null;
        NimPlayer player2 = null;
        boolean exist1 = false;
        boolean exist2 = false;
        for (int i = 0; i < playersCount; i++) {
            if (username1.equals(playersCollection[i].getUsername())) {
                exist1 = true;
                player1 = playersCollection[i];
            } else if (username2.equals(playersCollection[i].getUsername())) {
                exist2 = true;
                player2 = playersCollection[i];
            }
        }

        if (exist1 && exist2) {
            NimGame game = new NimGame(initNumStones, upperBound, player1, player2);
            game.play(keyboard);
        } else {
            System.out.println("One of the players does not exist");
        }
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
