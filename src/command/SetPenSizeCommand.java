package command;

import java.util.List;
import model.ModelMap;


public class SetPenSizeCommand extends Command {

    private ModelMap modelMap;

    public SetPenSizeCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(1);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("setLineWidth", new double[]{getCommands().get(0).get(0).execute()});
    }

}
