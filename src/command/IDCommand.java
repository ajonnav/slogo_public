package command;

import java.util.List;
import model.IModelMap;

public class IDCommand extends Command {


    public IDCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        setNumChildren(0);
    }
    
    @Override
    public double execute () {
        return getModelMap().getDisplay().getLastActiveID();
    }

}
