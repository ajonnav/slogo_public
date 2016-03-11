package display;

import model.CommandsModel;
import model.DisplayModel;
import model.HistoryModel;
import model.ModelMap;
import model.VariableModel;
import parser.CommandParser;
import preferences.SaveState;
import view.CommandsView;
import view.DisplayView;
import view.HistoryPaneView;
import view.InputView;
import view.TurtleIDView;
import view.VariableView;
import view.View;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;
import addons.WMenu;
import constants.UIConstants;

public class DemoWSpace extends Screen {

        private CommandParser parser;
        private TextArea inputText;
        private String myLang;
        private HistoryPaneView hpv;
        private ModelMap modelMap;
        private SaveState myState;

        
        private VariableView varView;
        private TurtleIDView turtleView;
        private InputView myIV;
        private CommandsView commandView;

        public DemoWSpace(SaveState myS) {
                myState = myS;
                modelMap = new ModelMap();
        }

        @Override
        public void setUpScene() {
                setScene(new Scene(getRoot(), UIConstants.WIDTH, UIConstants.HEIGHT, Color.LIGHTBLUE));
                inputText = new TextArea();
          }


        public void setBar() {
                WMenu workspaceMenu = new WMenu(getRoot(), modelMap, getMyLang());
                workspaceMenu.setStage(getStage());
                workspaceMenu.setViews(Arrays.asList(varView, hpv, turtleView, commandView, myIV));
                try {
					workspaceMenu.initializeMenus();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
                getRoot().getChildren().add(workspaceMenu.getMyMenu());
        }

        public void setLang(String language) {
                this.myLang = language;
                parser = new CommandParser(modelMap);
                parser.addPatterns(UIConstants.RSRC_LANG + myLang);
                parser.addPatterns(UIConstants.RSRC_LANG + UIConstants.SYNTAX);
                
                setVariablePane();
                setInputPane();
                setDisplay();
                setHistoryPane();
                setUserCommandPane();
                setBar();
        }


        /*
         * Initializes the turtle display's front end and back end relationship
         */
        private void setDisplay() {
//        TreeMap<Double,String> colorMap = new TreeMap<Double, String>();
//        colorMap.put(0.0, "#849775");
//        colorMap.put(1.0, "#1518b4");
//        colorMap.put(2.0, "#5df45d");
//        colorMap.put(3.0, "#7182a7");
//        colorMap.put(4.0, "#b73547");
//        colorMap.put(6.0, "#b73547");
//        TreeMap<Double, String> imageMap = new TreeMap<Double, String>();
//        imageMap.put(0.0, "black.png");
//        imageMap.put(1.0, "blue.png");
//        imageMap.put(2.0, "green.png");
//        imageMap.put(3.0, "red.png");
//        imageMap.put(4.0, "turtle.png");
//
//        imageMap.put(6.0, "turtle.png");
        //DisplayModel displayModel = new DisplayModel(colorMap, imageMap);
        DisplayModel displayModel = new DisplayModel(myState.getColorMap(), myState.getImages());
                DisplayView displayView = new DisplayView(getRoot());
                displayModel.addObserver(displayView);
                modelMap.setDisplay(displayModel);
                System.out.println(myState.getBackColorIndex());
                modelMap.getDisplay().setBackgroundColorIndex(myState.getBackColorIndex());
                displayModel.setToAnimate(true);
                displayModel.notifyObservers();
//                setTurtlePane(displayModel);
        }


        public void establishRelationship(Observable myModel, View myView){
                myModel.addObserver(myView);
                myModel.notifyObservers();
                getRoot().getChildren().add(myView.getMyRoot());
        }

        /*
         * Sets the Pane for the user input text area
         */
        private void setInputPane() {
                myIV  = new InputView(parser, inputText);
                getRoot().getChildren().add(myIV.getMyRoot());
        }

        /*
         * Sets the Pane for the user history
         */
        private void setHistoryPane() {
                HistoryModel hpm = new HistoryModel();
                hpv = new HistoryPaneView(inputText);
                modelMap.setHistory(hpm);
                establishRelationship(hpm, hpv);
                initializeHistory(hpm, myState.getHistory());
                hpm.updateView();
        }
        
        private void initializeHistory(HistoryModel hpm, List<String> history){
                for(String n: myState.getHistory()){
                        hpm.addToHistory(n);
                }
        }
        
        
        /*
         * Sets the Pane for the current user-defined variables in the environment
         */
        private void setVariablePane() {
                VariableModel varModel = new VariableModel();
                varView = new VariableView(inputText, getMyLang());
                modelMap.setVariable(varModel);
                establishRelationship(varModel, varView);
                initializeVariables(varModel, myState.getVariables());
                varModel.updateView();
        }
        
        private void initializeVariables(VariableModel vpm, Map<String, Double> vars){
                for (String n: vars.keySet()){
                        vpm.setVariable(n, vars.get(n));
                }
        }
        
        /*
         * Sets the Pane for the current user-defined methods in the environment
         */
        private void setUserCommandPane() {
                CommandsModel commandModel = new CommandsModel();
                commandView = new CommandsView(inputText);
                modelMap.setCommands(commandModel);
                establishRelationship(commandModel, commandView);
                //initializeCommands(commandModel, myState.getCommands(), myState.getCommandVars());
                commandModel.updateView();
        }
        
        /*
        private void initializeCommands(CommandsModel cpm, Map<String, List<Command>> commands, Map<String, List<Command>> commVars){
                for (String n: commands.keySet()){
                        cpm.setCommands(n, commands.get(n));
                        cpm.setVariables(n, commVars.get(n));
                }
        }
        */
        
        /*
         * Sets the Pane for the current status of the various turtles on the
         * display
         */
        private void setTurtlePane(DisplayModel dm) {
                turtleView = new TurtleIDView(inputText);
                getRoot().getChildren().add(turtleView.getMyRoot());
                dm.addObserver(turtleView);
        }

        private void setPenUpDown() {

        }

        private void setPenThicknessInputField() {

        }

        private void setPenThickness() {

        }

        private void setPenStyleBox() {

        }

        private void setPenStyle() {

        }

        public String getMyLang() {
                return myLang;
        }
}