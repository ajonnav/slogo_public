package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import parser.CommandParser;
import addons.Features;
import constants.UIConstants;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.DisplayModel;
import model.ModelMap;
import model.ViewableDisplayModel;
import model.ViewableTurtleModel;


public class TurtleIDView extends ScrollView {

    private VBox myBox;
    private Features features;
    private ViewableDisplayModel displayModel;
    private ModelMap myTurtleMap;
    private CommandParser myParser;
    
    private List<ComboBox<HBox>> myColorBoxes;
    private List<ComboBox<HBox>> myTurtleBoxes;
    private List<Text> myTexts;
    private List<Text> myActives;
    
    private final String turtleImage = "turtleImage";
    private final String tell = "tell";
    
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
        myTurtleBoxes = new ArrayList<>();
    }

    public void update (Observable o, Object arg) {
        if (o instanceof ViewableDisplayModel) {
            displayModel = (ViewableDisplayModel) o;
            if (displayModel.isToUpdateIDView()) {
                displayModel.setIsToUpdateIDView(false);
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
    
    /**
     * This class is gross, sad to use it, but refactoring it takes away from masterpiece time, so sorry
     * @param i
     */
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
    
	public List<ComboBox<HBox>> createComboBoxes(int ID) {
		List<ComboBox<HBox>> myList = new ArrayList<ComboBox<HBox>>();
		
		ComboBox<HBox> pens = makePenBox(ID);
		ComboBox<HBox> turtles = makeTurtleBox(ID);
		
		myList.add(pens);
		myList.add(turtles);
		myColorBoxes.add(pens);
		myTurtleBoxes.add(turtles);
		return myList;
    }

	public ComboBox<HBox> makePenBox(int ID) {
		ComboBox<HBox> penColorComboBox = features.makeHBox(1000, 0,
				UIConstants.COLOR_SELECTOR_WIDTH, UIConstants.BORDER_WIDTH);
		penColorComboBox.setOnAction(e -> penChange(ID,
				(HBox) penColorComboBox.getValue()));
		
		return penColorComboBox;
	}
	
	/**
	 * It works and follows the conventions of what we had, makes
	 * a turtle combobox hbox for us
	 * Also, is there anything more wodnerful than a turtle box?
	 * 
	 * @param i
	 * @return
	 */
	public ComboBox<HBox> makeTurtleBox(int ID) {
		ComboBox<HBox> turtleImageBox = features.makeHBox(1000, 0,
				UIConstants.COLOR_SELECTOR_WIDTH, UIConstants.BORDER_WIDTH);
		//we can't getChildren from the boxes we make... wonderful, gaaaah
		//turtleImageComboBox.getChildren();
		turtleImageBox.setOnAction(e -> changeTurtleImage(ID, (HBox) turtleImageBox.getValue()));
		return turtleImageBox;
	}
	
    public void updateComboBoxes (int i) {
        features.updateComboBoxOptions(myColorBoxes.get(i), displayModel.getColorMap());
        features.updateComboBoxOptionsImage(myTurtleBoxes.get(i), displayModel.getImageMap());
        Double penID = displayModel.getViewableTurtleList().get(i).getPenColorIndex();
        myColorBoxes.get(i).setPromptText("Color " + penID.intValue());
        //getting the box to display the current image was a greater task than I could manage with
        //the time constraint
        //myTurtleBoxes.get(i).setPromptText(displayModel.getImageMap().get(i));
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
    
   /**
    * Give us the modelMap and parser so that we can call on them to change turtle images
    * 
    * @param turtleMap
    * @param parser
    */
   public void initializeParser(ModelMap turtleMap, CommandParser parser) {
	   this.myTurtleMap = turtleMap;
	   this.myParser = parser;
   }
   
   /**
    * Fairly silly to do it this way, but to prevent having to wait to parse another
    * command for the image change to go through, like before, this method directly calls 
    * to parse by "tell ID SETSH" so set its image. We have a few images, so we'll just take
    * turtle index, n+1 and SETSH to the new val.
    * 
    * @param ID
    * @param turtleBox
    */
   public void changeTurtleImage(int ID, HBox turtleBox) {
	   updateComboBoxes(ID);
	   int newImageIndex = Integer.parseInt(((Text) turtleBox.getChildren().get(1)).getText());
	   
	   myParser.parseText(myBundle.getString(tell + (ID+1)) + " " + newImageIndex);
   }
}