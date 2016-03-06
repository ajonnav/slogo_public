package command;

import java.util.List;
import model.ModelMap;

public class IDCommand extends Command {

    private ModelMap modelMap;

    public IDCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        setTakesUnlimitedParameters(false);
        this.modelMap = modelMap;
    }
    
    @Override
    public double execute () {
        System.out.println(modelMap.getLastActiveID());
        return modelMap.getLastActiveID();
    }

}
