package command;

import java.util.Arrays;
import java.util.List;

import model.IModelMap;

public class SetPenStyleCommand extends Command{

	private IModelMap modelMap;
	
	public SetPenStyleCommand(IModelMap modelMap, List<String> text) {
		setNumChildren(1);
        this.modelMap = modelMap;
	}

	@Override
	public double execute() {
		return modelMap.getDisplay().TurtleAction("setPenStyleIndex", Arrays.asList(getCommands().get(0).get(0)));
	}

}
