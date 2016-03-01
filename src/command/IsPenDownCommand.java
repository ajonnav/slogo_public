package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;


public class IsPenDownCommand extends Command {

    private boolean status;

    public IsPenDownCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(0);
        status = ((TurtleModel) modelMap.get("turtle")).getPenStatus();
    }
    
    @Override
    public double execute () {
        return status ? 1 : 0;
    }

}
