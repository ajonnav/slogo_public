package command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class ListEndCommand implements ICommand{
    
    public static int numChildren = -2;
    
    private Map<String, Observable> modelMap;  
    
    public ListEndCommand( Map<String, Observable> modelMap, List<ICommand> commands) {
        
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
