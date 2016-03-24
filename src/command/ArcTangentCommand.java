package command;

import java.util.List;
import model.IModelMap;


public class ArcTangentCommand extends Command {

    public ArcTangentCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return Math.atan(Math.toRadians(getCommands().get(0).get(0).execute()));
    }

}
