package view;

import java.util.Observable;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.TurtleModel;

import java.util.ResourceBundle;

import constants.UIConstants;

public class CoordinateView extends View {

    private HBox coordBox;

    private Text xCoord;
    private Text yCoord;
    private Text heading;
    private Text penStat;
    private ResourceBundle myResources;
    
    //or refactor to be a single string to be shorter, will see
    public CoordinateView (double penState, double initX, double initY, double initHeading) {
        coordBox = new HBox();
		coordBox.setLayoutX(UIConstants.COORDINATE_LOCATION_X);
		coordBox.setLayoutY(UIConstants.COORDINATE_LOCATION_Y);
		coordBox.setMaxSize(UIConstants.RECT_X, UIConstants.BORDER_WIDTH);
        xCoord = new Text();
        yCoord = new Text();
        heading = new Text();
        penStat = new Text();
        myResources = ResourceBundle.getBundle(UIConstants.DEFAULT_RESOURCE + UIConstants.SCREEN_LANG);
        
        xCoord = updateTurtleStat(initX, myResources.getString("xcoord"), xCoord);
        yCoord = updateTurtleStat(initY, myResources.getString("ycoord"), yCoord);
        heading = updateTurtleStat(initHeading, myResources.getString("heading"), heading);
        penStat = updatePenStatus(penState, myResources.getString("pendu"), penStat);
        		
        coordBox.getChildren().addAll(xCoord, yCoord, heading, penStat);

    }

    private Double xWorkSpaceCoordinate (double modelCoordinate) {
        return (double) Math.round(modelCoordinate);
    }

    private Double yWorkSpaceCoordinate (double modelCoordinate) {
        return (double) Math.round(modelCoordinate);
    }

    private Double workSpaceHeading (double heading) {
        return Math.abs((90 - heading) % 360);
    }
    
    private Text updateTurtleStat (Double setting, String text, Text field) {
        field.setText(text + setting.toString() + "  ");
        return field;
    }

    private Text updatePenStatus (double status, String text, Text field) {
    	if (status==1)
    		field.setText(text + myResources.getString("true"));
    	else 
    		field.setText(text + myResources.getString("false"));
    	return field;
    }
    
    public HBox getMyHBox(){
    	return coordBox;
    }
    
    @Override
    public void update (Observable o, Object arg) {
        if (o instanceof TurtleModel) {
        	TurtleModel turtleModel = (TurtleModel) o;
            updateTurtleStat(xWorkSpaceCoordinate(turtleModel.getPositionX()), myResources.getString("xcoord"), xCoord);
            updateTurtleStat(yWorkSpaceCoordinate(turtleModel.getPositionY()), myResources.getString("ycoord"), yCoord);
            updateTurtleStat(workSpaceHeading(turtleModel.getHeading()), myResources.getString("heading"), heading);
            updatePenStatus(turtleModel.getPenStatus(), myResources.getString("pendu"), penStat);
        }
    }
}
