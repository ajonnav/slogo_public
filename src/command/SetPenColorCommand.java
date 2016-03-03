package command;

import java.util.List;
import model.ModelMap;


public class SetPenColorCommand extends Command {

    private ModelMap modelMap;

    public SetPenColorCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(1);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        double index = getCommands().get(0).get(0).execute();
        modelMap.getTurtle().setPenColorIndex(index);
        return index;
    }

}
