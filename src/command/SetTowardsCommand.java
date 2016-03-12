
package command;

import java.util.Arrays;
import java.util.List;
import model.IModelMap;


public class SetTowardsCommand extends Command {

    public SetTowardsCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(2);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("setTowards", Arrays.asList(getCommands().get(0).get(0),
                                                                 getCommands().get(1).get(0)));
    }

}
