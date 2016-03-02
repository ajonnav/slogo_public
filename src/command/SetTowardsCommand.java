
package command;

import java.util.List;
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
        double turtleXPos = turtleModel.getPositionX();
        double turtleYPos = turtleModel.getPositionY();
        double rawDegrees = Math.toDegrees(Math.atan((yPos - turtleYPos)  / (xPos - turtleXPos)));
        double newHeading = xPos - turtleXPos >= 0 ? rawDegrees : rawDegrees-180;
        turtleModel.setHeading(newHeading);
        double headingDiff = Math.abs(lastHeading - turtleModel.getHeading());
        return headingDiff >= 180 ? 360 - headingDiff: headingDiff;
    }

}
