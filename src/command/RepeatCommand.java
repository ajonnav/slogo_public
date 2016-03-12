package command;

import java.util.List;
import model.IModelMap;


public class RepeatCommand extends Command {

    private IModelMap modelMap;

    public RepeatCommand (IModelMap modelMap, List<String> text) {
        setNumChildren(2);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        double lastValue = 0;
        for (int i = 0; i < getCommands().get(0).get(0).execute(); i++) {
            modelMap.getVariable().setVariable(":repcount", i + 1);
            lastValue = loopExecute(getCommands().get(1));
        }
        return lastValue;
    }

}
