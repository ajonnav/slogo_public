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
    public void prepare (List<List<Command>> commands) {
        super.prepare(commands);
        CommandCommand newCommand = (CommandCommand) commands.get(0).get(0);
        List<Command> variables = commands.get(1);
        commandsModel.setVariables(newCommand.getName(), variables);
        commandsModel.setCommands(newCommand.getName(), commands.get(2));
    }
    
    @Override
    public double execute () {
        return 1;
    }
}
