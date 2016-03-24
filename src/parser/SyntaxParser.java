// This entire file is part of my masterpiece.
// Andy Wang (sw258)
// This superclass can be extended to create different types of SyntaxParsers, such
// as BracketChildSyntaxParser and UnlimitedSyntaxParser. Using the default parsing
// functions (parseFullText and parseHelper), it creates methods that are essential
// to implement different ways of parsing syntax, specifically getCommandsParams in this case.
// This method in addition to the CommandFactory are used in the SyntaxParsers to manipulate
// the expression text to construct commands. 

package parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import command.Command;
import constants.ResourceConstants;
import exception.SLogoException;
import model.IModelMap;


public abstract class SyntaxParser {

    private String trigger;
    private ICommandFactory commandFactory;
    private Function<List<String>, List<Command>> parseFullText;
    private Function<List<String>, Command> parseHelper;
    private ResourceBundle errorMessageBundle;

    public SyntaxParser (String trigger, ICommandFactory commandFactory,
                         Function<List<String>, List<Command>> parseFullText,
                         Function<List<String>, Command> parseHelper) {
        this.trigger = trigger;
        this.commandFactory = commandFactory;
        this.parseFullText = parseFullText;
        this.parseHelper = parseHelper;
        this.errorMessageBundle = ResourceBundle
                .getBundle(ResourceConstants.DEFAULT_RESOURCE + ResourceConstants.SYNTAX);
    }

    public Command makeCommand (List<String> text, IModelMap modelMap, String expression) {
        return trigger.equals("") || text.get(0).equals(trigger) ? 
                         parseSyntax(text, modelMap, expression) : null;
    }

    abstract Command parseSyntax (List<String> text, IModelMap modelMap, String expression);

    private List<String> getBracketed (List<String> text, String open, String close) {
        List<String> bracketed = new ArrayList<String>();
        int bracketNumber = 1;
        while (bracketNumber != 0) {
            if (text.get(0).equals(open)) {
                bracketNumber++;
            }
            else if (text.get(0).equals(close)) {
                bracketNumber--;
                if (bracketNumber == 0) {
                    break;
                }
            }
            bracketed.add(text.get(0));
            text.remove(0);
        }
        return bracketed;
    }

    public List<List<Command>> getCommandParams (List<String> text, int numChildren,
                                                 String open, String close) {
        List<List<Command>> commandParams = new ArrayList<List<Command>>();
        for (int i = 0; i < numChildren; i++) {
            if (text.isEmpty()) {
                throw new SLogoException(errorMessageBundle.getString("IncompleteCommand"));
            }
            if (text.get(0).equals(open)) {
                text.remove(0);
                commandParams.add(parseFullText.apply((getBracketed(text, open, close))));
                text.remove(0);
            }
            else {
                commandParams.add(Collections.singletonList(parseHelper.apply(text)));
            }
        }
        return commandParams;
    }

    public ICommandFactory getCommandFactory () {
        return commandFactory;
    }

    public ResourceBundle getErrorMessageBundle () {
        return errorMessageBundle;
    }
}