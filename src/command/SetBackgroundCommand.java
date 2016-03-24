package command;

import java.util.List;
import model.IModelMap;


public class SetBackgroundCommand extends Command {


    public SetBackgroundCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().setBackgroundColorIndex(getCommands().get(0).get(0).execute());
    }

}
