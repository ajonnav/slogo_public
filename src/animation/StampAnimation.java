package animation;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.IStampModel;
import model.TurtleModel;


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
        TurtleModel turtle = getDisplayModel().getTurtleList().get(index);
        if (turtle.getStampList(frameNumber - 1).size() + 1 == turtle.getStampList(frameNumber)
                .size()) {
            return drawStamp(turtle.getStampList(frameNumber)
                    .get(turtle.getStampList(frameNumber).size() - 1),
                             index, speed);
        }
        if (turtle.getStampList().size() == 0) {
            stampViewGroups.get(index).getChildren().clear();
        }
        return null;
    }

    public FadeTransition drawStamp (IStampModel stamp, int index, int speed) {
        ImageView image = new ImageView();
        image.setImage(getImageFromString(stamp.getImageString()));
        image.setX(getDrawableX(stamp.getPositionX()));
        image.setY(getDrawableY(stamp.getPositionY()));
        image.setFitWidth(50);
        image.setFitHeight(50);
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
