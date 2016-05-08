package command;
 
 import java.util.List;
 import model.IModelMap;
 
 
 public class FenceCommand extends Command {
 
 
     public FenceCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
         super(modelMap, tokenNumber, text);
     }
 
     @Override
     public double execute () {
         return getModelMap().getDisplay().TurtleAction("fence", null);
     }
 
 }