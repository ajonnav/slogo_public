package command;

import java.util.List;
import model.ModelMap;


public class GetPenColorCommand extends Command {

    private double index;

    public GetPenColorCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        index = modelMap.getTurtle().getPenColorIndex();
    }

    @Override
    public double execute () {
        return index;
    }
}
