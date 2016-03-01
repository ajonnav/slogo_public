package command;

import java.util.List;
import model.ModelMap;


public class LeftCommand extends Command {

    private ModelMap modelMap;

    public LeftCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(1);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        double deg = getCommands().get(0).get(0).execute();
        modelMap.getTurtle().turn(-deg);
        return deg;
    }
    
}
