
package command;

import java.util.List;
import model.ModelMap;
import model.TurtleModel;


public class SetPositionCommand extends Command {

    private ModelMap modelMap;

    public SetPositionCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(2);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        double xPos = getCommands().get(0).get(0).execute();
        double yPos = getCommands().get(1).get(0).execute();
        TurtleModel turtleModel = modelMap.getTurtle();
        double turtleXPos = turtleModel.getPositionX();
        double turtleYPos = turtleModel.getPositionY();
        turtleModel.setPosition(xPos, yPos);
        return Math.sqrt(Math.pow((xPos - turtleXPos), 2) +
                         Math.pow((yPos - turtleYPos), 2));
    }

}
