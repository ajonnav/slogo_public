package command;

import java.util.List;
import model.IModelMap;

public class TurtlesCommand extends Command {

    private IModelMap modelMap;

    public TurtlesCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
    }
    
    @Override
    public double execute () {
        return modelMap.getDisplay().getNumTurtles();
    }

}
