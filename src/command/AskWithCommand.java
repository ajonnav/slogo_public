package command;

import java.util.ArrayList;
import java.util.List;
import model.IModelMap;
import model.TurtleModel;

public class AskWithCommand extends Command {


    public AskWithCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(2);
    }
    
    @Override
    public double execute () { 
        List<Double> ids = new ArrayList<Double>();
        double[] oldValues = getModelMap().getDisplay().getActiveTurtleIDs();
        List<TurtleModel> turtleList = getModelMap().getDisplay().getTurtleList();
        for(int i = 0; i < turtleList.size(); i++) {
            getModelMap().getDisplay().tell(new double[]{i+1});
            if(getCommands().get(0).get(0).execute() == 1) {
                ids.add((double) i + 1);
            }
        }
        getModelMap().getDisplay().tell(ids.stream().mapToDouble(d -> d).toArray());
        double returnValue = loopExecute(getCommands().get(1));
        getModelMap().getDisplay().tell(oldValues);
        return returnValue;
    }
}
