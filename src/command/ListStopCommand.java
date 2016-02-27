package command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class ListStopCommand implements ICommand{
    
    public static int numChildren = -1;
    
    private Map<String, Observable> modelMap;  
    
    public ListStopCommand( Map<String, Observable> modelMap, List<ICommand> commands) {
        
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
