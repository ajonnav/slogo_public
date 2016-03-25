package command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import exception.SLogoException;
import model.AbstractCommandsModel;
import model.IModelMap;


public class CommandCommand extends Command {

    private AbstractCommandsModel commandsModel;
    private String name;

    public CommandCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(-1);
        this.commandsModel = modelMap.getCommands();
        this.name = text.get(0);
        List<Command> variables = commandsModel.getVariables(name);
        List<Command> commands = commandsModel.getCommands(name);
        if (commands == null && variables != null) {
           commandsModel.setCommands(name, new ArrayList<Command>());
        }
        else if(commands != null) {
            setNumChildren(variables.size());
        }
    }
    
    @Override
    public double execute () {
        if (getNumChildren() != -1) {
            Map<String, Double> mapCopy = 
                    new HashMap<String, Double>(getModelMap().getVariable().getImmutableVariableMap());
            for (int i = 0; i < getNumChildren(); i++) {
                mapCopy.put(((VariableCommand) commandsModel.getVariables(name).get(i))
                                .getName(), getCommands().get(i).get(0).execute());
            }
            getModelMap().getVariable().pushScope(mapCopy);
            double returnValue = loopExecute(commandsModel.getCommands(name));
            getModelMap().getVariable().popScope();
            return returnValue;
        }
        else {
            throw new SLogoException(getErrorBundle().getString("UserCommandNotFound"));
        }
    }

    public String getName () {
        return name;
    }
}
