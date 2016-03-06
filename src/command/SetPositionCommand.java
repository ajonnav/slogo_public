
package command;

import java.util.Arrays;
import java.util.List;
import model.ModelMap;


public class SetPositionCommand extends Command {

    private ModelMap modelMap;

    public SetPositionCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(2);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.TurtleAction("setPosition", Arrays.asList(getCommands().get(0).get(0), 
                                                                  getCommands().get(1).get(0)));
    }

}
