package parser;

import java.util.List;
import java.util.function.Function;
import command.Command;
import model.IModelMap;


public class BracketChildSyntaxParser extends SyntaxParser {

    public BracketChildSyntaxParser (String trigger, ICommandFactory commandFactory,
                                     Function<List<String>, List<Command>> parseFullText,
                                     Function<List<String>, Command> parseHelper) {
        super(trigger, commandFactory, parseFullText, parseHelper);
    }

    @Override
    public Command parseSyntax (List<String> text, IModelMap modelMap, String expression) {
        Command command = getCommandFactory().constructCommand(modelMap, expression, text);
        text.remove(0);
        command.prepare(getCommandParams(text, command.getNumChildren(), "[", "]"));
        return command;
    }
}
