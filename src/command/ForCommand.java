package command;

import java.util.List;
import exception.SLogoException;
import model.IModelMap;


public class ForCommand extends Command {

    private IModelMap modelMap;

    public ForCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(2);
    }

    @Override
    public double execute () {
        double lastValue = 0;
        List<Command> block = getCommands().get(0);
        if (block.size() != 4) {
            throw new SLogoException(getErrorBundle().getString("WrongNumberArguments"));
        }
        if (!block.get(0).getCommandName().equals("VariableCommand")) {
            throw new SLogoException(getErrorBundle().getString("NoVariable"));
        }
        double start = block.get(1).execute();
        double end = block.get(2).execute();
        double increment = block.get(3).execute();
        for (double i = start; i <= end; i += increment) {
            modelMap.getVariable()
                    .setVariable(((VariableCommand) block.get(0)).getName(), i);
            lastValue = loopExecute(getCommands().get(1));
        }
        return lastValue;
    }
}
