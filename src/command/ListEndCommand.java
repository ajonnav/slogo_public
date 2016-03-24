package command;

import java.util.List;
import model.IModelMap;


public class ListEndCommand extends Command {

    public ListEndCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
    }

    @Override
    public double execute () {
        return 0;
    }

}
