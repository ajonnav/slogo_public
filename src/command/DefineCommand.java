package command;

import java.util.List;
import exception.SLogoSyntaxException;
import model.IModelMap;


public class DefineCommand extends Command {

    private IModelMap modelMap;

    public DefineCommand (IModelMap modelMap, List<String> text) {
        setNumChildren(2);
        this.modelMap = modelMap;
        modelMap.getCommands().setVariables(text.get(1), null);
        modelMap.getCommands().setCommands(text.get(1), null);
    }
    
    @Override
    public void prepare (List<List<Command>> commands) {
        super.prepare(commands);
        if (!getCommands().get(0).get(0).getCommandName().equals("CommandCommand")) {
            throw new SLogoSyntaxException("This command cannot be created");
        }
        List<Command> variables = getCommands().get(1);
        for (Command v : variables) {
            if (!v.getCommandName().equals("VariableCommand")) {
                throw new SLogoSyntaxException("Non-variable supplied in variable list");
            }
        }
        CommandCommand newCommand = (CommandCommand) getCommands().get(0).get(0);
        modelMap.getCommands().setVariables(newCommand.getName(), variables);
    }


    @Override
    public double execute () {
        return 1;
    }
}
