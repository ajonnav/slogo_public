// This entire file is part of my masterpiece.
// Andy Wang (sw258)
// This is the main parser class that takes a String expression, parses it into a
// list of commands, and executes them. It supports a substantial amount of configuration,
// including changing the implementation of the ISymbolProcessor and ICommandFactory
// from using resource bundles and reflection to other ways such as factory classes.
// It also supports using a different IPreProcessor, other than the current CommentPreProcessor,
// which removes all commented lines from the code. The alternative implementations simply
// have to implement their respective interfaces and be constructed in this class instead of the current
// implementations. Additionally, it supports adding new syntax types using a resource bundle 
// that specifies a trigger and the specific class that describes the syntax. 
// See UnlimitedSyntaxParser for an example of additional syntax parsing.

package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import command.Command;
import constants.ResourceConstants;
import exception.SLogoException;
import model.IModelMap;


public class SLogoCommandParser implements ICommandParser {

    public static final String DELIMITER = "\\s+";
    private IModelMap modelMap;
    private ISymbolProcessor symbolProcessor;
    private IPreProcessor preProcessor;
    private String expression;
    private List<SyntaxParser> syntaxParsers;
    private ResourceBundle errorMessageBundle;
    

    public SLogoCommandParser (IModelMap modelMap) {
        this.modelMap = modelMap;
        this.symbolProcessor = new ResourceBundleSymbolProcessor();
        this.preProcessor = new CommentPreProcessor();
        this.syntaxParsers = new ArrayList<>();
        this.errorMessageBundle = ResourceBundle
                .getBundle(ResourceConstants.DEFAULT_RESOURCE + ResourceConstants.ERRORS);
        initSyntaxParsers(new ReflectionSyntaxParserFactory(new ReflectionCommandFactory(symbolProcessor),
                                                            split -> parseFullText(split),
                                                            text -> parseHelper(text)));
    }

    private void initSyntaxParsers (ISyntaxParserFactory syntaxParserFactory) {
        ResourceBundle syntax = ResourceBundle
                .getBundle(ResourceConstants.DEFAULT_RESOURCE + ResourceConstants.SYNTAX);
        Collections.list(syntax.getKeys())
                   .forEach(k -> syntaxParsers.add(syntaxParserFactory.constructSyntax(syntax.getString(k), k)));
    }

    public void setLanguage (String language) {
        symbolProcessor.setSymbols(language);
    }

    public void parseExpression (String text) {
        expression = text;
        String trimmed = text.trim();
        if (trimmed.equals("")) {
            return;
        }
        try {
            modelMap.getHistory().addToHistory(trimmed);
            modelMap.getHistory().addToHistory(Double.toString(executeCommands(trimmed)));
        }
        catch (Exception e) {
            modelMap.getHistory().addToHistory(e.getMessage());
        }
    }

    private double executeCommands (String text) {
        List<Command> commandsList = parseFullText(new ArrayList<String>(Arrays
                .asList(preProcessor.preProcess(expression).split(DELIMITER))));
        double returnValue = 0;
        for(Command c : commandsList) {
            returnValue = c.execute();
        }
        modelMap.getDisplay().setToAnimate(true);
        modelMap.getDisplay().setToUpdateIDView(true);
        modelMap.getDisplay().updateView();
        return returnValue;
    }

    private List<Command> parseFullText (List<String> split) {
        List<Command> commandsList = new ArrayList<Command>();
        while (!split.isEmpty()) {
            commandsList.add(parseHelper(split));
        }
        return commandsList;
    }

    private Command parseHelper (List<String> text) {
        if (symbolProcessor.isSymbolInvalid(text.get(0))) {
            throw new SLogoException(errorMessageBundle.getString("CommandNotFound"));
        }
        Command command = null;
        for (SyntaxParser sp : syntaxParsers) {
            if (command != null) {
                break;
            }
            command = sp.makeCommand(text, modelMap, expression);
        }
        return command;
    }
}