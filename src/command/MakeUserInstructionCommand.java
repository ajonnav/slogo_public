package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.CommandsModel;

public class MakeUserInstructionCommand extends Command {
    
    private CommandsModel commandsModel;
    
    public MakeUserInstructionCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(3);
        this.commandsModel = (CommandsModel) modelMap.get("commands");
        commandsModel.setVariables(text.get(1), null);
        commandsModel.setCommands(text.get(1), null);
    }

    @Override
    public double execute () {
        CommandCommand newCommand = (CommandCommand) getCommands().get(0).get(0);
        List<Command> variables = getCommands().get(1);
        commandsModel.setVariables(newCommand.getName(), variables);
        commandsModel.setCommands(newCommand.getName(), getCommands().get(2));
        return 1;
    }
}
