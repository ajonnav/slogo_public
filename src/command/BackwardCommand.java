package command;

import java.util.List;
import model.ModelMap;


public class BackwardCommand extends Command {

    private ModelMap modelMap;

    public BackwardCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(1);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return -modelMap.TurtleAction("forward", new double[]{-getCommands().get(0).get(0).execute()});
    }
}
