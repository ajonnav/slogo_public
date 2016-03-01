package command;

import java.util.List;
import model.ModelMap;


public class PenUpCommand extends Command {

    private ModelMap modelMap;

    public PenUpCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        modelMap.getTurtle().penUp();
        return 0;
    }

}
