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
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import command.ConstantCommand;
import command.ICommand;
import command.VariableCommand;
import model.HistoryPaneModel;

public class CommandParser {
    
    private List<Entry<String, Pattern>> mySymbols;
    private Map<String, Observable> modelMap = new HashMap<String, Observable>(); 
    private List<ICommand> commandsList = new ArrayList<ICommand>();
    private List<ICommand> tempList;
    private boolean tempFlag = false;
    private List<String> commands = new ArrayList<String>();
    public static final String WHITESPACE = "\\s+";
    
    public CommandParser(Map<String, Observable> modelMap) {
        mySymbols = new ArrayList<>();
        this.modelMap = modelMap;
    }
    
    public void addPatterns (String syntax) {
    	System.out.println(syntax);
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
        ((HistoryPaneModel) modelMap.get("history")).addToHistory(text);
        while(!commands.isEmpty()) {
            parseHelper(commands);  
        }
        for(int i = 0; i < commandsList.size(); i++) {
            System.out.println(commandsList.get(i).getClass().getName());
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
            command = new ConstantCommand(Integer.parseInt(currString));
            if(tempFlag) {
                tempList.add(command);
            }
        }
        else if(className.equals("command.VariableCommand"))  {
            command = new VariableCommand(modelMap, currString);
            if(tempFlag) {
                tempList.add(command);
            }
        }
        else {
            try {
            	List<List<ICommand>> x = getCommandParams(className, text);
                command = ((ICommand) Class.forName(className).getConstructor(Map.class, List.class)
                        .newInstance(modelMap, x));
                if(tempFlag) {
                    tempList.add(command);
                }
                else {
                    commandsList.add(command);
                }
            }
            catch (InvocationTargetException ee) {
            	Throwable x = ee.getTargetException();
            	int y = 0;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return command;
    }
    
    public List<List<ICommand>> getCommandParams(String className, List<String> text) {
        int numChildren = getNumChildren(className);
        System.out.println(text);
        List<List<ICommand>> commandParams = new ArrayList<List<ICommand>>();
        for(int i = 0; i < numChildren; i++) {
            int bracketNumber = 0;
            if(text.get(0).equals("[")) {
                bracketNumber++;
                List<String> bracketed = new ArrayList<String>();
                while(bracketNumber != 0) {
                    text.remove(0);
                    if(text.get(0).equals("[")) {
                        bracketNumber++;
                    }
                    if(text.get(0).equals("]")) {
                        bracketNumber--;
                    }
                    bracketed.add(text.get(0));
                }
                tempFlag = true;
                tempList = new ArrayList<ICommand>();
                while(!bracketed.isEmpty()) {
                	parseHelper(bracketed);
                }
                commandParams.add(new ArrayList<ICommand>(tempList));
                System.out.println(tempList);
                tempFlag = false;
                tempList.clear();
            }
            else {
                List<ICommand> singleList = new ArrayList<ICommand>();
                singleList.add(parseHelper(text));
                commandParams.add(singleList); 
                int y=0;
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
