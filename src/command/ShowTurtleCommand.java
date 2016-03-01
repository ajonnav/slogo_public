package command;

import java.util.List;
import model.ModelMap;


public class ShowTurtleCommand extends Command {

    private ModelMap modelMap;

    public ShowTurtleCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        modelMap.getTurtle().show();
        return 1;
    }

}
