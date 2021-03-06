package command;

import java.util.List;
import model.IModelMap;


public class AndCommand extends Command {

    public AndCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(2);
        setTakesUnlimitedParameters(true);
    }

    @Override
    public double execute () {
        if(getCommands().get(0).size() > 1) {
            for(int i = 0; i < getCommands().get(0).size(); i++) {
                if(getCommands().get(0).get(i).execute() == 0) {
                    return 0;
                }
            }
            return 1;
        }
        return getCommands().get(0).get(0).execute() != 0 &&
               getCommands().get(1).get(0).execute() != 0 ? 1 : 0;
    }

}
