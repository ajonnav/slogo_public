package command;

import java.util.List;
import model.ModelMap;


public class RightCommand extends Command {

    private ModelMap modelMap;

    public RightCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(1);
        this.modelMap = modelMap;
    }

    public double execute () {
        double deg = getCommands().get(0).get(0).execute();
        modelMap.getTurtle().turn(deg);
        return deg;
    }
}
