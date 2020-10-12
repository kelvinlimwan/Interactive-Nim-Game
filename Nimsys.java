/*
    SUBJECT: COMP90041 PROGRAMMING AND SOFTWARE DEVELOPMENT
    PERIOD: SEMESTER 2 2020
    ASSIGNMENT: ASSIGNMENT 2
    FULL NAME: KELVIN LIM WAN
    STUDENT NUMBER: 929715
    CANVAS USERNAME: KELVINL3
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;

public class Nimsys {

    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);
        Nimsys system = new Nimsys(keyboard);
        File fileObject = new File(FILENAME);

        // load previous game's contents if file exists
        if (fileObject.exists()) {
            system.loadState();
        }

        System.out.println("Welcome to Nim");
        System.out.println();
        System.out.println("Please enter a command to continue or type 'help' for more " +
                "information");
        System.out.println();

        // launch command console
        console(keyboard, system);
    }

    // constants
    private static final String FILENAME = "players.dat";
    private final String DELIMITERS = ", ";
    private final int PLAYERS_COLLECTION_CAPACITY = 100;
    private final int MAX_PLAYERS_TO_DISPLAY = 10;

    // instance variables
    private NimPlayer[] playersCollection;
    private int playersCount;
    private static boolean repeat;  // whether to repeat the game or not
    private final Scanner keyboard;

    private enum CommandName {EXIT, ADDPLAYER, ADDAIPLAYER, REMOVEPLAYER, EDITPLAYER, RESETSTATS,
        DISPLAYPLAYER, RANKINGS, STARTGAME, STARTADVANCEDGAME, COMMANDS, HELP};

    // constructor
    public Nimsys(Scanner keyboard) {
        playersCollection = new NimPlayer[PLAYERS_COLLECTION_CAPACITY];
        playersCount = 0;
        repeat = true;
        this.keyboard = keyboard;
    }

    // command-line input console
    public static void console(Scanner keyboard, Nimsys system) {

        // repeat until user wants to exit game
        while (repeat) {

            // get user command
            System.out.print("$ ");
            String command = keyboard.next();

            try {
                CommandName commandName = CommandName.valueOf(command.toUpperCase());
                switch (commandName) {
                    case EXIT:
                        system.exit();
                        break;
                    case ADDPLAYER:
                        system.addPlayer("human");
                        break;
                    case ADDAIPLAYER:
                        system.addPlayer("ai");
                        break;
                    case REMOVEPLAYER:
                        system.removePlayer();
                        break;
                    case EDITPLAYER:
                        system.editPlayer();
                        break;
                    case RESETSTATS:
                        system.resetStats();
                        break;
                    case DISPLAYPLAYER:
                        system.displayPlayer();
                        break;
                    case RANKINGS:
                        system.rankings();
                        break;
                    case STARTGAME:
                        system.startGame("game");
                        break;
                    case STARTADVANCEDGAME:
                        system.startGame("advanced");
                        break;
                    case COMMANDS:
                        system.commands();
                        break;
                    case HELP:
                        system.help();
                        break;
                }

            } catch (IllegalArgumentException e) {
                keyboard.nextLine();  // discard any further arguments
                System.out.printf("'%s' is not a valid command.\n", command);
            } finally {
                System.out.println();
            }
        }
    }

    // exit the program
    private void exit() {
        saveState();  // save current game's contents
        keyboard.close();
        repeat = false;  // to terminate program naturally
    }

    // add player to the system
    private void addPlayer(String playerType) {

        StringTokenizer arguments = new StringTokenizer(keyboard.nextLine(), DELIMITERS);
        String username;
        String familyname;
        String givenname;

        // get user inputs
        try {
            username = arguments.nextToken().toLowerCase();
            familyname = arguments.nextToken();
            givenname = arguments.nextToken();

        } catch (NoSuchElementException e) {
            System.out.println("Incorrect number of arguments supplied to command.");
            return;  // return to command console
        }

        /* check if player already exists; if not, find new player's appropriate index in the
        collection (in alphabetical order) */
        int index = 0;
        for (int i = 0; i < playersCount; i++) {
            if (username.equals(playersCollection[i].getUsername())) {
                System.out.println("The player already exists.");
                return;  // return to command console
            } else if (username.compareTo(playersCollection[i].getUsername()) > 0) {
                index++;
            } else {
                break;  // break when search is beyond username in alphabetical order
            }
        }

        // increment players 'higher' than new player in the alphabetical order
        for (int i = playersCount; i > index; i--) {
            playersCollection[i] = playersCollection[i-1];
        }

        // create new player of appropriate type and add to the collection at appropriate index
        if (playerType.equals("human")) {
            playersCollection[index] = new NimHumanPlayer(username, familyname, givenname);
        } else {
            playersCollection[index] = new NimAIPlayer(username, familyname, givenname);
        }

        playersCount++;
    }

    // remove player(s) from the system
    private void removePlayer() {

        String username = keyboard.nextLine().trim();

        // when no username is given
        if (username.isEmpty()) {

            // get user confirmation
            System.out.println("Are you sure you want to remove all players? (y/n)");
            String answer = keyboard.next().toLowerCase();

            if (answer.equals("y")) {

                // remove all players
                for (int i = 0; i < playersCount; i++) {
                    playersCollection[i] = null;
                }
                playersCount = 0;
            }
            // when answer is 'n', function naturally returns to command console

        // when a username is given
        } else {

            username = username.toLowerCase();

            /* check if player exists; if so, decrement players 'higher' than player to be removed
            in the alphabetical order */
            boolean exist = false;
            for (int i = 0; i < playersCount; i++) {
                if (username.equals(playersCollection[i].getUsername()) || exist) {
                    if (i < playersCount - 1) {
                        playersCollection[i] = playersCollection[i + 1];
                        if (!exist) {
                            exist = true;
                        }

                    // for last player in collection
                    } else {
                        playersCollection[i] = null;
                        playersCount--;
                        return;  // return to command console
                    }
                }
            }

            System.out.println("The player does not exist.");

        }
    }

    // edit player's given name and family name
    private void editPlayer() {

        StringTokenizer arguments = new StringTokenizer(keyboard.nextLine(), DELIMITERS);
        String username;
        String newFamilyname;
        String newGivenname;

        // get user inputs
        try {
            username = arguments.nextToken().toLowerCase();
            newFamilyname = arguments.nextToken();
            newGivenname = arguments.nextToken();
        } catch (NoSuchElementException e) {
            System.out.println("Incorrect number of arguments supplied to command.");
            return;
        }

        // check if player exists; if so, set new given name and new family name
        for (int i = 0; i < playersCount; i++) {
            if (username.equals(playersCollection[i].getUsername())) {
                playersCollection[i].setFamilyname(newFamilyname);
                playersCollection[i].setGivenname(newGivenname);
                return;  // return to command console
            }
        }

        System.out.println("The player does not exist.");

    }

    // reset player(s) stats for number of wins and number of games played
    private void resetStats() {

        String username = keyboard.nextLine().trim();

        // when no username is given
        if (username.isEmpty()) {

            // get user confirmation
            System.out.println("Are you sure you want to reset all player statistics? (y/n)");
            String answer = keyboard.next().toLowerCase();

            if (answer.equals("y")) {

                // reset all players' stats
                for (int i = 0; i < playersCount; i++) {
                    playersCollection[i].setGames(0);
                    playersCollection[i].setWins(0);
                }
            }
            // when answer is 'n', function naturally returns to command console

        // when a username is given
        } else {

            username = username.toLowerCase();

            // check if player exists; if so, reset its stats
            for (int i = 0; i < playersCount; i++) {
                if (username.equals(playersCollection[i].getUsername())) {
                    playersCollection[i].setGames(0);
                    playersCollection[i].setWins(0);
                    return;  // return to command console
                }
            }

            System.out.println("The player does not exist.");

        }
    }

    // display player(s) information
    private void displayPlayer() {

        String username = keyboard.nextLine().trim();

        // when no username is given
        if (username.isEmpty()) {

            // display all players' information
            for (int i = 0; i < playersCount; i++) {
                System.out.println(playersCollection[i]);
            }

        // when a username is given
        } else {

            username = username.toLowerCase();

            // check if player exists; if so, display its information
            for (int i = 0; i < playersCount; i++) {
                if (username.equals(playersCollection[i].getUsername())) {
                    System.out.println(playersCollection[i]);
                    return;  // return to command console
                }
            }

            System.out.println("The player does not exist.");

        }
    }

    // display player rankings in ascending or descending order
    private void rankings() {

        String order = keyboard.nextLine().trim();

        // create an output array of appropriate capacity
        NimPlayer[] ranks = new NimPlayer[Math.min(playersCount, MAX_PLAYERS_TO_DISPLAY)];

        // iterate through every entry in output array
        for (int i = 0; i < ranks.length; i++) {

            NimPlayer ithPlayer = null;

            /* set comparable win ratio to lowest by default and if user prompts for ascending
            order, set comparable win ratio to highest */
            double compWinRatio = - Double.MAX_VALUE;
            if (order.equals("asc")) {
                compWinRatio = Double.MAX_VALUE;
            }

            /* iterate through all players in playersCollection to set the ith player to have
            the maximum (default) or minimum (if ascending order) win ratio amongst all players that
            are not already in the output array */
            for (int j = 0; j < playersCount; j++) {
                if ((!order.equals("asc") && playersCollection[j].winRatio() > compWinRatio) ||
                        (order.equals("asc") && playersCollection[j].winRatio() < compWinRatio)) {

                    // check if jth player is not in output array
                    boolean newPlayer = true;
                    for (int k = 0; k < i; k++){
                        if (playersCollection[j].equals(ranks[k])) {
                            newPlayer = false;
                            break;
                        }
                    }

                    // when jth player is not in output array
                    if (newPlayer) {
                        ithPlayer = playersCollection[j];
                        compWinRatio = playersCollection[j].winRatio();
                    }
                }
            }
            ranks[i] = ithPlayer;
        }

        // display players' information in appropriate order
        for (NimPlayer player: ranks) {
            System.out.printf("%-5s| %s games | %s %s\n", player.winRatioRoundedRep(),
                    player.gamesTwoDigitRep(), player.getGivennname(), player.getFamilyname());

        }
    }

    // trigger the start of a game
    private void startGame(String gameType) {

        StringTokenizer arguments = new StringTokenizer(keyboard.nextLine(), DELIMITERS);
        int initNumStones;
        int upperBound = 0;  // to satisfy compiler
        String username1;
        String username2;

        try {
            initNumStones = Integer.parseInt(arguments.nextToken());

            // only prompt for upper bound argument for normal game
            if (gameType.equals("game")) {
                upperBound = Integer.parseInt(arguments.nextToken());
            }

            username1 = arguments.nextToken().toLowerCase();
            username2 = arguments.nextToken().toLowerCase();

        } catch (NoSuchElementException e) {
            System.out.println("Incorrect number of arguments supplied to command.");
            return;  // return to command console
        }

        // check if both players exist
        NimPlayer player1 = null;
        NimPlayer player2 = null;
        boolean exist1 = false;
        boolean exist2 = false;
        for (int i = 0; i < playersCount; i++) {
            if (exist1 && exist2) {
                break;
            } else if (username1.equals(playersCollection[i].getUsername())) {
                exist1 = true;
                player1 = playersCollection[i];
            } else if (username2.equals(playersCollection[i].getUsername())) {
                exist2 = true;
                player2 = playersCollection[i];
            }
        }

        // when both players exist, transfer control to appropriate game class
        if (exist1 && exist2) {
            NimBaseGame game;
            if (gameType.equals("game")) {
                game = new NimGame(initNumStones, upperBound, player1, player2);
            } else {
                game = new NimAdvancedGame(initNumStones, player1, player2);
            }
            game.play(keyboard);
        } else {
            System.out.println("One of the players does not exist.");
        }

    }

    // display all commands available to user
    private void commands() {

        // create array containing all commands of type NimCommand
        NimCommand[] commands = new NimCommand[12];
        commands[0] = new NimCommand("exit");
        commands[1] = new NimCommand("addplayer", new String[] {"username", "secondname",
                "firstname"});
        commands[2] = new NimCommand("addaiplayer", new String[] {"username", "secondname",
                "firstname"});
        commands[3] = new NimCommand("removeplayer", new String[] {"optional username"});
        commands[4] = new NimCommand("editplayer", new String[] {"username", "secondname",
                "firstname"});
        commands[5] = new NimCommand("resetstats", new String[] {"optional username"});
        commands[6] = new NimCommand("displayplayer", new String[] {"optional username"});
        commands[7] = new NimCommand("rankings", new String[] {"optional asc"});
        commands[8] = new NimCommand("startgame", new String[] {"initialstones",
                "upperbound", "username1", "username2"});
        // TODO: include upperbound parameter?
        commands[9] = new NimCommand("startadvancedgame", new String[] {"initialstones",
                "username1", "username2"});
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

    // load state of previous game from players.dat
    private void loadState() {

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILENAME));

            NimState state = (NimState) inputStream.readObject();

            // set collection to previous game's players collection information
            playersCollection = state.getPlayersCollection();
            playersCount = state.getPlayersCount();

            inputStream.close();

        } catch (FileNotFoundException e) {
            System.out.println("Could not find file!");
        } catch (IOException e) {
            System.out.println("Could not read from file!");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not find class!");
        }
    }

    // save state of current game to players.dat
    private void saveState() {

        try {
            ObjectOutputStream outputStream =
                    new ObjectOutputStream(new FileOutputStream(FILENAME));

            // create object containing current game's collection information
            NimState state = new NimState(playersCollection, playersCount);

            outputStream.writeObject(state);

            outputStream.close();

        } catch (FileNotFoundException e) {
            System.out.println("Could not find file!");
        } catch (IOException e) {
            System.out.println("Could not read from file!");
        }
    }

}
