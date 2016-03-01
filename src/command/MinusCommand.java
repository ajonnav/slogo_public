package command;

import java.util.List;
import model.ModelMap;


public class MinusCommand extends Command {

    public MinusCommand (ModelMap modelMap, List<List<Command>> commands) {
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return -getCommands().get(0).get(0).execute();
    }
}
