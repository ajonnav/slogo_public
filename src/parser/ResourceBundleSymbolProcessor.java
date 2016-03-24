package parser;

import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.regex.Pattern;
import constants.ResourceConstants;
import java.util.Map.Entry;


public class ResourceBundleSymbolProcessor implements ISymbolProcessor {

    public final String ERROR = "NO MATCH";
    private List<Entry<String, Pattern>> mySymbols;

    public ResourceBundleSymbolProcessor () {
        this.mySymbols = new ArrayList<>();
    }

    @Override
    public String getSymbol (String text) {
        for (Entry<String, Pattern> e : mySymbols) {
            if (match(text, e.getValue())) {
                return e.getKey();
            }
        }
        return ERROR;
    }

    private boolean match (String text, Pattern regex) {
        return regex.matcher(text).matches();
    }

    @Override
    public void setSymbols (String language) {
        mySymbols.clear();
        addPatterns(ResourceConstants.RSRC_LANG + language);
        addPatterns(ResourceConstants.RSRC_LANG + ResourceConstants.SYNTAX);
    }

    public void addPatterns (String syntax) {
        ResourceBundle resources = ResourceBundle.getBundle(syntax);
        Enumeration<String> iter = resources.getKeys();
        while (iter.hasMoreElements()) {
            String key = iter.nextElement();
            String regex = resources.getString(key);
            mySymbols.add(new SimpleEntry<>(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
        }
    }

    @Override
    public boolean isSymbolInvalid (String text) {
        return getSymbol(text) == ERROR ? true : false;
    }
}
