package command;

import java.util.List;
import model.IModelMap;

public class IDCommand extends Command {

    private IModelMap modelMap;

    public IDCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(0);
        setTakesUnlimitedParameters(false);
    }
    
    @Override
    public double execute () {
        System.out.println(modelMap.getDisplay().getLastActiveID());
        return modelMap.getDisplay().getLastActiveID();
    }

}
