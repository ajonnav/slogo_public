package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.VariableModel;


public class DoTimesCommand extends Command {

    private Map<String, Observable> modelMap;

    public DoTimesCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(2);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        double lastValue = 0;
        for (double i = 1; i <= getCommands().get(0).get(1).execute(); i++) {
            ((VariableModel) modelMap.get("variables"))
            .setVariable(((VariableCommand) getCommands().get(0).get(0)).getName(), i);
            lastValue = loopExecute(getCommands().get(1));
        }
        return lastValue;
    }

}
