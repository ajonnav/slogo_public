package parser;

import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import command.Command;
import constants.ResourceConstants;
import exception.SLogoException;

public class ReflectionSyntaxParserFactory implements ISyntaxParserFactory{
    
    public static final String PACKAGE = "parser.";
    public static final String BASE = "SyntaxParser";
    private ICommandFactory commandFactory;
    private Function<List<String>, List<Command>> parseFullText;
    private Function<List<String>, Command> parseHelper;
    private ResourceBundle errorMessageBundle;
    
    
    public ReflectionSyntaxParserFactory(ICommandFactory commandFactory, 
                                         Function<List<String>, List<Command>> parseFullText,
                                         Function<List<String>, Command> parseHelper) {
        this.commandFactory = commandFactory;
        this.parseFullText = parseFullText;
        this.parseHelper = parseHelper;
        this.errorMessageBundle = ResourceBundle
                .getBundle(ResourceConstants.DEFAULT_RESOURCE + ResourceConstants.ERRORS);
    }
    
    @Override
    public SyntaxParser constructSyntax(String trigger, String key) {
        SyntaxParser syntaxParser = null;
        try {
            syntaxParser = (SyntaxParser) Class.forName(PACKAGE + key + BASE)
                    .getConstructor(String.class, ICommandFactory.class, Function.class, Function.class)
                    .newInstance(trigger, commandFactory, parseFullText, parseHelper);
        }
        catch (Exception e) {
            throw new SLogoException(errorMessageBundle.getString("ReflectionError"));
        }
        return syntaxParser;
    }
}
