package command;

import java.util.List;
import model.ModelMap;


public class GetShapeCommand extends Command {

    private double index;

    public GetShapeCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        index = modelMap.getTurtle().getImageIndex();
    }

    @Override
    public double execute () {
        return index;
    }
}
