package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import addons.Features;
import constants.UIConstants;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.DisplayModel;
import model.TurtleModel;
import pane.SPane;

public class TurtleIDView extends ScrollView {
	private TextArea myTA;
	private VBox myHB;
	private Map<Double, VBox> myMap;
	private Features features;
	private DisplayModel myT;
	//private Integer myIndex;
	
	public TurtleIDView(TextArea ta) {
		features = new Features();
		getMyPane().setLayoutX(UIConstants.TURTLE_PANE_X);
		getMyPane().setLayoutY(UIConstants.LOWER_PANE_Y);
		getMyPane().setMinSize(UIConstants.TURTLE_MIN_W, UIConstants.LOWER_PANE_HEIGHT);
		getMyPane().setMaxSize(400, UIConstants.LOWER_PANE_HEIGHT);
		getMyBox().getChildren().add(new Text("Turtles"));
		myTA = ta;
		myHB = getMyBox();
		myMap = new HashMap<Double,VBox>();
	}
	

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof DisplayModel){
			myT = (DisplayModel) o;
			getMyBox().getChildren().clear();
			for(int myIndex = 0; myIndex < myT.getTurtleList().size(); myIndex++){
				TurtleModel tm = myT.getTurtleList().get(myIndex);
				VBox myBox = new VBox();
				List<ComboBox> myCombos = handleComboBoxes(myIndex);
				final int currentInd = myIndex;
				myCombos.get(0).setOnAction(e -> penChange(currentInd, (HBox) myCombos.get(0).getValue()));
				myCombos.get(1).setOnAction(e -> imageChange(currentInd, (HBox) myCombos.get(0).getValue()));
//				Hyperlink item = new Hyperlink("Turtle " + Integer.toString(i+1) + " : " + Boolean.toString(tm.isActive()));
//				item.setOnAction(e -> {
//					myTA.appendText("tell ");
//				});
//				Text info = new Text("\t" +"(" + Double.toString(Math.round(tm.getPositionX())) + "," + Double.toString(Math.round(tm.getPositionY())) + ")");
//				myBox.getChildren().add(item);
				//myBox.getChildren().add(info);
				myBox.getChildren().addAll(myCombos);
				myHB.getChildren().add(myBox);
			}
		}
	}
	
	public List<ComboBox> handleComboBoxes(int i){
		ComboBox<HBox> penColorComboBox = features.makeColorPicker(1000, 0, UIConstants.COLOR_SELECTOR_WIDTH, UIConstants.BORDER_WIDTH);
	    ComboBox<HBox> imageColorComboBox = features.makeColorPicker(600, 0, UIConstants.COLOR_SELECTOR_WIDTH, UIConstants.BORDER_WIDTH);
	    features.updateComboBoxOptionsImage(imageColorComboBox, myT.getImageMap());
        features.updateComboBoxOptions(penColorComboBox, myT.getColorMap());
        Double imageID = myT.getTurtleList().get(i).getImageIndex();
        Double penID = myT.getTurtleList().get(i).getPenColorIndex();
        penColorComboBox.setValue(penColorComboBox.getItems().get(penID.intValue()));
        imageColorComboBox.setValue(imageColorComboBox.getItems().get(imageID.intValue()));
        List<ComboBox> myList = new ArrayList<ComboBox>();
        myList.add(penColorComboBox);
        myList.add(imageColorComboBox);
        return myList;
	}
	
	public void penChange(int ID, HBox myB){
		int x = ID;
		Text t = (Text) myB.getChildren().get(1);
		Integer ind = Integer.parseInt(t.getText());
		myT.getTurtleList().get(x).setPenColorIndex(new double[]{ind.intValue()});
		//myT.updateView();
	}
	
	public void imageChange(int pos, HBox myB){
		int x = pos;
		Text t = (Text) myB.getChildren().get(1);
		Integer ind = Integer.parseInt(t.getText());
		System.out.println(myT.getTurtleList().get(x).getImageIndex());
		System.out.print(ind.intValue() + " ind value");
		myT.getTurtleList().get(x).setImageIndex(new double[]{ind.intValue()});
		System.out.println(myT.getTurtleList().get(x).getImageIndex());
		myT.updateView();
	}

}
