package command;
 
 import java.util.List;
 import model.IModelMap;
 
 
 public class WindowCommand extends Command {
 
 
     public WindowCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
         super(modelMap, tokenNumber, text);
     }
 
     @Override
     public double execute () {
         return getModelMap().getDisplay().TurtleAction("window", null);
     }
 
 }