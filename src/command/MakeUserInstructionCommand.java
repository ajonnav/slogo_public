package command;

import java.util.List;

import model.CommandsModel;
import model.ModelMap;

public class MakeUserInstructionCommand extends Command {
    
    private CommandsModel commandsModel;
    
    public MakeUserInstructionCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(3);
        this.commandsModel = modelMap.getCommands();
        commandsModel.setVariables(text.get(1), null);
        commandsModel.setCommands(text.get(1), null);
    }
    
    @Override
    public void prepare (List<List<Command>> commands) {
        super.prepare(commands);
        CommandCommand newCommand = (CommandCommand) getCommands().get(0).get(0);
        List<Command> variables = getCommands().get(1);
        commandsModel.setVariables(newCommand.getName(), variables);
        commandsModel.setCommands(newCommand.getName(), getCommands().get(2));
    }
    
    @Override
    public double execute () {
        return 1;
    }
}
