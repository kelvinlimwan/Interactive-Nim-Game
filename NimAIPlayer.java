/*
    SUBJECT: COMP90041 PROGRAMMING AND SOFTWARE DEVELOPMENT
    PERIOD: SEMESTER 2 2020
    ASSIGNMENT: ASSIGNMENT 1
    FULL NAME: KELVIN LIM WAN
    STUDENT NUMBER: 929715
    CANVAS USERNAME: KELVINL3
*/

import java.util.StringTokenizer;

public class NimAIPlayer extends NimPlayer {

    // constant variable
    private static final String DEFAULT_NAME = "anonymous";
    private static final int MIN_STONES_TO_REMOVE = 1;
    private static final int MAX_STONES_TO_REMOVE_ADVANCED = 2;
    private static final int MAX_CROSSOVERS_FOR_SUBSET = 2;

    // instance variables
    private int numStonesLeft;
    private int maxStonesToRemove;
    private boolean symmetrical;
    private boolean subset;
    private int startIndex;
    private int endIndex;
    private int subLength;

    // constructors
    public NimAIPlayer() {
        this(DEFAULT_NAME, DEFAULT_NAME, DEFAULT_NAME);
    }

    public NimAIPlayer(String username, String familyname, String givenname) {
        super(username, familyname, givenname);
        numStonesLeft = 0;
        maxStonesToRemove = 1;
        symmetrical = false;
        subset = false;
        startIndex = 0;
        endIndex = 0;
        subLength = 0;
    }

    // setters
    public void setNumStonesLeft(int numStonesLeft) {
        this.numStonesLeft = numStonesLeft;
    }
    public void setMaxStonesToRemove(int maxStonesToRemove) {
        this.maxStonesToRemove = maxStonesToRemove;
    }
    public void setSymmetrical(boolean symmetrical) {
        this.symmetrical = symmetrical;
    }
    public void setSubset(boolean subset) {
        this.subset = subset;
    }

    @Override
    public int removeStone() {
        if ((numStonesLeft - 1) % (maxStonesToRemove + 1) != 0) {
            return (numStonesLeft - 1) % (maxStonesToRemove + 1);
        } else {
            return MIN_STONES_TO_REMOVE;
        }
    }

    @Override
    public String advancedMove(boolean[] available, String lastMove) {

        int n = available.length;

        // when player moves first
        if (lastMove == null) {

            symmetrical = true;

            // 'cut' array into equal halves by removing the middle stone(s)
            if (n % 2 == 0) {
                return n / 2 + " " + MAX_STONES_TO_REMOVE_ADVANCED;
            } else {
                return (n / 2 + 1) + " " + MIN_STONES_TO_REMOVE;
            }

        // when array is symmetrical
        } else if (symmetrical) {

            StringTokenizer move = new StringTokenizer(lastMove);
            int posPlayed = Integer.parseInt(move.nextToken());
            int numRemoved = Integer.parseInt(move.nextToken());

            // mirror opponent's previous move in other half
            if (numRemoved == MIN_STONES_TO_REMOVE) {
                return (n + 1) - posPlayed + " " + numRemoved;
            } else {
                return n - posPlayed + " " + numRemoved;
            }

        // when array contains a subset as a new game that is 'cut' into equal halves
        } else if (subset) {

            StringTokenizer move = new StringTokenizer(lastMove);
            int transPosPlayed = Integer.parseInt(move.nextToken()) - (startIndex - 1);
            int numRemoved = Integer.parseInt(move.nextToken());

            // mirror opponent's previous move in other half of subset
            if (numRemoved == MIN_STONES_TO_REMOVE) {
                return (subLength + 1) - transPosPlayed + (startIndex - 1) + " " + numRemoved;
            } else {
                return subLength - transPosPlayed + (startIndex - 1) + " " + numRemoved;
            }
        } else {

            // SUBSET ATTEMPT

            // check if subset as new game exists
            endIndex = n + 1;
            int crossovers = 0;
            boolean temp = false;
            for (int i = 0; i < n; i++) {
                if (available[i] && !temp) {
                    temp = true;
                    crossovers++;
                    startIndex = i + 1;
                } else if (!available[i] && temp) {
                    temp = false;
                    crossovers++;
                    endIndex = i + 1;
                }
            }

            // when subset of game exists
            if (crossovers <= MAX_CROSSOVERS_FOR_SUBSET) {

                subset = true;
                subLength = endIndex - startIndex;

                // 'cut' array subset into equal halves by removing the middle stone(s)
                if (subLength % 2 == 0) {
                    return subLength / 2 + (startIndex - 1) + " " + MAX_STONES_TO_REMOVE_ADVANCED;
                } else {
                    return (subLength / 2 + 1) + (startIndex - 1) + " " + MIN_STONES_TO_REMOVE;
                }
            }

            // SYMMETRICAL ATTEMPT

            int count = 0;
            int position = 0;
            boolean adjacent = false;
            boolean left = false;
            boolean right = false;

            // when array is already split in half
            if ((n % 2 == 0 && !available[n / 2 - 1] && !available[n / 2]) ||
                    (n % 2 != 0 && !available[n / 2])) {

                int mid = n / 2 - 1;  // when array length is even
                if (n % 2 != 0) {
                    mid = n /2;  // when array length is odd
                }

                // check if game can be made symmetrical with next move
                for (int i = 0; i < mid; i++) {
                    if (available[i] != available[n - i - 1]) {
                        count++;

                        // set index to remove
                        if (position == 0) {
                            position = i + 1;
                            if (available[n - i - 1]) {
                                position = n - i;
                            }
                        }


                        if (!left && !right) {
                            left = available[i];
                            right = available[n - i - 1];

                        // check if two adjacent stones can be removed
                        } else if (left == available[i] && right == available[n - i - 1]) {
                            adjacent = true;
                            if (available[n - i - 1]) {
                                position = n - i;
                            }
                        }
                    }
                }
            }

            // when removing one stone makes array symmetrical
            if (count == MIN_STONES_TO_REMOVE) {

                symmetrical = true;
                return position + " " + MIN_STONES_TO_REMOVE;

            // when removing two adjacent stones makes array symmetrical
            } else if (count == MAX_STONES_TO_REMOVE_ADVANCED && adjacent) {

                symmetrical = true;
                return position + " " + MAX_STONES_TO_REMOVE_ADVANCED;

            }

            // OPTIMISATION

            StringTokenizer move = new StringTokenizer(lastMove);
            int posPlayed = Integer.parseInt(move.nextToken());
            int numRemoved = Integer.parseInt(move.nextToken());

            // mirror opponent's move in other half if possible
            if (numRemoved == 1) {
                if (posPlayed < n / 2 + 1) {
                    for (int i = n / 2 + 1; i <= n; i++) {
                        if (available[i - 1]) {
                            return i + " " + numRemoved;
                        }
                    }
                } else {
                    for (int i = 1; i <= n / 2; i++) {
                        if (available[i - 1]) {
                            return i + " " + numRemoved;
                        }
                    }
                }
            } else {
                if (posPlayed < n / 2 + 1) {
                    for (int i = n / 2 + 1; i <= n - 1; i++) {
                        if (available[i - 1] && available[i]) {
                            return i + " " + numRemoved;
                        }
                    }
                } else {
                    for (int i = 1; i <= n / 2 - 1; i++) {
                        if (available[i - 1] && available[i]) {
                            return i + " " + numRemoved;
                        }
                    }
                }
            }

            // generate a random number between 1 and n and remove if possible
            int random;
            while (true) {
                random = (int) Math.floor(Math.random() * n);
                if (available[random]) {
                    return random + 1 + " " + MIN_STONES_TO_REMOVE;
                }
            }

        }
    }
}