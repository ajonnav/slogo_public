
package command;

import java.util.List;
import model.ModelMap;


public class SetTowardsCommand extends Command {

    private ModelMap modelMap;

    public SetTowardsCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(2);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.TurtleAction("setTowards",
                                     new double[] { getCommands().get(0).get(0).execute(),
                                                    getCommands().get(1).get(0).execute() });
    }

}
