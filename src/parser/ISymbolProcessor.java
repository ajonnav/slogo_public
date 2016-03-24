package parser;

public interface ISymbolProcessor {
    
    String getSymbol(String text);
    
    void setSymbols(String language);
    
    boolean isSymbolInvalid(String text);
    
}
