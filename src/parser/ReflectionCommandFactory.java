// This entire file is part of my masterpiece.
// Andy Wang (sw258)
// This constructs commands using reflection, which avoids making CommandFactory
// classes for every command. It uses the ISymbolProcessor to get the symbol to construct
// the class name of the command. Since all commands take the same parameters, the same construction
// can be used for all commands, drastically reducing code redundancy. 

package parser;

import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import command.Command;
import constants.ResourceConstants;
import exception.SLogoException;
import model.IModelMap;


public class ReflectionCommandFactory implements ICommandFactory {
    
    public static final String PACKAGE = "command.";
    public static final String BASE = "Command";
    private ISymbolProcessor symbolProcessor;
    private ResourceBundle errorMessageBundle;

    public ReflectionCommandFactory (ISymbolProcessor symbolProcessor) {
        this.symbolProcessor = symbolProcessor;
        this.errorMessageBundle = ResourceBundle
                .getBundle(ResourceConstants.DEFAULT_RESOURCE + ResourceConstants.SYNTAX);
    }

    @Override
    public Command constructCommand (IModelMap modelMap, String expression, List<String> text) {
        Command command = null;
        try {
            command = ((Command) Class.forName(PACKAGE + symbolProcessor.getSymbol(text.get(0)) + BASE)
                    .getConstructor(IModelMap.class, String.class, List.class)
                    .newInstance(modelMap, expression, Collections.unmodifiableList(text)));
        }
        catch (Exception e) {
            throw new SLogoException(errorMessageBundle.getString("ReflectionError"));
        }
        return command;
    }
}
