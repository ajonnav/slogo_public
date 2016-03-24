package command;

import java.util.List;
import exception.SLogoException;
import model.ICommandsModel;
import model.IModelMap;


public class MakeUserInstructionCommand extends Command {

    private ICommandsModel commandsModel;

    public MakeUserInstructionCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        setNumChildren(3);
        this.commandsModel = modelMap.getCommands();
    }

    @Override
    public void prepare (List<List<Command>> commands) {
        super.prepare(commands);
        if (!commands.get(0).get(0).getCommandName().equals("CommandCommand")) {
            throw new SLogoException(getErrorBundle().getString("CannotCreateCommmand"));
        }
        List<Command> variables = commands.get(1);
        for (Command v : variables) {
            if (!v.getCommandName().equals("VariableCommand")) {
                throw new SLogoException(getErrorBundle().getString("NonVariableSupplied"));
            }
        }
        CommandCommand newCommand = (CommandCommand) commands.get(0).get(0);
        commandsModel.setVariables(newCommand.getName(), variables);
        commandsModel.setCommands(newCommand.getName(), commands.get(2));
    }

    @Override
    public double execute () {
        return 1;
    }
}
