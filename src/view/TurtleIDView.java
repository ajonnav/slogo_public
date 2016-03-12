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

public class TurtleIDView extends ScrollView {
	
	private VBox myBox;
	private Features features;
	private DisplayModel displayModel;
	private List<ComboBox<HBox>> myColorBoxes;
	private List<ComboBox<HBox>> myImageBoxes;
	
	public TurtleIDView() {
		features = new Features();
		getMyPane().setLayoutX(UIConstants.TURTLE_PANE_X);
		getMyPane().setLayoutY(UIConstants.LOWER_PANE_Y);
		getMyPane().setMinSize(UIConstants.TURTLE_MIN_W, UIConstants.LOWER_PANE_HEIGHT);
		getMyPane().setMaxSize(UIConstants.MAX_PANE, UIConstants.LOWER_PANE_HEIGHT);
		setMyName(myBundle.getString("Turtles"));
		refresh();
		myColorBoxes = new ArrayList<>();
		myImageBoxes = new ArrayList<>();
		myBox = getMyBox();
	}
	

	public void update(Observable o, Object arg) {
		if(o instanceof DisplayModel){
			displayModel = (DisplayModel) o;
			if(displayModel.isToUpdateIDView()) {
				displayModel.setIsToUpdateIDView(false);
				displayModel = (DisplayModel) o;
				for(int i = myColorBoxes.size(); i < displayModel.getTurtleList().size(); i++){
					List<ComboBox<HBox>> myCombos = createComboBoxes(i);
					VBox v = new VBox();
					v.getChildren().addAll(myCombos);
					myBox.getChildren().add(v);
				}
				for(int i = 0; i < displayModel.getTurtleList().size(); i++) {
					updateComboBoxes(i);
				}
			}
		}
	}
	
	public List<ComboBox<HBox>> createComboBoxes(int i){
        List<ComboBox<HBox>> myList = new ArrayList<ComboBox<HBox>>();
	    ComboBox<HBox> imageColorComboBox = features.makeColorPicker(600, 0, 
	    		UIConstants.COLOR_SELECTOR_WIDTH, UIConstants.BORDER_WIDTH);
		ComboBox<HBox> penColorComboBox = features.makeColorPicker(1000, 0, 
				UIConstants.COLOR_SELECTOR_WIDTH, UIConstants.BORDER_WIDTH);
	    imageColorComboBox.setOnAction(e -> imageChange(i, (HBox) imageColorComboBox.getValue()));
	    penColorComboBox.setOnAction(e -> penChange(i, (HBox) penColorComboBox.getValue()));
        myList.add(penColorComboBox);
        myList.add(imageColorComboBox);
        myColorBoxes.add(penColorComboBox);
        myImageBoxes.add(imageColorComboBox);
        return myList;
	}
	
	public void updateComboBoxes(int i) {
	    features.updateComboBoxOptionsImage(myColorBoxes.get(i), displayModel.getImageMap());
        features.updateComboBoxOptions(myImageBoxes.get(i), displayModel.getColorMap());
        Double penID = displayModel.getTurtleList().get(i).getPenColorIndex();
        Double imageID = displayModel.getTurtleList().get(i).getImageIndex();
        myColorBoxes.get(i).setPromptText("Color " + penID.intValue());
        myImageBoxes.get(i).setPromptText("Image " + imageID.intValue());
        System.out.println("hi");
	}
	
	public void penChange(int ID, HBox box){
		Text t = (Text) box.getChildren().get(1);
        Integer ind = Integer.parseInt(t.getText());
		displayModel.getTurtleList().get(ID).setPenColorIndex(new double[]{ind});
		updateComboBoxes(ID);
	}
	
	public void imageChange(int ID, HBox box){
		Text t = (Text) box.getChildren().get(1);
        Integer ind = Integer.parseInt(t.getText());
		displayModel.getTurtleList().get(ID).setImageIndex(new double[]{ind});
		updateComboBoxes(ID);
	}
}
