package command;

import java.util.List;
import model.IModelMap;


public class NaturalLogCommand extends Command {

    public NaturalLogCommand (IModelMap modelMap, List<String> text) {
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return Math.log(getCommands().get(0).get(0).execute());
    }
}
