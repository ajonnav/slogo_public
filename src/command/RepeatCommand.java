package command;

import java.util.List;
import model.IModelMap;


public class RepeatCommand extends Command {


    public RepeatCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(2);
    }

    @Override
    public double execute () {
        double lastValue = 0;
        for (int i = 0; i < getCommands().get(0).get(0).execute(); i++) {
            getModelMap().getVariable().setVariable(":repcount", i + 1);
            lastValue = loopExecute(getCommands().get(1));
        }
        return lastValue;
    }

}
