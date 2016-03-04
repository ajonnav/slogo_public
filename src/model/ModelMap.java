package model;
import java.util.Map;


public class ModelMap {

    private HistoryPaneModel history;
    private CommandsModel commands;
    private VariableModel variable;
    private DisplayModel display;    

    public ModelMap (Map<Double, String> colorMap, Map<Double, String> imageMap) {
        
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
