package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import command.Command;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import model.CommandsModel;

public class CommandsView implements IView{

        private Map<String, List<Command>> vars;
        private VBox varBox;
        private TextArea myTA;
        
        public CommandsView(VBox vb, TextArea va) {
                vars = new HashMap<String, List<Command>>();
                varBox = vb;
                myTA = va;
        }

        @Override
        public void update(Observable o, Object arg) {
                if(o instanceof CommandsModel){
                        CommandsModel vm = (CommandsModel) o;
                        vars = vm.getImmutableVariablesMap();
                        varBox.getChildren().clear();
                        for(String item : vars.keySet()){
                                if(vars.get(item) != null) {
                                    String parameterString = vars.get(item).size() == 1 ? " parameter" : " parameters";
                                    String temp = item + " - " + vars.get(item).size() + parameterString;
                                    Hyperlink pastVar = new Hyperlink(temp);
                                    pastVar.setOnAction(event -> {
                                        myTA.appendText(item);
                                    });
                                    varBox.getChildren().add(pastVar);
                                }
                        }
                }
                
        }

}
