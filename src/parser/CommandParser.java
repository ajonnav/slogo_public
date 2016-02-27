package parser;

import java.util.ArrayList;
import java.util.Arrays;
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
import command.ConstantCommand;
import command.ICommand;

public class CommandParser {
    
    private List<Entry<String, Pattern>> mySymbols;
    private Map<String, Observable> modelMap = new HashMap<String, Observable>(); 
    private List<ICommand> commandsList = new ArrayList<ICommand>();
    public static final String WHITESPACE = "\\p{Space}";
    
    public CommandParser(Map<String, Observable> modelMap) {
        mySymbols = new ArrayList<>();
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
        ArrayList<String> fullText = new ArrayList<String>(Arrays.asList(text.split(WHITESPACE)));
        parseHelper(fullText);
        for(int i = 0; i < commandsList.size(); i++) {
            commandsList.get(i).execute();
        }
        commandsList.clear();
    }
    
    public ICommand parseHelper(ArrayList<String> text) {
        ICommand command = null;
        String currString = text.get(0);
        text.remove(0);
        String className = "command." + getSymbol(currString) + "Command";
        if(className.equals("command.ConstantCommand")) {
            return new ConstantCommand(Integer.parseInt(currString));
        }
        try {
            command = ((ICommand) Class.forName(className).getConstructor(Map.class, List.class)
                    .newInstance(modelMap, getCommandParams(className, text)));
                        commandsList.add(command);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return command;
    }
    
    public List<ICommand> getCommandParams(String className, ArrayList<String> text) {
        int numChildren = getNumChildren(className);
        List<ICommand> commandParams = new ArrayList<ICommand>();
        for(int i = 0; i < numChildren; i++) {
            commandParams.add(parseHelper(text)); 
        }
        return commandParams;
    }
    
    public int getNumChildren(String className) {
        try {
            Class<?> c = Class.forName(className);
            Field[] fields = c.getFields();
            for(Field field : fields) {
                if(field.getName().equals("numChildren")) {
                    int numChildren = (int) field.get(null);
                    return numChildren;
                }
            }   
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
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
