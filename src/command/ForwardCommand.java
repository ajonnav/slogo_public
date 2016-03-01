package command;

import java.util.List;
import model.ModelMap;


public class ForwardCommand extends Command {

    private ModelMap modelMap;

    public ForwardCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(1);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        double dist = getCommands().get(0).get(0).execute();
        modelMap.getTurtle().forward(dist);
        return dist;
    }
}
