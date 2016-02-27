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
    private List<ICommand> tempList;
    private boolean tempFlag = false;
    private List<String> commands = new ArrayList<String>();
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
        commands = new ArrayList<String>(Arrays.asList(text.split(WHITESPACE)));
        while(commands.size()!=0) {
            parseHelper(commands);  
        }
        for(int i = 0; i < commandsList.size(); i++) {
            commandsList.get(i).execute();
        }
        commandsList.clear();
    }
    
    public ICommand parseHelper(List<String> text) {
        ICommand command = null;
        if(text.size() == 0) {
            return null;
        }
        String currString = text.get(0);
        text.remove(0);
        String className = getClassName(currString);
        if(className.equals("command.ConstantCommand")) {
            return new ConstantCommand(Integer.parseInt(currString));
        }
        try {
            command = ((ICommand) Class.forName(className).getConstructor(Map.class, List.class)
                    .newInstance(modelMap, getCommandParams(className, text)));
            if(tempFlag) {
                tempList.add(command);
            }
            else {
                commandsList.add(command);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return command;
    }
    
    public List<List<ICommand>> getCommandParams(String className, List<String> text) {
        int numChildren = getNumChildren(className);
        List<List<ICommand>> commandParams = new ArrayList<List<ICommand>>();
        for(int i = 0; i < numChildren; i++) {
            if(getNumChildren(getClassName(text.get(0))) == -1) {
                text.remove(0);
                List<String> bracketed = new ArrayList<String>();
                while(getNumChildren(getClassName(text.get(0))) != -2) {
                    bracketed.add(text.get(0));
                    text.remove(0);
                }
                text.remove(0);
                tempFlag = true;
                tempList = new ArrayList<ICommand>();
                while(!bracketed.isEmpty()) {
                	parseHelper(bracketed);
                }
                commandParams.add(new ArrayList<ICommand>(tempList));
                tempFlag = false;
                tempList.clear();
            }
            else {
                List<ICommand> singleList = new ArrayList<ICommand>();
                singleList.add(parseHelper(text));
                commandParams.add(singleList); 
            }
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
