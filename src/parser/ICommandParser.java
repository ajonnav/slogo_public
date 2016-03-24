package parser;

public interface ICommandParser {
    
    void setLanguage(String language);
    
    void parseExpression(String text);
}
