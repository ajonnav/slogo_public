package command;

import java.util.List;
import model.ModelMap;

public class AskCommand extends Command {

    private ModelMap modelMap;

    public AskCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(2);
        this.modelMap = modelMap;
    }
    
    @Override
    public double execute () { 
        double[] turtles = new double[getCommands().get(0).size()];
        for(int i = 0; i < getCommands().get(0).size(); i++) {
            turtles[i] = getCommands().get(0).get(i).execute();
        }
        double[] oldValues = modelMap.getActiveTurtleIDs();
        modelMap.tell(turtles);
        double returnValue = loopExecute(getCommands().get(1));
        modelMap.tell(oldValues);
        return returnValue;
    }
}
