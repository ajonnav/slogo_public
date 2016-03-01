package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;


public class RemainderCommand extends Command {

    public RemainderCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(2);
    }

    @Override
    public double execute () {
        return getCommands().get(0).get(0).execute() % getCommands().get(1).get(0).execute();
    }

}
