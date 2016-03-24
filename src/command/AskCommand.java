package command;

import java.util.List;
import model.IModelMap;

public class AskCommand extends Command {

    public AskCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        setNumChildren(2);
    }
    
    @Override
    public double execute () { 
        double[] turtles = new double[getCommands().get(0).size()];
        for(int i = 0; i < getCommands().get(0).size(); i++) {
            turtles[i] = getCommands().get(0).get(i).execute();
        }
        double[] oldValues = getModelMap().getDisplay().getActiveTurtleIDs();
        getModelMap().getDisplay().tell(turtles);
        double returnValue = loopExecute(getCommands().get(1));
        getModelMap().getDisplay().tell(oldValues);
        return returnValue;
    }
}
