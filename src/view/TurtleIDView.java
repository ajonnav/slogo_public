package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import addons.Features;
import constants.UIConstants;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.DisplayModel;
import model.ViewableDisplayModel;
import model.ViewableTurtleModel;


public class TurtleIDView extends ScrollView {

    private VBox myBox;
    private Features features;
    private ViewableDisplayModel displayModel;
    private List<ComboBox<HBox>> myColorBoxes;
    private List<Text> myTexts;
    private List<Text> myActives;

    public TurtleIDView () {
        features = new Features();
        getMyPane().setLayoutX(UIConstants.TURTLE_PANE_X);
        getMyPane().setLayoutY(UIConstants.LOWER_PANE_Y);
        getMyPane().setMinSize(UIConstants.TURTLE_MIN_W, UIConstants.LOWER_PANE_HEIGHT);
        getMyPane().setMaxSize(UIConstants.MAX_PANE, UIConstants.LOWER_PANE_HEIGHT);
        setMyName(myBundle.getString("Turtles"));
        myColorBoxes = new ArrayList<>();
        myTexts = new ArrayList<>();
        myActives = new ArrayList<>();
        myBox = getMyBox();
    }

    public void update (Observable o, Object arg) {
        if (o instanceof ViewableDisplayModel) {
            displayModel = (ViewableDisplayModel) o;
            if (displayModel.isToUpdateIDView()) {
                displayModel.setToUpdateIDView(false);
                displayModel = (DisplayModel) o;
                for (int i = myColorBoxes.size(); i < displayModel.getViewableTurtleList().size(); i++) {
                    setUpViews(i);
                }
                for (int i = 0; i < displayModel.getViewableTurtleList().size(); i++) {
                    updateComboBoxes(i);
                    updateText(i);
                }
            }
        }
    }
    
    public void setUpViews(int i) {
        List<ComboBox<HBox>> myCombos = createComboBoxes(i);
        VBox v = new VBox();
        List<ViewableTurtleModel> turtles = displayModel.getViewableTurtleList();
        Text act = new Text(Boolean.toString(turtles.get(i).isActive(turtles.get(i).getFrameNumber()-1)));
        v.getChildren().add(new Text(myBundle.getString("Turt") + Integer.toString(i + 1)));
        myActives.add(act);
        v.getChildren().add(act);
        List<Text> initList = createTexts(i);
        v.getChildren().addAll(initList);
        v.getChildren().addAll(myCombos);
        myBox.getChildren().add(v);
    }
    
    public List<Text> createTexts(int i){
        List<ViewableTurtleModel> turtles = displayModel.getViewableTurtleList();
        List<Text> myL = new ArrayList<>();
        Text t = new Text("\t" + "("
                + Double.toString(Math.round(turtles.get(i).getPositionX(turtles.get(i).getFrameNumber()-1))) + ","
                + Double.toString(Math.round(turtles.get(i).getPositionY(turtles.get(i).getFrameNumber()-1))) + ")");
        myTexts.add(t);
        myL.add(t);
        return myL;
    }
    
    public List<ComboBox<HBox>> createComboBoxes (int i) {
        List<ComboBox<HBox>> myList = new ArrayList<ComboBox<HBox>>();
        ComboBox<HBox> penColorComboBox = features.makeColorPicker(1000, 0,
                                                                   UIConstants.COLOR_SELECTOR_WIDTH,
                                                                   UIConstants.BORDER_WIDTH);
        penColorComboBox.setOnAction(e -> penChange(i, (HBox) penColorComboBox.getValue()));
        myList.add(penColorComboBox);
        myColorBoxes.add(penColorComboBox);
        return myList;
    }

    public void updateComboBoxes (int i) {
        features.updateComboBoxOptions(myColorBoxes.get(i), displayModel.getColorMap());
        Double penID = displayModel.getViewableTurtleList().get(i).getPenColorIndex();
        myColorBoxes.get(i).setPromptText("Color " + penID.intValue());
    }

    public void penChange (int ID, HBox box) {
        Text t = (Text) box.getChildren().get(1);
        Integer ind = Integer.parseInt(t.getText());
        displayModel.getViewableTurtleList().get(ID).setPenColorIndex(new double[] { ind });
        updateComboBoxes(ID);
    }

    public void updateText(int i){
       List<ViewableTurtleModel> turtles = displayModel.getViewableTurtleList();
       myTexts.get(i).setText("\t" + "("
               + Double.toString(Math.round(turtles.get(i).getPositionX(turtles.get(i).getFrameNumber()-1))) + ","
               + Double.toString(Math.round(turtles.get(i).getPositionY(turtles.get(i).getFrameNumber()-1))) + ")");
       myActives.get(i).setText(Boolean.toString(turtles.get(i).isActive(turtles.get(i).getFrameNumber()-1)));
   }
}