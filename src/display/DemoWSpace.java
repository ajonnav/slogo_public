package display;

import model.CommandsModel;
import model.DisplayModel;
import model.HistoryModel;
import model.IHistoryModel;
import model.IVariableModel;
import model.ModelMap;
import model.TurtleModel;
import model.VariableModel;
import parser.CommandParser;
import preferences.PrefLoader;
import preferences.PrefWriter;
import preferences.saveState;
import view.CommandsView;
import view.DisplayView;
import view.HistoryPaneView;
import view.InputView;
import view.TurtleIDView;
import view.VariableView;
import view.View;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Optional;
import java.util.TreeMap;
import java.util.Optional;

import command.Command;
import addons.Features;
import addons.MenuMaker;
import addons.WMenu;
import constants.UIConstants;

public class DemoWSpace extends Screen {

        private CommandParser parser;
        private TextArea inputText;
        private String myLang;
        private HistoryPaneView hpv;
        private ModelMap modelMap;
        private saveState myState;

        
        private VariableView varView;
        private TurtleIDView turtleView;
        private InputView myIV;
        private CommandsView commandView;

        public DemoWSpace(saveState myS) {
                myState = myS;
                modelMap = new ModelMap();
        }

        @Override
        public void setUpScene() {
                //getRoot().getStylesheets().add(UIConstants.DEFAULT_RESOURCE + "demo.css");
                setScene(new Scene(getRoot(), UIConstants.WIDTH, UIConstants.HEIGHT, Color.LIGHTBLUE));
                inputText = new TextArea();
          }


        public void switchWS() {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle(getResources().getString("FileSelect"));
                fileChooser.getExtensionFilters().addAll(
                                new ExtensionFilter(
                                                getResources().getString("Files"),
                                                getResources().getString("srl")));

                File selectedFile = fileChooser.showOpenDialog(getStage());

                PrefLoader loader = new PrefLoader();
                saveState myState = loader.load(selectedFile);
                
                DemoWSpace myW = new DemoWSpace(myState);
                myW.setLang(myLang);
                myW.begin();
        }

        public void setBar() {
                WMenu workspaceMenu = new WMenu();
                
                MenuMaker menuMaker = new MenuMaker();
                MenuBar myMenu = menuMaker.getMenu();
                Menu fileMenu = menuMaker.addMenu(getResources().getString("FileCommand"));
                menuMaker.addMenuItem(getResources().getString("HelpTitle"), e -> openHelpPage(), fileMenu);
                menuMaker.addMenuItem(getResources().getString("NewCommand"), e -> switchWS(), fileMenu);
                menuMaker.addMenuItem(getResources().getString("SaveCommand"), e -> setPrefs(), fileMenu);
                Menu toggleMenu = menuMaker.addMenu(getResources().getString("Toggle"));
                menuMaker.addMenuItem(getResources().getString("cToggle"), e -> noVars(varView), toggleMenu);
                menuMaker.addMenuItem(getResources().getString("hToggle"), e -> noVars(hpv), toggleMenu);
                menuMaker.addMenuItem(getResources().getString("tToggle"), e -> noVars(turtleView), toggleMenu);
                menuMaker.addMenuItem(getResources().getString("vToggle"), e -> noVars(commandView), toggleMenu);
                menuMaker.addMenuItem(getResources().getString("uToggle"), e -> noVars(myIV), toggleMenu);
                Menu editMenu = menuMaker.addMenu(getResources().getString("EditCommand"));
                menuMaker.addMenuItem(getResources().getString("penStatus"), e -> setPenUpDown(), editMenu);
                getRoot().getChildren().add(myMenu);
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
        TreeMap<Double,String> colorMap = new TreeMap<Double, String>();
        colorMap.put(0.0, "#849775");
        colorMap.put(1.0, "#1518b4");
        colorMap.put(2.0, "#5df45d");
        colorMap.put(3.0, "#7182a7");
        colorMap.put(4.0, "#b73547");
        colorMap.put(6.0, "#b73547");
        TreeMap<Double, String> imageMap = new TreeMap<Double, String>();
        imageMap.put(0.0, "black.png");
        imageMap.put(1.0, "blue.png");
        imageMap.put(2.0, "green.png");
        imageMap.put(3.0, "red.png");
        imageMap.put(4.0, "turtle.png");

        imageMap.put(6.0, "turtle.png");
        DisplayModel displayModel = new DisplayModel(colorMap, imageMap);
        //DisplayModel displayModel = new DisplayModel(myState.getColorMap(), myState.getImages());
                DisplayView displayView = new DisplayView(getRoot());
                displayModel.addObserver(displayView);
                modelMap.setDisplay(displayModel);
                System.out.println(myState.getBackColorIndex());
                modelMap.getDisplay().setBackgroundColorIndex(myState.getBackColorIndex());
                displayModel.setToAnimate(true);
                displayModel.notifyObservers();
//                setTurtlePane(displayModel);
        }

        /*
         * Hides/shows a user view from the Scene
         */
        private void noVars(View variables) {
                if (getRoot().getChildren().contains(variables.getMyRoot())) {
                        getRoot().getChildren().remove(variables.getMyRoot());
                } else {
                        getRoot().getChildren().add(variables.getMyRoot());
                }
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
                System.out.println(myState.getVariables().keySet());
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

        /*
         * Opens an new window with a help page for reference
         */
        private void openHelpPage() {
                Stage myStage = new Stage();
                Group helpRoot = new Group();
                Scene scene = new Scene(helpRoot, UIConstants.WIDTH, UIConstants.HEIGHT);
                myStage.setTitle(getResources().getString("HelpTitle"));
                myStage.setScene(scene);
                myStage.show();
                WebView browser = new WebView();
                browser.setPrefSize(UIConstants.WIDTH, UIConstants.HEIGHT);
                helpRoot.getChildren().add(browser);
                browser.getEngine().load(DemoWSpace.class.getResource("/references/help.html").toExternalForm());
        }

        private void setPrefs() {
                String newTitle = newTextInput("File Name", "Save File", "Enter New File Name", "File:");
                PrefWriter setter = new PrefWriter(modelMap, newTitle, myLang);
                setter.writeToSrl();
        }

        private String newTextInput(String holder, String title, String header, String prompt) {
                TextInputDialog dialog = new TextInputDialog(holder);
                dialog.setTitle(title);
                dialog.setHeaderText(header);
                dialog.setContentText(prompt);
                Optional<String> input = dialog.showAndWait();
                if (input.isPresent()) {
                        String newTitle = input.get();
                        return newTitle;
                } else {
                        return null;
                }
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