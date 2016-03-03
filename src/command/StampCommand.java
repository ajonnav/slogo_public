package command;

import java.util.List;
import model.ModelMap;
import model.TurtleModel;


public class StampCommand extends Command {

    private TurtleModel turtleModel;

    public StampCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        this.turtleModel = modelMap.getTurtle();
    }

    @Override
    public double execute () {
        turtleModel.setShouldStamp(true);
        turtleModel.setNumStamps(turtleModel.getNumStamps() + 1);
        return turtleModel.getImageIndex();
    }

}
