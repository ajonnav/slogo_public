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
import command.VariableCommand;

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
        List<ICommand> commandsList = new ArrayList<ICommand>();
        while(!commands.isEmpty()) {
            commandsList.add(parseHelper(commands));
        } 
        for(int i = 0; i < commandsList.size(); i++) {
            commandsList.get(i).execute();
        }
    }
    
    public ICommand parseHelper(List<String> text) {
        String currString = text.get(0);
        String currName = getClassName(currString);
        text.remove(0);
        if(currName.equals("command.ConstantCommand")) {
            return new ConstantCommand(Integer.parseInt(currString));
        }
        else if(currName.equals("command.VariableCommand"))  {
            return new VariableCommand(modelMap, currString);
        }
        try {
            return ((ICommand) Class.forName(currName).getConstructor(Map.class, List.class)
                    .newInstance(modelMap, getCommandParams(currName, text)));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<List<ICommand>> getCommandParams(String currName, List<String> text) {
        List<List<ICommand>> commandParams = new ArrayList<List<ICommand>>();
        for(int i = 0; i < getNumChildren(currName); i++) {
            String nextName = getClassName(text.get(0));
            if(nextName.equals("command.ListStartCommand")) {
                text.remove(0);
                commandParams.add(getBracketParams(text));
                text.remove(0);
            }
            else {
                commandParams.add(getNonBracketParams(text, nextName));
            }
        }
        return commandParams;
    }
    
    public List<ICommand> getNonBracketParams(List<String> text, String nextName) {
        List<ICommand> list = new ArrayList<ICommand>();
        if(nextName.equals("command.ConstantCommand")) {
            list.add(new ConstantCommand(Integer.parseInt(text.get(0))));
            text.remove(0);
        }
        else if(nextName.equals("command.VariableCommand"))  {
            list.add(new VariableCommand(modelMap, text.get(0)));
            text.remove(0);
        }
        else {
            list.add(parseHelper(text));
        }
        return list;
    }
    
    public List<ICommand> getBracketParams(List<String> text) {
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
        List<ICommand> bracketList = new ArrayList<ICommand>();
        while(!bracketed.isEmpty()) {
            bracketList.add(parseHelper(bracketed));
        }
        return bracketList;
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
