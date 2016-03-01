
package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;

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
        turtleModel.setPosition(xPos, yPos);
        return Math.sqrt(Math.pow((xPos - turtleModel.getPositionX()), 2) +
                         Math.pow((yPos - turtleModel.getPositionY()), 2));
    }

}
