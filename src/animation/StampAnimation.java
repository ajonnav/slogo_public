package animation;

import java.util.ArrayList;
import java.util.List;

import constants.UIConstants;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.ViewableStampModel;
import model.ViewableTurtleModel;


public class StampAnimation extends CustomAnimation {

    private List<Group> stampViewGroups = new ArrayList<>();

    public StampAnimation (Group root) {
        super(root);
    }

    @Override
    void setUpSingleView (int frameNumber, int index) {
        stampViewGroups.add(new Group());
        getRoot().getChildren().add(stampViewGroups.get(index));
        setNumNodes(stampViewGroups.size());
    }

    @Override
    Animation generateSingleAnimation (int frameNumber, int index, int speed) {
        ViewableTurtleModel turtle = getDisplayModel().getViewableTurtleList().get(index);
        if (turtle.getViewableStampList(frameNumber - 1).size() + 1 == turtle.getViewableStampList(frameNumber)
                .size()) {
            return drawStamp(turtle.getViewableStampList(frameNumber)
                    .get(turtle.getViewableStampList(frameNumber).size() - 1),
                             index, speed);
        }
        if (turtle.getViewableStampList(turtle.getFrameNumber()-1).size() == 0) {
            stampViewGroups.get(index).getChildren().clear();
        }
        return null;
    }

    public FadeTransition drawStamp (ViewableStampModel stamp, int index, int speed) {
        ImageView image = new ImageView();
        image.setImage(getImageFromString(stamp.getImageString()));
        image.setX(getDrawableX(stamp.getPositionX()));
        image.setY(getDrawableY(stamp.getPositionY()));
        image.setFitWidth(UIConstants.TURTLE_IMAGE_WIDTH);
        image.setFitHeight(UIConstants.TURTLE_IMAGE_WIDTH);
        image.setRotate(transformHeading(stamp.getHeading()));
        image.setOpacity(0);
        stampViewGroups.get(index).getChildren().add(image);
        FadeTransition ft = new FadeTransition(Duration.millis(speed), image);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        return ft;
    }

}
