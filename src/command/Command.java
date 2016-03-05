package command;

import java.util.List;


public abstract class Command {

    private int numChildren = 0;
    private String name;
    private boolean takesUnlimitedParameters = false;
    private List<List<Command>> commands;

    public abstract double execute ();

    public void prepare (List<List<Command>> commands) {
        this.commands = commands;
        this.name = this.getClass().getSimpleName();
    }

    public int getNumChildren () {
        return numChildren;
    }

    public void setNumChildren (int numChildren) {
        this.numChildren = numChildren;
    }

    public List<List<Command>> getCommands () {
        return commands;
    }

    public String getCommandName () {
        return name;
    }

    public double loopExecute (List<Command> commands) {
        double lastValue = 0;
        for (int i = 0; i < commands.size(); i++) {
            lastValue = commands.get(i).execute();
        }
        return lastValue;
    }
    
    public boolean takesUnlimitedParameters () {
        return takesUnlimitedParameters;
    }

    public void setTakesUnlimitedParameters (boolean takesUnlimitedParameters) {
        this.takesUnlimitedParameters = takesUnlimitedParameters;
    }

}
