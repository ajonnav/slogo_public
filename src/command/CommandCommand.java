package command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import exception.SLogoSyntaxException;
import model.ICommandsModel;
import model.ModelMap;


public class CommandCommand extends Command {

    private ICommandsModel commandsModel;
    private ModelMap modelMap;
    private String name;

    public CommandCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(-1);
        this.modelMap = modelMap;
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
                    new HashMap<String, Double>(modelMap.getVariable().getImmutableVariableMap());
            for (int i = 0; i < getNumChildren(); i++) {
                mapCopy.put(((VariableCommand) commandsModel.getVariables(name).get(i))
                                .getName(), getCommands().get(i).get(0).execute());
            }
            modelMap.getVariable().pushScope(mapCopy);
            double returnValue = loopExecute(commandsModel.getCommands(name));
            modelMap.getVariable().popScope();
            return returnValue;
        }
        else {
            throw new SLogoSyntaxException("User command not found");
        }
    }

    public String getName () {
        return name;
    }
}
