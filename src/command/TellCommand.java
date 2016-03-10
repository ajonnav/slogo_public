package command;

import java.util.List;
import model.ModelMap;

public class TellCommand extends Command {

    private ModelMap modelMap;

    public TellCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(1);
        this.modelMap = modelMap;
    }
    
    @Override
    public double execute () { 
        double[] turtles = new double[getCommands().get(0).size()];
        for(int i = 0; i < getCommands().get(0).size(); i++) {
            turtles[i] = getCommands().get(0).get(i).execute();
        }
        return modelMap.getDisplay().tell(turtles);
    }

}
