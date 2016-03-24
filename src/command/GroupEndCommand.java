package command;

import java.util.List;

import model.IModelMap;


public class GroupEndCommand extends Command {

    public GroupEndCommand (IModelMap modelMap, String expression, List<String> text) {
    	super(modelMap, expression, text);
    }

    @Override
    public double execute () {
        return 0;
    }

}
