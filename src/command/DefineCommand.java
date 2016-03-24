package command;

import java.util.List;
import exception.SLogoException;
import model.IModelMap;


public class DefineCommand extends Command {



    public DefineCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        setNumChildren(2);
        getModelMap().getCommands().setVariables(text.get(1), null);
        getModelMap().getCommands().setCommands(text.get(1), null);
    }
    
    @Override
    public void prepare (List<List<Command>> commands) {
        super.prepare(commands);
        if (!getCommands().get(0).get(0).getCommandName().equals("CommandCommand")) {
            throw new SLogoException(getErrorBundle().getString("CannotCreateCommand"));
        }
        List<Command> variables = getCommands().get(1);
        for (Command v : variables) {
            if (!v.getCommandName().equals("VariableCommand")) {
                throw new SLogoException(getErrorBundle().getString("NonVariableSupplied"));
            }
        }
        CommandCommand newCommand = (CommandCommand) getCommands().get(0).get(0);
        getModelMap().getCommands().setVariables(newCommand.getName(), variables);
    }


    @Override
    public double execute () {
        return 1;
    }
}
