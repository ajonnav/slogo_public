package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;


public class SineCommand extends Command {

    public SineCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return Math.sin(Math.toRadians(getCommands().get(0).get(0).execute()));
    }

}
