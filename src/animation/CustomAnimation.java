package animation;

import java.util.ArrayList;
import java.util.List;
import constants.UIConstants;
import javafx.animation.Animation;
import javafx.scene.Group;
import javafx.scene.image.Image;
import model.ViewableDisplayModel;
import model.ViewableTurtleModel;

public abstract class CustomAnimation {
    
    private Group root;
    private int numNodes = 0;
    private ViewableDisplayModel displayModel;
    
    public CustomAnimation(Group root) {
        this.root = root;
    }
    
    public void setUpView(int frameNumber, int speed) {
        for(int i = numNodes; i < displayModel.getViewableTurtleList().size(); i++) {
            setUpSingleView(frameNumber, i);
        }
    };
    
    abstract void setUpSingleView(int frameNumber, int index);
    
    public List<Animation> generateAnimations(int frameNumber, int speed) {
        List<Animation> animations = new ArrayList<>();
        for(int i = 0; i < displayModel.getViewableTurtleList().size(); i++) {
            Animation a = generateSingleAnimation(frameNumber, i, speed);
            if(a != null) {
                animations.add(a);
            }
        }
        return animations;
    }
    
    abstract Animation generateSingleAnimation(int frameNumber, int index, int speed);
    
    public double getDrawableX (double x) {
        return transformX(x) + UIConstants.INITIAL_X;
    }

    public double getDrawableY (double y) {
        return transformY(y) + UIConstants.INITIAL_Y;
    }

    public double transformX (double x) {
        return x + (double) UIConstants.CANVAS_SIZE / 2;
    }

    public double transformY (double y) {
        return -y + (double) UIConstants.CANVAS_SIZE / 2;
    }

    public double transformHeading (double heading) {
        return 90 - heading;
    }
    
    public Image getImageFromString (String image) {
        return new Image(getClass().getClassLoader().getResourceAsStream(image));
    }
    
    public double getTwoStateTranslationTime(ViewableTurtleModel turtle, int frameNumber, int speed) {
        return turtle.getPositionX(frameNumber-1) != turtle.getPositionX(frameNumber) ||
                                 turtle.getPositionY(frameNumber-1) != turtle.getPositionY(frameNumber) ? speed : 1;
    }

    public Group getRoot () {
        return root;
    }

    public void setRoot (Group root) {
        this.root = root;
    }

    public ViewableDisplayModel getDisplayModel () {
        return displayModel;
    }

    public void setDisplayModel (ViewableDisplayModel displayModel) {
        this.displayModel = displayModel;
    }
    
    public void setNumNodes(int numNodes) {
        this.numNodes = numNodes;
    }
}
