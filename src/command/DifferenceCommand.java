package command;

import java.util.List;
import model.IModelMap;


public class DifferenceCommand extends Command {

    public DifferenceCommand (IModelMap modelMap, List<String> text) {
        setNumChildren(2);
        setTakesUnlimitedParameters(true);
    }

    @Override
    public double execute () {
        if(getCommands().get(0).size() > 1) {
            double first = getCommands().get(0).get(0).execute();
            for(int i = 1; i < getCommands().get(0).size(); i++) {
                first -= getCommands().get(0).get(i).execute();
            }
            return first;
        }
        return getCommands().get(0).get(0).execute() - getCommands().get(1).get(0).execute();
    }
}
