package command;

import java.util.List;

import model.IModelMap;

public class ConstantCommand extends Command {

    private double constant;

    public ConstantCommand (IModelMap modelMap, List<String> text) {
        this.constant = Double.parseDouble(text.get(0));
    }
    
    @Override
    public double execute () {
        return constant;
    }

}
