
package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.ModelMap;
import model.TurtleModel;


public class SetTowardsCommand extends Command {

    private ModelMap modelMap;

    public SetTowardsCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(2);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        double xPos = getCommands().get(0).get(0).execute();
        double yPos = getCommands().get(1).get(0).execute();
        TurtleModel turtleModel = modelMap.getTurtle();
        double lastHeading = turtleModel.getHeading();
        turtleModel.setHeading(Math.atan(xPos / yPos));
        double headingDiff = turtleModel.getHeading() - lastHeading;
        return headingDiff <= 180 ? headingDiff : lastHeading - turtleModel.getHeading();
    }

}
