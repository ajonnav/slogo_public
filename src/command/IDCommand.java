package command;

import java.util.List;
import model.IModelMap;

public class IDCommand extends Command {

    private IModelMap modelMap;

    public IDCommand (IModelMap modelMap, List<String> text) {
        setNumChildren(0);
        setTakesUnlimitedParameters(false);
        this.modelMap = modelMap;
    }
    
    @Override
    public double execute () {
        System.out.println(modelMap.getDisplay().getLastActiveID());
        return modelMap.getDisplay().getLastActiveID();
    }

}
