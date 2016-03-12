package animation;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.ViewableTurtleModel;

public class TurtleAnimation extends CustomAnimation{
    
    private List<ImageView> turtleViews = new ArrayList<>();
    private Group imageViewGroup = new Group();
    private LineAnimation lineAnim;
    private ActiveAnimation activeAnim;
    
    public TurtleAnimation (Group root) {
        super(root);
        root.getChildren().add(imageViewGroup);
        this.lineAnim = new LineAnimation(root);
        this.activeAnim = new ActiveAnimation(root);
    }
   
    @Override
    public void setUpSingleView (int frameNumber, int index) {
        turtleViews.add(initTurtleImageView(getDisplayModel().getViewableTurtleList().get(index), frameNumber));    
        setNumNodes(turtleViews.size());
        lineAnim.setDisplayModel(getDisplayModel());
        activeAnim.setDisplayModel(getDisplayModel());
        lineAnim.setUpView(frameNumber, index);
        activeAnim.setUpView(frameNumber, index);
    }
    
    public ImageView initTurtleImageView (ViewableTurtleModel t, int frameNumber) {
        ImageView image = new ImageView(getImageFromString(t.getImageString(frameNumber)));
        image.setFitHeight(50);
        image.setFitWidth(50);
        image.setOpacity(0);
        image.setX(0);
        image.setY(0);
        image.setRotate(transformHeading(t.getHeading(frameNumber)));
        imageViewGroup.getChildren().add(image);
        return image;
    }

    @Override
    public Animation generateSingleAnimation (int frameNumber, int index, int speed) {
        ViewableTurtleModel turtle = getDisplayModel().getViewableTurtleList().get(index);
        SequentialTransition st = new SequentialTransition();
        st.getChildren().add(makeParallelTransition(turtle, frameNumber, speed, index));
        st.getChildren().add(makeRotateTransition(turtle, frameNumber, speed, index));
        st.getChildren().add(makeStatusTransition(turtle, frameNumber, index));
        return st;
    }
    
    public ParallelTransition makeParallelTransition(ViewableTurtleModel turtle, int frameNumber, int speed, int index) {
        ParallelTransition pt = new ParallelTransition();
        Animation lineAnimation = lineAnim.generateSingleAnimation(frameNumber, index, speed);
        if(lineAnimation != null) {
            pt.getChildren().add(lineAnimation);
        }
        Animation activeAnimation = activeAnim.generateSingleAnimation(frameNumber, index, speed);
        if(activeAnimation != null) {
            pt.getChildren().add(activeAnimation);
        }
        pt.getChildren().add(makeTranslationTransition(turtle, frameNumber, speed, index));
        return pt;
    }
    
    public TranslateTransition makeTranslationTransition(ViewableTurtleModel turtle, int frameNumber, int speed, int index) {
        double translationTime = getTwoStateTranslationTime(turtle, frameNumber, speed);
        TranslateTransition tt = new TranslateTransition(Duration.millis(translationTime), turtleViews.get(index));
        tt.setFromX(getDrawableX(turtle.getPositionX(frameNumber-1)));
        tt.setFromY(getDrawableY(turtle.getPositionY(frameNumber-1)));
        tt.setToX(getDrawableX(turtle.getPositionX(frameNumber)));
        tt.setToY(getDrawableY(turtle.getPositionY(frameNumber)));
        tt.setCycleCount(1);
        return tt;
    }
    
    public RotateTransition makeRotateTransition(ViewableTurtleModel turtle, int frameNumber, int speed, int index) {
        double rotationTime = turtle.getHeading(frameNumber-1) != turtle.getHeading(frameNumber) ? speed : 1;
        RotateTransition rt = new RotateTransition(Duration.millis(rotationTime), turtleViews.get(index));   
        rt.setFromAngle(transformHeading(turtle.getHeading(frameNumber-1)));
        rt.setToAngle(transformHeading(turtle.getHeading(frameNumber)));
        rt.setCycleCount(1);
        return rt;
    }
    
    public FadeTransition makeStatusTransition(ViewableTurtleModel turtle, int frameNumber, int index) {
        FadeTransition fs = new FadeTransition(Duration.millis(1), turtleViews.get(index));
        double opacity = turtle.getShowStatus(frameNumber) == 1 ? 1.0 : 0.0;
        fs.setFromValue(opacity);
        fs.setToValue(opacity);
        fs.setCycleCount(1);
        return fs;
    }

}
