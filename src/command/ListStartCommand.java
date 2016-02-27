package command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class ListStartCommand implements ICommand{
    
    public static int numChildren = -1;
    
    private Map<String, Observable> modelMap;  
    
    public ListStartCommand( Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return 0;
    }

    @Override
    public double evaluate () {
        return 0;
    }
    
    
}
