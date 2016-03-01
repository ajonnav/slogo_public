package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Map.Entry;
import java.lang.reflect.Field;
import java.util.AbstractMap.SimpleEntry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import command.Command;

public class CommandParser {
    
    private List<Entry<String, Pattern>> mySymbols;
    private Map<String, Observable> modelMap = new HashMap<String, Observable>(); 
    public static final String WHITESPACE = "\\s+";
    
    public CommandParser(Map<String, Observable> modelMap) {
        this.mySymbols = new ArrayList<>();
        this.modelMap = modelMap;
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
    
    public void parseText(String text) {
        List<String> commands = new ArrayList<String>(Arrays.asList(text.split(WHITESPACE)));
        while(!commands.isEmpty()) {
            parseHelper(commands).execute();
        }
    }
    
    public List<Command> parseFullText(List<String> text) {
        List<Command> commandsList = new ArrayList<Command>();
        while(!text.isEmpty()) {
            commandsList.add(parseHelper(text));
        }
        return commandsList;
    }
    
    public Command parseHelper(List<String> text) {
        String currString = text.get(0);
        String currName = getClassName(currString);
        Command command = null;
        try {
            command = ((Command) Class.forName(currName).getConstructor(Map.class, List.class)
                    .newInstance(modelMap, Collections.unmodifiableList(text)));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        text.remove(0);
        command.setCommands(getCommandParams(currName, text, command.getNumChildren()));
        return command;
    }
    
    public List<List<Command>> getCommandParams(String currName, List<String> text, int numChildren) {
        List<List<Command>> commandParams = new ArrayList<List<Command>>();
        for(int i = 0; i < numChildren; i++) {
            String nextName = getClassName(text.get(0));
            if(nextName.equals("command.ListStartCommand")) {
                text.remove(0);
                commandParams.add(parseFullText(getBracketed(text)));
                text.remove(0);
            }
            else {
                commandParams.add(Collections.singletonList(parseHelper(text)));
            }
        }
        return commandParams;
    }
    
    public List<String> getBracketed(List<String> text) {
        List<String> bracketed = new ArrayList<String>();
        int bracketNumber = 1;
        while(bracketNumber != 0) {
            if(text.get(0).equals("[")) {
                bracketNumber++;
            }
            else if(text.get(0).equals("]")) {
                bracketNumber--;
                if(bracketNumber == 0) {
                    break;
                }
            }
            bracketed.add(text.get(0));
            text.remove(0);
        }
        return bracketed;
    }
    
    public int getNumChildren(String className) {
        try {
            for(Field field : Class.forName(className).getFields()) {
                if(field.getName().equals("numChildren")) {
                    return (int) field.get(null);
                }
            }   
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public String getClassName(String text) {
        return "command." + getSymbol(text) + "Command";
    }
    
    public String getSymbol (String text) {
        final String ERROR = "NO MATCH";
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
}
