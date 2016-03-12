package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import command.Command;
import constants.UIConstants;
import exception.SLogoException;
import model.IModelMap;


public class CommandParser {

    private List<Entry<String, Pattern>> mySymbols;
    private IModelMap modelMap;
    public static final String WHITESPACE = "\\s+";
    private final String ERROR = "NO MATCH";
    private List<Command> commandsList;
    private ResourceBundle errorMessageBundle = ResourceBundle.getBundle(UIConstants.DEFAULT_RESOURCE + UIConstants.ERRORS);

    public CommandParser (IModelMap modelMap) {
        this.mySymbols = new ArrayList<>();
        this.modelMap = modelMap;
        this.commandsList = new ArrayList<>();
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
    
    public void parseText (String text) {
        text = text.trim();
        if(text.equals("")) {
            return;
        }
        modelMap.getHistory().addToHistory(text);
        try {
             List<String> commands = preProcess(text);
             commandsList = parseFullText(commands);
             double returnValue = 0;
             for (int i = 0; i < commandsList.size(); i++) {
                 returnValue = commandsList.get(i).execute();
            }
            modelMap.getDisplay().setToAnimate(true);
            modelMap.getDisplay().updateView();
            modelMap.getHistory().addToHistory(Double.toString(returnValue));
        }
        catch (Exception e) {
            modelMap.getHistory().addToHistory(e.getMessage());
        }
    }

    public List<String> preProcess (String text) {
        StringBuilder sbText = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            if (text.charAt(i) == '#') {
                i++;
                while (text.charAt(i) != '\n' && i < text.length() - 1) {
                    i++;
                }
            }
            sbText.append(text.charAt(i));
            i++;
        }
        return new ArrayList<String>(Arrays.asList(sbText.toString().trim().split(WHITESPACE)));
    }

    public List<Command> parseFullText (List<String> text) {
        List<Command> commandsList = new ArrayList<Command>();
        while (!text.isEmpty()) {
            commandsList.add(parseHelper(text));
        }
        return commandsList;
    }

    public Command parseHelper (List<String> text) {
        String currString = text.get(0);
        String currName = getClassName(currString);
        if(currName.equalsIgnoreCase("command.NO MATCHCommand")) {
                throw new SLogoException(errorMessageBundle.getString("CommandNotFound"));
        }
        Command command = null;
        if(currString.equals("(")) {
            command = processUnlimitedParameters(text);
        }
        else {
            command = constructCurrCommand(text, currName);
            text.remove(0);
            command.prepare(getCommandParams(text, command.getNumChildren(), "[", "]"));
        }
        return command;
    }
    
    public Command processUnlimitedParameters(List<String> text) {
        Command command = null;
        text.remove(0);
        command = constructCurrCommand(text, getClassName(text.get(0)));
        text.remove(0);
        text.add(0, "(");
        if(command.takesUnlimitedParameters()) {
            command.prepare(getCommandParams(text, 1, "(", ")" ));
        }     
        else {
            throw new SLogoException(errorMessageBundle.getString("UnlimitedParametersNotPossible"));
        }
        return command;
    }
    
    public Command constructCurrCommand(List<String> text, String currName) {
        Command command = null;
        try {
            command = ((Command) Class.forName(currName).getConstructor(IModelMap.class, int.class, List.class)
                    .newInstance(modelMap, 0, Collections.unmodifiableList(text)));
        }
        catch (SLogoException e) {
            throw e;
        }
        catch (Exception e) {
            throw new SLogoException("ReflectionError");
        }
        return command;
    }

    public List<List<Command>> getCommandParams (List<String> text, int numChildren, String open, String close) {
        List<List<Command>> commandParams = new ArrayList<List<Command>>();
        for (int i = 0; i < numChildren; i++) {
            if(text.isEmpty()) {
                throw new SLogoException(errorMessageBundle.getString("IncompleteCommand"));
            }
            if (text.get(0).equals(open)) {
                text.remove(0);
                commandParams.add(parseFullText(getBracketed(text, open, close)));
                text.remove(0);
            }
            else {
                commandParams.add(Collections.singletonList(parseHelper(text)));
            }
        }
        return commandParams;
    }

    public List<String> getBracketed (List<String> text, String openSymbol, String closeSymbol) {
        List<String> bracketed = new ArrayList<String>();
        int bracketNumber = 1;
        while (bracketNumber != 0) {
            if (text.get(0).equals(openSymbol)) {
                bracketNumber++;
            }
            else if (text.get(0).equals(closeSymbol)) {
                bracketNumber--;
                if (bracketNumber == 0) {
                    break;
                }
            }
            bracketed.add(text.get(0));
            text.remove(0);
        }
        return bracketed;
    }

    public String getClassName (String text) {
        return "command." + getSymbol(text) + "Command";
    }

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
}
