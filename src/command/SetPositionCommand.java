
package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;


public class SetPositionCommand implements ICommand {

    public static int numChildren = 2;

    private ICommand x;
    private ICommand y;
    private Map<String, Observable> modelMap;

    public SetPositionCommand (Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        this.modelMap = modelMap;
        this.x = commands.get(0).get(0);
        this.y = commands.get(1).get(0);
    }

    @Override
    public double execute () {
        double xPos = x.execute();
        double yPos = y.execute();
        TurtleModel turtleModel = (TurtleModel) modelMap.get("turtle");
        turtleModel.setPosition(xPos, yPos);
        return Math.sqrt(Math.pow((xPos - turtleModel.getPositionX()), 2) +
                         Math.pow((yPos - turtleModel.getPositionY()), 2));
    }

}
