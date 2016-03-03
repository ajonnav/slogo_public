package command;

import java.util.List;
import model.ModelMap;


public class SetPaletteCommand extends Command {

    private ModelMap modelMap;

    public SetPaletteCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(4);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        double index = getCommands().get(0).get(0).execute();
        double r = getCommands().get(1).get(0).execute();
        double g = getCommands().get(2).get(0).execute();
        double b = getCommands().get(3).get(0).execute();
        modelMap.getTurtle().addToColorMap(index, r, g, b);
        return index;
    }

}
