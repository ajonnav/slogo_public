package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.ModelMap;


public class PiCommand extends Command {

    public PiCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
    }

    @Override
    public double execute () {
        return Math.PI;
    }
}
