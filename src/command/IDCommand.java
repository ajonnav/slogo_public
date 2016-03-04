package command;

import java.util.List;
import model.ModelMap;

public class IDCommand extends Command {

    private ModelMap modelMap;

    public IDCommand (ModelMap modelMap, List<String> text) {
        this.modelMap = modelMap;
    }
    
    @Override
    public double execute () {
        return modelMap.getLastActiveID();
    }

}
