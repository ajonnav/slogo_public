package view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import constants.UIConstants;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.TurtleModel;

public class CoordinateView implements IView{
	
	private HBox coordBox;
	private TurtleModel turtleModel;
	
	private Text xCoord;
	private Text yCoord;
	private Text heading;
	
	public CoordinateView(HBox hb, TurtleModel turtleModel){
		coordBox = hb;
		this.turtleModel = turtleModel;
		
		xCoord = new Text();
		yCoord = new Text();
		heading = new Text();
		
		xCoord = updateText(workSpaceCoordinate(turtleModel.getPositionX()), "X Coord: ", xCoord);
		yCoord = updateText(workSpaceCoordinate(turtleModel.getPositionY()), "Y Coord: ", yCoord);
		heading = updateText(workSpaceHeading(turtleModel.getHeading()), "Heading: ", heading);
		
		coordBox.getChildren().add(xCoord);
		coordBox.getChildren().add(yCoord);
		coordBox.getChildren().add(heading);
	}

	private Double workSpaceCoordinate(double modelCoordinate){
		return (double) Math.round(-(modelCoordinate - UIConstants.CANVAS_SIZE/2));
	}
	
	private Double workSpaceHeading(double heading){
		return (360-heading)%360;
	}
	
	private Text updateText(Double setting, String text, Text field){
		field.setText(text + setting.toString() + "  ");
		return field;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof TurtleModel){
			updateText(workSpaceCoordinate(turtleModel.getPositionX()), "X Coord: ", xCoord);
			updateText(workSpaceCoordinate(turtleModel.getPositionY()), "Y Coord: ", yCoord);
			updateText(workSpaceHeading(turtleModel.getHeading()), "Heading: ", heading);
		}	
	}
}
