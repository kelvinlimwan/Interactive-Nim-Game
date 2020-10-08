/*
    SUBJECT: COMP90041 PROGRAMMING AND SOFTWARE DEVELOPMENT
    PERIOD: SEMESTER 2 2020
    ASSIGNMENT: ASSIGNMENT 1
    FULL NAME: KELVIN LIM WAN
    STUDENT NUMBER: 929715
    CANVAS USERNAME: KELVINL3
*/

import java.io.*;

import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;

public class Nimsys {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Nimsys system = new Nimsys(keyboard);

        File fileObject = new File(FILENAME);
        if (fileObject.exists()) {
            system.loadState();
        }

        System.out.println("Welcome to Nim");
        System.out.println();
        System.out.println("Please enter a command to continue or type 'help' for more " +
                "information");
        System.out.println();

        // start the command console
        console(keyboard, system);
    }

    // constant variables
    private static final String FILENAME = "players.dat";
    private static final String DELIMITERS = ", ";
    private static final int PLAYERS_COLLECTION_CAPACITY = 100;
    private static final int MAX_PLAYERS_TO_DISPLAY = 10;

    // instance variables
    private NimPlayer[] playersCollection;
    private int playersCount;
    private Scanner keyboard;
    private static boolean repeat;

    private enum CommandName {EXIT, ADDPLAYER, ADDAIPLAYER, REMOVEPLAYER, EDITPLAYER, RESETSTATS,
        DISPLAYPLAYER, RANKINGS, STARTGAME, STARTADVANCEDGAME, COMMANDS, HELP};

    // constructor
    public Nimsys(Scanner keyboard) {
        playersCollection = new NimPlayer[PLAYERS_COLLECTION_CAPACITY];
        playersCount = 0;
        this.keyboard = keyboard;
        repeat = true;
    }

    /*
    // getters
    public NimPlayer[] getPlayersCollection() {
        return playersCollection;
    }
    public int getPlayersCount() {
        return playersCount;
    }

    // setters
    public void setPlayersCollection(NimPlayer[] playersCollection) {
        this.playersCollection = playersCollection;
    }
    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
    }
    */

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
                        break;  // terminate application operations naturally
                    case ADDPLAYER:
                        system.addPlayer();
                        break;
                    case ADDAIPLAYER:
                        system.addAIPlayer();
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
                        system.startGame();
                        break;
                    case STARTADVANCEDGAME:
                        system.startAdvancedGame();
                        break;
                    case COMMANDS:
                        system.commands();
                        break;
                    case HELP:
                        system.help();
                        break;
                }
            } catch (IllegalArgumentException iae) {
                keyboard.nextLine();  // skip any further arguments
                System.out.printf("'%s' is not a valid command.\n", command);
            } finally {
                System.out.println();
            }
        }
    }

    private void exit() {
        saveState();
        keyboard.close();
        repeat = false;
    }

    private void addPlayer() {
        StringTokenizer arguments = new StringTokenizer(keyboard.nextLine(), DELIMITERS);
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

        playersCollection[index] = new NimHumanPlayer(username, familyname, givenname);
        playersCount++;
    }

    private void addAIPlayer() {
        StringTokenizer arguments = new StringTokenizer(keyboard.nextLine(), DELIMITERS);
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

        playersCollection[index] = new NimAIPlayer(username, familyname, givenname);
        playersCount++;
    }

    private void removePlayer() {
        String username = keyboard.nextLine().trim();
        if (username.isEmpty()) {
            System.out.println("Are you sure you want to remove all players? (y/n)");
            String answer = keyboard.next().toLowerCase();
            if (answer.equals("y")) {
                for (int i = 0; i < playersCount; i++) {
                    playersCollection[i] = null;
                }
                playersCount = 0;
            }
            // when answer is 'n', function does nothing and return the command console
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
    }

    private void editPlayer() {

        StringTokenizer arguments = new StringTokenizer(keyboard.nextLine(), DELIMITERS);
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

    private void resetStats() {
        String username = keyboard.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println("Are you sure you want to reset all player statistics? (y/n)");
            String answer = keyboard.next().toLowerCase();
            if (answer.equals("y")) {
                for (int i = 0; i < playersCount; i++) {
                    playersCollection[i].setGames(0);
                    playersCollection[i].setWins(0);
                }
            }
            // when answer is 'n', function does nothing and return the command console
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

    private void displayPlayer() {
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
            System.out.printf("%-5s| %s games | %s %s\n", player.winRatioRoundedRep(),
                    player.gamesTwoDigitRep(), player.getGivennname(), player.getFamilyname());
        }
    }

    // trigger the start of the game
    private void startGame() {

        StringTokenizer arguments = new StringTokenizer(keyboard.nextLine(), DELIMITERS);
        int initNumStones = 0;
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

        if (exist1 && exist2) {
            NimGame game = new NimGame(initNumStones, upperBound, player1, player2);
            game.play(keyboard);
        } else {
            System.out.println("One of the players does not exist.");
        }
    }

    private void startAdvancedGame() {
        StringTokenizer arguments = new StringTokenizer(keyboard.nextLine(), DELIMITERS);
        int initNumStones = 0;
        String username1 = null;
        String username2 = null;
        try {
            initNumStones = Integer.parseInt(arguments.nextToken());
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

        if (exist1 && exist2) {
            NimAdvancedGame advancedGame = new NimAdvancedGame(initNumStones, player1, player2);
            advancedGame.play(keyboard);
        } else {
            System.out.println("One of the players does not exist.");
        }
    }

    // display all commands available to user
    private void commands() {
        // create an array containing all commands of type NimCommand
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
        // TODO: check parameter upperbound in startadvancedgame
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

    private void loadState() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILENAME));
            NimState state = (NimState) inputStream.readObject();

            playersCollection = state.getPlayersCollection();
            playersCount = state.getPlayersCount();
            inputStream.close();

        } catch (FileNotFoundException fnfe) {
            System.out.println("Could not find file!");
        } catch (IOException ioe) {
            System.out.println("Could not read from file!");
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Could not find class!");
        }
    }

    private void saveState() {
        try {
            ObjectOutputStream outputStream =
                    new ObjectOutputStream(new FileOutputStream(FILENAME));
            NimState state = new NimState(playersCollection, playersCount);

            outputStream.writeObject(state);
            outputStream.close();

        } catch (FileNotFoundException fnfe) {
            System.out.println("Could not find file!");
        } catch (IOException ioe) {
            System.out.println("Could not read from file!");
        }
    }
}
