package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.VariableModel;


public class ForCommand extends Command {

    private Map<String, Observable> modelMap;

    public ForCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(2);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        double lastValue = 0;
        List<Command> block = getCommands().get(0);
        double start = block.get(1).execute();
        double end = block.get(2).execute();
        double increment = block.get(3).execute();
        for (double i = start; i <= end; i += increment) {
            ((VariableModel) modelMap.get("variables"))
            .setVariable(((VariableCommand) block.get(0)).getName(), i);
            lastValue = loopExecute(getCommands().get(1));
        }
        return lastValue;
    }
}
