package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;


public class ArcTangentCommand extends Command {

    public ArcTangentCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return Math.atan(Math.toRadians(getCommands().get(0).get(0).execute()));
    }
}
