package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;


public class TangentCommand extends Command {

    public TangentCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return Math.tan(Math.toRadians(getCommands().get(0).get(0).execute()));
    }
}
