package command;

import java.util.List;
import model.IModelMap;

public class TellCommand extends Command {


    public TellCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        setNumChildren(1);
    }
    
    @Override
    public double execute () { 
        double[] turtles = new double[getCommands().get(0).size()];
        for(int i = 0; i < getCommands().get(0).size(); i++) {
            turtles[i] = getCommands().get(0).get(i).execute();
        }
        return getModelMap().getDisplay().tell(turtles);
    }

}
