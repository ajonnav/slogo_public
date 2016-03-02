package command;

import java.util.List;
import exception.SLogoSyntaxException;
import model.CommandsModel;
import model.ModelMap;

public class CommandCommand extends Command{
    
    private CommandsModel commandsModel;
    private ModelMap modelMap;
    private String name;
    
    public CommandCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(-1);
        this.modelMap = modelMap;
        this.commandsModel = modelMap.getCommands();
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
                modelMap.getVariable()
                .setVariable(((VariableCommand) commandsModel.getVariables(name).get(i)).getName(), 
                             getCommands().get(i).get(0).execute());
            }
            return loopExecute(commandsModel.getCommands(name));
        }
        else {
        	throw new SLogoSyntaxException("User command not found");
        }
    }
    
    public String getName () {
        return name;
    }
}
