package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;


public class PenDownCommand implements ICommand {

    public static int numChildren = 0;
    private Map<String, Observable> modelMap;

    public PenDownCommand (Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        ((TurtleModel) modelMap.get("turtle")).penDown();
        return evaluate();
    }

    @Override
    public double evaluate () {
        return 1;
    }

    @Override
    public int getNumChildren () {
        return numChildren;
    } 
    
}
