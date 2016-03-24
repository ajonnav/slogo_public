// This entire file is part of my masterpiece.
// Andy Wang (sw258)
// This SyntaxParser allows for the unlimited parameters by manipulating the expression text
// to first construct the command and obtain the unlimited parameters. It uses getCommandParams
// with open and close parenthesis to construct and prepare the command correctly. It deviates
// noticeably from the default BracketChildSyntaxParser but can still be implemented by string manipulation. 

package parser;

import java.util.List;
import java.util.function.Function;
import command.Command;
import exception.SLogoException;
import model.IModelMap;


public class UnlimitedSyntaxParser extends SyntaxParser {
    
    public UnlimitedSyntaxParser(String trigger, ICommandFactory commandFactory, 
                                 Function<List<String>, List<Command>> parseFullText,
                                 Function<List<String>, Command> parseHelper) {
        super(trigger, commandFactory, parseFullText, parseHelper);
    }
    
    @Override
    public Command parseSyntax (List<String> text, IModelMap modelMap, String expression) {
        text.remove(0);
        Command command = getCommandFactory().constructCommand(modelMap, expression, text);
        text.remove(0);
        text.add(0, "(");
        prepareCommand(text, command);
        return command;
    }
    
    public void prepareCommand(List<String> text, Command command) {
        if (command.takesUnlimitedParameters()) {
            command.prepare(getCommandParams(text, 1, "(", ")"));
        }
        else {
            throw new SLogoException(getErrorMessageBundle().getString("UnlimitedParametersNotPossible"));
        }
    }
}
