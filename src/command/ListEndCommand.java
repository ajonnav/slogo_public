package command;

import java.util.List;
import model.IModelMap;


public class ListEndCommand extends Command {

    public ListEndCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
    }

    @Override
    public double execute () {
        return 0;
    }

}
