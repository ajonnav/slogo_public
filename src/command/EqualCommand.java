package command;

import java.util.List;
import model.ModelMap;


public class EqualCommand extends Command {

    public EqualCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(2);
        setTakesUnlimitedParameters(true);
    }

    @Override
    public double execute () {
        if(getCommands().get(0).size() > 1) {
            double first = getCommands().get(0).get(0).execute();
            for(int i = 1; i < getCommands().get(0).size(); i++) {
                if(getCommands().get(0).get(i).execute() != first) {
                    return 0;
                }
            }
            return 1;
        }
        return getCommands().get(0).get(0).execute() == getCommands().get(1).get(0).execute() ? 1
                                                                                              : 0;
    }
}
