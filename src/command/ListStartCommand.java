package command;

import java.util.List;
import model.IModelMap;


public class ListStartCommand extends Command {

    public ListStartCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
    }

    @Override
    public double execute () {
        return 0;
    }

}
