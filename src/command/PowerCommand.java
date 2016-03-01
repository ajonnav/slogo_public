package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.ModelMap;


public class PowerCommand extends Command {

    public PowerCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(2);
    }

    @Override
    public double execute () {
        return Math.pow(getCommands().get(0).get(0).execute(), getCommands().get(1).get(0).execute());
    }

}
