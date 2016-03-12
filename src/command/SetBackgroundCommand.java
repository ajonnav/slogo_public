package command;

import java.util.List;
import model.IModelMap;


public class SetBackgroundCommand extends Command {


    public SetBackgroundCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().setBackgroundColorIndex(getCommands().get(0).get(0).execute());
    }

}
