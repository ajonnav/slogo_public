package command;

import java.util.List;
import model.IModelMap;


public class MinusCommand extends Command {

    public MinusCommand (IModelMap modelMap, List<List<Command>> commands) {
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return -getCommands().get(0).get(0).execute();
    }
}
