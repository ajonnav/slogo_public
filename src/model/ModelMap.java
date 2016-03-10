package model;

public class ModelMap {

    private HistoryPaneModel history;
    private CommandsModel commands;
    private VariableModel variable;
    private DisplayModel display;    

    public void setHistory (HistoryPaneModel history) {
        this.history = history;
    }

    public HistoryPaneModel getHistory () {
        return history;
    }

    public void setCommands (CommandsModel commands) {
        this.commands = commands;
    }

    public CommandsModel getCommands () {
        return commands;
    }

    public void setVariable (VariableModel variable) {
        this.variable = variable;
    }

    public VariableModel getVariable () {
        return variable;
    }

    public void setDisplay (DisplayModel display) {
        this.display = display;
    }

    public DisplayModel getDisplay () {
        return display;
    }
}
