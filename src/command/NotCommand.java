package command;

import java.util.List;
import model.IModelMap;


public class NotCommand extends Command {

    public NotCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return getCommands().get(0).get(0).execute() == 0 ? 1 : 0;
    }

}
