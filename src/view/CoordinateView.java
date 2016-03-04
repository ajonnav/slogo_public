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

    public CoordinateView (HBox hb, double initX, double initY, double initHeading) {
        coordBox = hb;
        xCoord = new Text();
        yCoord = new Text();
        heading = new Text();
        xCoord = updateText(xWorkSpaceCoordinate(initX), "X Coord: ", xCoord);
        yCoord = updateText(yWorkSpaceCoordinate(initY), "Y Coord: ", yCoord);
        heading = updateText(workSpaceHeading(initHeading), "Heading: ", heading);
        coordBox.getChildren().add(xCoord);
        coordBox.getChildren().add(yCoord);
        coordBox.getChildren().add(heading);
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

    private Text updateText (Double setting, String text, Text field) {
        field.setText(text + setting.toString() + "  ");
        return field;
    }

    @Override
    public void update (Observable o, Object arg) {
        if (o instanceof TurtleModel) {
            TurtleModel turtleModel = (TurtleModel) o;
            updateText(xWorkSpaceCoordinate(turtleModel.getPositionX()), "X Coord: ", xCoord);
            updateText(yWorkSpaceCoordinate(turtleModel.getPositionY()), "Y Coord: ", yCoord);
            updateText(workSpaceHeading(turtleModel.getHeading()), "Heading: ", heading);
        }
    }
}
