package animation;

import java.util.ArrayList;
import java.util.List;

import constants.UIConstants;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import model.ViewableLineModel;
import model.ViewablePenModel;
import model.ViewableTurtleModel;


public class LineAnimation extends CustomAnimation {

    private List<Group> lineViewGroups = new ArrayList<>();

    public LineAnimation (Group root) {
        super(root);
    }

    @Override
    void setUpSingleView (int frameNumber, int index) {
        lineViewGroups.add(new Group());
        getRoot().getChildren().add(lineViewGroups.get(index));
        setNumNodes(lineViewGroups.size());
    }

    @Override
    Animation generateSingleAnimation (int frameNumber, int index, int speed) {
        ViewableTurtleModel turtle = getDisplayModel().getViewableTurtleList().get(index);
        double translationTime = getTwoStateTranslationTime(turtle, frameNumber, speed);
        if (turtle.getViewableLineList(frameNumber - 1).size() + 1 == turtle.getViewableLineList(frameNumber)
                .size()) {
            return drawLine(turtle.getViewablePen(turtle.getFrameNumber()-1), turtle.getViewableLineList(frameNumber)
                    .get(turtle.getViewableLineList(frameNumber).size() - 1), translationTime, index);
        }
        if (turtle.getViewableLineList(turtle.getFrameNumber()-1).size() == 0) {
            lineViewGroups.get(index).getChildren().clear();
        }
        return null;
    }

    public SequentialTransition drawLine (ViewablePenModel pen,
                                          ViewableLineModel line,
                                          double translationTime,
                                          int turtleID) {
        SequentialTransition st = new SequentialTransition();
        double x1 = getDrawableX(line.getX1());
        double x2 = getDrawableX(line.getX2());
        double y1 = getDrawableY(line.getY1());
        double y2 = getDrawableY(line.getY2());
        double xDiff = x2 - x1;
        double yDiff = y2 - y1;
        double num = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
        for (int i = 0; i < num; i++) {
            Line l = new Line(x1, y1, x1 + xDiff / num * i, y1 + yDiff / num * i);
            l.setTranslateX(UIConstants.TURTLE_IMAGE_WIDTH / 2);
            l.setTranslateY(UIConstants.TURTLE_IMAGE_WIDTH / 2);
            l.setStroke(Color.web(pen.getColorString()));
            l.setStrokeWidth(pen.getSize());
            for(Double d: pen.getStyle()) {
            	l.getStrokeDashArray().add(d);
            }
            lineViewGroups.get(turtleID).getChildren().add(l);
            l.setOpacity(0);
            FadeTransition ft = new FadeTransition(Duration.millis(translationTime / num), l);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.setCycleCount(1);
            st.getChildren().add(ft);
        }
        return st;
    }

}
