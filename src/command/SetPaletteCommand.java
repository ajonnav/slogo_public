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
        return modelMap.getDisplay().addToColorMap(new double[] { getCommands().get(0).get(0).execute(),
                                                     getCommands().get(1).get(0).execute(),
                                                     getCommands().get(2).get(0).execute(),
                                                     getCommands().get(3).get(0).execute()});
    }

}
