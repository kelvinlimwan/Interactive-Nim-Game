/*
    SUBJECT: COMP90041 PROGRAMMING AND SOFTWARE DEVELOPMENT
    PERIOD: SEMESTER 2 2020
    ASSIGNMENT: ASSIGNMENT 1
    FULL NAME: KELVIN LIM WAN
    STUDENT NUMBER: 929715
    CANVAS USERNAME: KELVINL3
*/

public class NimCommand {

    // instance variables
    private String command;
    private String[] parameterList;

    // constructors
    public NimCommand() {
        command = null;
        parameterList = null;
    }

    public NimCommand(String command) {
        this.command = command;
        parameterList = null;
    }

    public NimCommand(String command, String[] parameterList) {
        this.command = command;
        this.parameterList = new String[parameterList.length];
        for (int i = 0; i < parameterList.length; i++) {
            this.parameterList[i] = parameterList[i];
        }
    }

    // getters
    public String getCommand() {
        return command;
    }

    public String[] getParameterList() {
        String[] output = new String[parameterList.length];
        for (int i = 0; i < parameterList.length; i++) {
            output[i] = parameterList[i];
        }
        return output;

    }

    // setters
    public void setCommand(String command) {
        this.command = command;
    }

    public void setParameterList(String[] parameterList) {
        this.parameterList = new String[parameterList.length];
        for (int i = 0; i < parameterList.length; i++) {
            this.parameterList[i] = parameterList[i];
        }
    }

    public String toString() {
        String output = String.format("%-18s", command);

        if (parameterList == null) {
            output += "(no parameter)";
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
