package command;

import java.util.ArrayList;
import java.util.List;
import model.ModelMap;
import model.TurtleModel;

public class AskWithCommand extends Command {

    private ModelMap modelMap;

    public AskWithCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(2);
        this.modelMap = modelMap;
    }
    
    @Override
    public double execute () { 
        List<Double> ids = new ArrayList<Double>();
        double[] oldValues = modelMap.getDisplay().getActiveTurtleIDs();
        List<TurtleModel> turtleList = modelMap.getDisplay().getFrame( modelMap.getDisplay().getNumFrames());
        for(int i = 0; i < turtleList.size(); i++) {
            modelMap.getDisplay().tell(new double[]{i+1});;
            if(getCommands().get(0).get(0).execute() == 1) {
                ids.add((double) i + 1);
            }
        }
        modelMap.getDisplay().tell(ids.stream().mapToDouble(d -> d).toArray());
        double returnValue = loopExecute(getCommands().get(1));
        modelMap.getDisplay().tell(oldValues);
        return returnValue;
    }
}
