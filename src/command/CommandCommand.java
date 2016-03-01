package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.CommandsModel;
import model.VariableModel;

public class CommandCommand extends Command{
    
    private CommandsModel commandsModel;
    private Map<String, Observable> modelMap;
    private String name;
    
    public CommandCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(-1);
        this.modelMap = modelMap;
        this.commandsModel = (CommandsModel) modelMap.get("commands");
        this.name = text.get(0);
        List<Command> variables = commandsModel.getVariables(name);
        if(variables != null) {
            setNumChildren(variables.size());
        }
    }
    
    @Override
    public double execute () {
        if(getNumChildren() != -1) {       
            for(int i = 0; i < getNumChildren(); i++) {
                ((VariableModel) modelMap.get("variables"))
                .setVariable(((VariableCommand) commandsModel.getVariables(name).get(i)).getName(), 
                             getCommands().get(i).get(0).execute());
            }
            return loopExecute(commandsModel.getCommands(name));
        }
        return 0;
    }
    
    public String getName () {
        return name;
    }
}
