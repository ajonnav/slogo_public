package command;

import java.util.List;
import java.util.ResourceBundle;

import constants.UIConstants;


public abstract class Command {

    private int numChildren = 0;
    private String name;
    private boolean takesUnlimitedParameters = false;
    private List<List<Command>> commands;
    private ResourceBundle errorBundle = ResourceBundle.getBundle(UIConstants.DEFAULT_RESOURCE + UIConstants.ERRORS);

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
    
    public ResourceBundle getErrorBundle() {
    	return errorBundle;
    }

}
