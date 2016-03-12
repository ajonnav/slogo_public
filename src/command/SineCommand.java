package command;

import java.util.List;
import model.IModelMap;


public class SineCommand extends Command {

    public SineCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(1);
        setTakesUnlimitedParameters(false);
    }

    @Override
    public double execute () {
        return Math.sin(Math.toRadians(getCommands().get(0).get(0).execute()));
    }

}
