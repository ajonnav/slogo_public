package command;

import java.util.Arrays;
import java.util.List;
import model.ModelMap;


public class SetShapeCommand extends Command {

    private ModelMap modelMap;

    public SetShapeCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(1);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("setImageIndex", Arrays.asList(getCommands().get(0).get(0)));
    }

}
