
package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;


public class SetPositionCommand extends Command {

    private Map<String, Observable> modelMap;

    public SetPositionCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(2);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        double xPos = getCommands().get(0).get(0).execute();
        double yPos = getCommands().get(1).get(0).execute();
        TurtleModel turtleModel = (TurtleModel) modelMap.get("turtle");
        turtleModel.setPosition(xPos, yPos);
        return Math.sqrt(Math.pow((xPos - turtleModel.getPositionX()), 2) +
                         Math.pow((yPos - turtleModel.getPositionY()), 2));
    }

}
