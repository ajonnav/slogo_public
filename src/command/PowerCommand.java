package command;

import java.util.List;
import model.IModelMap;


public class PowerCommand extends Command {

    public PowerCommand (IModelMap modelMap, List<String> text) {
        setNumChildren(2);
    }

    @Override
    public double execute () {
        return Math.pow(getCommands().get(0).get(0).execute(),
                        getCommands().get(1).get(0).execute());
    }

}
