package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.VariableModel;


public class RepeatCommand extends Command {

    private Map<String, Observable> modelMap;

    public RepeatCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(2);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        double lastValue = 0;
        for (int i = 0; i < getCommands().get(0).get(0).execute(); i++) {
            ((VariableModel) modelMap.get("variables")).setVariable(":repcount", i+1);
            lastValue = loopExecute(getCommands().get(1));
        }
        return lastValue;
    }

}
