package model;

public class ModelMap implements IModelMap {

    private IHistoryModel history;
    private ICommandsModel commands;
    private IVariableModel variable;
    private DisplayModel display;    

    public void setHistory (IHistoryModel history) {
        this.history = history;
    }

    public IHistoryModel getHistory () {
        return history;
    }

    public void setCommands (ICommandsModel commands) {
        this.commands = commands;
    }

    public ICommandsModel getCommands () {
        return commands;
    }

    public void setVariable (IVariableModel variable) {
        this.variable = variable;
    }

    public IVariableModel getVariable () {
        return variable;
    }

    public void setDisplay (DisplayModel display) {
        this.display = display;
    }

    public DisplayModel getDisplay () {
        return display;
    }
    
}
