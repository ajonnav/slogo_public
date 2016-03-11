package command;

import java.util.List;
import exception.SLogoSyntaxException;
import model.ICommandsModel;
import model.ModelMap;


public class MakeUserInstructionCommand extends Command {

    private ICommandsModel commandsModel;

    public MakeUserInstructionCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(3);
        this.commandsModel = modelMap.getCommands();
    }

    @Override
    public void prepare (List<List<Command>> commands) {
        super.prepare(commands);
        if (!commands.get(0).get(0).getCommandName().equals("CommandCommand")) {
            throw new SLogoSyntaxException("This command cannot be created");
        }
        List<Command> variables = commands.get(1);
        for (Command v : variables) {
            if (!v.getCommandName().equals("VariableCommand")) {
                throw new SLogoSyntaxException("Non-variable supplied in variable list");
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
