package command;

import java.util.List;
import model.ModelMap;
import model.TurtleModel;


public class ClearStampsCommand extends Command {

    private TurtleModel turtleModel;

    public ClearStampsCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        this.turtleModel = modelMap.getTurtle();
    }

    @Override
    public double execute () {
        int numStamps = turtleModel.getNumStamps();
        if(numStamps > 0) {
            turtleModel.setNumStamps(0);
            turtleModel.setShouldClearStamp(true);
            return 1;
        }
        return 0;
    }

}
