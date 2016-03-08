package view;

import java.util.Observable;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.TurtleModel;


public class CoordinateView implements IView {

    private HBox coordBox;

    private Text xCoord;
    private Text yCoord;
    private Text heading;
    private Text penStat;
    
    //or refactor to be a single string to be shorter, will see
    public CoordinateView (HBox hb, double penState, double initX, double initY, double initHeading) {
        coordBox = hb;
        xCoord = new Text();
        yCoord = new Text();
        heading = new Text();
        penStat = new Text();
        
        xCoord = updateTurtleStat(initX, "X Coord: ", xCoord);
        yCoord = updateTurtleStat(initY, "Y Coord: ", yCoord);
        heading = updateTurtleStat(initHeading, "Heading: ", heading);
        penStat = updatePenStatus(penState, "Pen Down", penStat);
        		
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
    		field.setText(text + ": True ");
    	else 
    		field.setText(text + ": False");
    	return field;
    }
    
    @Override
    public void update (Observable o, Object arg) {
        if (o instanceof TurtleModel) {
        	TurtleModel turtleModel = (TurtleModel) o;
            updateTurtleStat(xWorkSpaceCoordinate(turtleModel.getPositionX()), "X Coord: ", xCoord);
            updateTurtleStat(yWorkSpaceCoordinate(turtleModel.getPositionY()), "Y Coord: ", yCoord);
            updateTurtleStat(workSpaceHeading(turtleModel.getHeading()), "Heading: ", heading);
            updatePenStatus(turtleModel.getPenStatus(), "Pen Down", penStat);
        }
    }
}
