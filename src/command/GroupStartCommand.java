package command;

import java.util.List;

import model.IModelMap;


public class GroupStartCommand extends Command {

    public GroupStartCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
    	super(modelMap, tokenNumber, text);
    }

    @Override
    public double execute () {
        return 0;
    }

}
