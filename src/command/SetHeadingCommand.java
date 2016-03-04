package command;

import java.util.List;
import model.ModelMap;


public class SetHeadingCommand extends Command {

    private ModelMap modelMap;

    public SetHeadingCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(1);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("setHeading", new double[]{getCommands().get(0).get(0).execute()});
    }

}
