package command;

import java.util.List;
import model.ModelMap;


public class ClearScreenCommand extends Command {

    private ModelMap modelMap;
    private HomeCommand home;

    public ClearScreenCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        this.modelMap = modelMap;
        home = new HomeCommand(modelMap, text);
    }

    @Override
    public double execute () {
        modelMap.getTurtle().setShouldClear(true);
        return home.execute();
    }

}
