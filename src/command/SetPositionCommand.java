package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.TurtleModel;

public class SetPositionCommand implements ICommand {
    
    public static int numChildren = 2;
    
    private double x;
    private double y;
    private double distance;
    private Map<String, Observable> modelMap;
    
    public SetPositionCommand(Map<String, Observable> modelMap, List<List<ICommand>> commands) {
            this.x = commands.get(0).get(0).evaluate();
            this.y = commands.get(1).get(0).evaluate();
            this.modelMap = modelMap;
            TurtleModel turtleModel = (TurtleModel)modelMap.get("turtle");
            this.distance = Math.sqrt(Math.pow((x - turtleModel.getPositionX()), 2) + Math.pow((y - turtleModel.getPositionY()), 2));
    }

    @Override
    public double execute() {
    	((TurtleModel)modelMap.get("turtle")).setPosition(x, y);
        return evaluate();
    }

    @Override
    public double evaluate () {
        return distance;
    }
}
