package model;

public class ModelMap implements IModelMap {

    private AbstractHistoryModel history;
    private AbstractCommandsModel commands;
    private AbstractVariableModel variable;
    private AbstractDisplayModel display;    

    public void setHistory (AbstractHistoryModel history) {
        this.history = history;
    }

    public AbstractHistoryModel getHistory () {
        return history;
    }

    public void setCommands (AbstractCommandsModel commands) {
        this.commands = commands;
    }

    public AbstractCommandsModel getCommands () {
        return commands;
    }

    public void setVariable (AbstractVariableModel variable) {
        this.variable = variable;
    }

    public AbstractVariableModel getVariable () {
        return variable;
    }

    public void setDisplay (AbstractDisplayModel display) {
        this.display = display;
    }

    public AbstractDisplayModel getDisplay () {
        return display;
    }
    
}
