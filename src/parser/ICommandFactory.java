package parser;

import java.util.List;
import command.Command;
import model.IModelMap;

public interface ICommandFactory {

    Command constructCommand(IModelMap modelMap, String expression, List<String> text);
    
}
