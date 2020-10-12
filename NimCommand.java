/*
    SUBJECT: COMP90041 PROGRAMMING AND SOFTWARE DEVELOPMENT
    PERIOD: SEMESTER 2 2020
    ASSIGNMENT: ASSIGNMENT 2
    FULL NAME: KELVIN LIM WAN
    STUDENT NUMBER: 929715
    CANVAS USERNAME: KELVINL3
*/

public class NimCommand {

    // instance variables
    private String command;
    private String[] parameterList;

    // constructors
    public NimCommand(String command) {
        this(command, null);
    }

    public NimCommand(String command, String[] parameterList) {
        this.command = command;
        this.parameterList = parameterList;
    }

    // display command and its parameters
    public String toString() {
        String output = String.format("%-18s", command);

        // when command has no parameters
        if (parameterList == null) {
            output += "(no parameters)";

        // when command has parameters
        } else {
            for (int i = 0; i < parameterList.length; i++) {
                if (i == 0) {
                    output += "(" + parameterList[i];
                } else {
                    output += ", " + parameterList[i];
                }
            }
            output += ")";
        }

        return output;
    }

}
