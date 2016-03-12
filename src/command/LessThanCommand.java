package command;

import java.util.List;
import model.IModelMap;


public class LessThanCommand extends Command {

    public LessThanCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(2);
        setTakesUnlimitedParameters(true);
    }

    @Override
    public double execute () {
        if(getCommands().get(0).size() > 1) {
            double current = getCommands().get(0).get(0).execute();
            for(int i = 1; i < getCommands().get(0).size(); i++) {
                double next = getCommands().get(0).get(i).execute();
                if(next > current) {
                    return 0;
                }
                current = next;
            }
            return 1;
        }
        return getCommands().get(0).get(0).execute() < getCommands().get(1).get(0).execute() ? 1
                                                                                             : 0;
    }
}
