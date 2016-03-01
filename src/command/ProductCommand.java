package command;

import java.util.List;
import model.ModelMap;


public class ProductCommand extends Command {

    public ProductCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(2);
    }

    @Override
    public double execute () {
        return getCommands().get(0).get(0).execute() * getCommands().get(1).get(0).execute();
    }

}
