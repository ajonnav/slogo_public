package command;

import java.util.List;
import model.ModelMap;


public class OrCommand extends Command {

    public OrCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(2);
    }

    @Override
    public double execute () {
        if(getCommands().get(0).size() > 1) {
            for(int i = 0; i < getCommands().get(0).size(); i++) {
                if(getCommands().get(0).get(i).execute() == 1) {
                    return 1;
                }
            }
            return 0;
        }
        return getCommands().get(0).get(0).execute() != 0 ||
               getCommands().get(1).get(0).execute() != 0 ? 1 : 0;
    }

}
