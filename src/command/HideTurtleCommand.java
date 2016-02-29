package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;


public class HideTurtleCommand implements ICommand {

    public static final int numChildren = 0;
    private Map<String, Observable> modelMap;

    public HideTurtleCommand (Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        ((TurtleModel) modelMap.get("turtle")).hide();
        return evaluate();
    }

    @Override
    public double evaluate () {
        return 0;
    }

}
