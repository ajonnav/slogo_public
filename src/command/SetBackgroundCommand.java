package command;

import java.util.List;
import model.ModelMap;


public class SetBackgroundCommand extends Command {

    private ModelMap modelMap;

    public SetBackgroundCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(1);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().setBackgroundColorIndex(getCommands().get(0).get(0).execute());
    }

}