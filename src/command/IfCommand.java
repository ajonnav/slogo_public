package command;


import java.util.List;
import java.util.Map;
import java.util.Observable;

public class IfCommand implements ICommand{
    
    public static int numChildren = 2;
    
    private Map<String, Observable> modelMap;  
    private double bool;
    private List<ICommand> commands;
    
    public IfCommand( Map<String, Observable> modelMap, List<List<ICommand>> commands) {
            this.modelMap = modelMap;
            this.bool = commands.get(0).get(0).evaluate();
            this.commands = commands.get(1);
    }
    
    @Override
    public double execute () {
        if(bool != 0.0) {
            for(int i = 0; i < commands.size(); i++) {
                commands.get(i).execute();
            }
        }
        return 0;
    }

    @Override
    public double evaluate () {
        if(bool != 0.0) {
            for(int i = 0; i < commands.size(); i++) {
                commands.get(i).evaluate();
            }
        }
        return 0;
    }
}
