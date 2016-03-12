
package command;

import java.util.Arrays;
import java.util.List;
import model.IModelMap;


public class SetPositionCommand extends Command {



    public SetPositionCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(2);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("setPosition", Arrays.asList(getCommands().get(0).get(0), 
                                                                  getCommands().get(1).get(0)));
    }

}
