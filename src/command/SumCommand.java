package command;

import java.util.List;
import model.IModelMap;


public class SumCommand extends Command {

    public SumCommand (IModelMap modelMap, List<String> text) {
        setNumChildren(2);
        setTakesUnlimitedParameters(true);
    }

    @Override
    public double execute () {
        if(getCommands().get(0).size() > 1) {
            double current = getCommands().get(0).get(0).execute();
            for(int i = 1; i < getCommands().get(0).size(); i++) {
                current += getCommands().get(0).get(i).execute();
            }
            return current;
        }
        return getCommands().get(0).get(0).execute() + getCommands().get(1).get(0).execute();
    }

}
