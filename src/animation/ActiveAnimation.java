package animation;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.TurtleModel;

public class ActiveAnimation extends CustomAnimation{
    
    private List<Rectangle> actives = new ArrayList<>();
    
    public ActiveAnimation (Group root) {
        super(root);
    }

    @Override
    void setUpSingleView (int frameNumber, int index) {
        actives.add(initActiveRect());
        setNumNodes(actives.size()); 
    }

    @Override
    Animation generateSingleAnimation (int frameNumber, int index, int speed) {
        TurtleModel turtle = getDisplayModel().getTurtleList().get(index);
        if (turtle.isActive()) {
            Rectangle area = actives.get(index);
            area.setStroke(Color.BLACK);
            TranslateTransition at =
                    new TranslateTransition(Duration.millis(getTwoStateTranslationTime(turtle, frameNumber, speed)), 
                                            area);
            at.setFromX(getDrawableX(turtle.getPositionX(frameNumber-1)));
            at.setFromY(getDrawableY(turtle.getPositionY(frameNumber-1)));
            at.setToX(getDrawableX(turtle.getPositionX(frameNumber)));
            at.setToY(getDrawableY(turtle.getPositionY(frameNumber)));
            return at;
        }
        else {
            actives.get(index).setStroke(Color.TRANSPARENT);
        }
        return null;
    }
    
    public Rectangle initActiveRect () {
        Rectangle area = new Rectangle(0, 0, 50, 50);
        area.setFill(Color.TRANSPARENT);
        area.setStroke(Color.TRANSPARENT);
        getRoot().getChildren().add(area);
        return area;
    }

}
