package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import command.Command;
import constants.UIConstants;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import model.ViewableCommandsModel;

public class CommandsView extends ScrollView{

        private Map<String, List<Command>> vars;
        private TextArea myTA;
        
        public CommandsView(TextArea va) {
                vars = new HashMap<String, List<Command>>();
                myTA = va;
                getMyPane().setLayoutX(UIConstants.BORDER_WIDTH);
                getMyPane().setLayoutY(UIConstants.METHODS_Y);
        		getMyPane().setMinSize(UIConstants.UPPER_PANE_WIDTH, UIConstants.UPPER_PANE_HEIGHT);
        		getMyPane().setMaxSize(UIConstants.UPPER_PANE_WIDTH, UIConstants.UPPER_PANE_HEIGHT);
        		setMyName(myBundle.getString("UCommands"));
        		refresh();
        }

        @Override
        public void update(Observable o, Object arg) {
                if(o instanceof ViewableCommandsModel){
                        ViewableCommandsModel vm = (ViewableCommandsModel) o;
                        vars = vm.getImmutableVariablesMap();
                        refresh();
                        for(String item : vars.keySet()){
                                if(vars.get(item) != null) {
                                    String parameterString = vars.get(item).size() == 1 ? " parameter" : " parameters";
                                    String temp = item + " - " + vars.get(item).size() + parameterString;
                                    Hyperlink pastVar = new Hyperlink(temp);
                                    pastVar.setOnAction(event -> {
                                        myTA.appendText(item);
                                    });
                                    getMyBox().getChildren().add(pastVar);
                                }
                        }
                }
                
        }

}
