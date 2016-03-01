package command;

import java.util.List;

import model.ModelMap;

public class ConstantCommand extends Command {

    private double constant;

    public ConstantCommand (ModelMap modelMap, List<String> text) {
        this.constant = Double.parseDouble(text.get(0));
    }
    
    @Override
    public double execute () {
        return constant;
    }

}
