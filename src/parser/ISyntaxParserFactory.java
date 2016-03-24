package parser;

public interface ISyntaxParserFactory {

    SyntaxParser constructSyntax (String trigger, String key);

}
