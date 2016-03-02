package command;

import java.util.List;
import exception.SLogoSyntaxException;
import model.ModelMap;


public class ForCommand extends Command {

    private ModelMap modelMap;

    public ForCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(2);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        double lastValue = 0;
        List<Command> block = getCommands().get(0);
        if (block.size() != 4) {
            throw new SLogoSyntaxException("Wrong number of arguments");
        }
        if (!block.get(0).getCommandName().equals("VariableCommand")) {
            throw new SLogoSyntaxException("No Variable");
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
