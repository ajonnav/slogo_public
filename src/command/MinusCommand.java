package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;


public class MinusCommand extends Command {

    public MinusCommand (Map<String, Observable> modelMap, List<List<Command>> commands) {
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return -getCommands().get(0).get(0).execute();
    }
}
