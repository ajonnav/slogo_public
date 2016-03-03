package model;

public class ModelMap {

    TurtleModel turtle;
    HistoryPaneModel history;
    CommandsModel commands;
    VariableModel variable;
    DisplayModel display;

    public void setTurtle (TurtleModel turtle) {
        this.turtle = turtle;
    }

    public TurtleModel getTurtle () {
        return turtle;
    }

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
