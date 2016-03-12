package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;
import addons.Features;
import animation.StampAnimation;
import animation.TurtleAnimation;
import constants.UIConstants;
import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.ViewableDisplayModel;



public class DisplayView implements IView {

    private Canvas backgroundCanvas;
    private GraphicsContext backgroundGC;
    private ComboBox<HBox> backgroundColorComboBox;
    private TurtleAnimation turtleAnim;
    private StampAnimation stampAnim;
    private Features features = new Features();
    private List<Animation> animations = new ArrayList<>();
    private int lastExpressionFrameNumber = 1;

    public DisplayView (Group root) {
        this.backgroundCanvas = features.makeCanvas(UIConstants.CANVAS_X, UIConstants.BORDER_WIDTH,
                                                    UIConstants.CANVAS_SIZE,
                                                    UIConstants.CANVAS_SIZE,
                                                    Color.GREEN);
        this.backgroundGC = backgroundCanvas.getGraphicsContext2D();
        this.backgroundColorComboBox = features.makeColorPicker(UIConstants.BACKGROUND_PICK_X,
                                                                UIConstants.ZERO,
                                                                UIConstants.COLOR_SELECTOR_WIDTH,
                                                                UIConstants.BORDER_WIDTH);
        root.getChildren().add(backgroundCanvas);
        root.getChildren().add(backgroundColorComboBox);
        this.turtleAnim = new TurtleAnimation(root);
        this.stampAnim = new StampAnimation(root);
    }

    @Override
    public void update (Observable o, Object arg) {
        if (o instanceof ViewableDisplayModel) {
        	ViewableDisplayModel displayModel = (ViewableDisplayModel) o;
            updateBackground(displayModel);
            animateDisplay(displayModel, 1);
        }
    }

    public void updateBackground (ViewableDisplayModel displayModel) {
        features.updateComboBoxOptions(backgroundColorComboBox, displayModel.getColorMap());
        String backgroundColorString = displayModel.getBackgroundColorIndex() + " " +
                                       displayModel.getColorMap().get(displayModel.getBackgroundColorIndex());
        backgroundGC.clearRect(0, 0, backgroundCanvas.getWidth(),
                               backgroundCanvas.getHeight());
        backgroundGC.setFill(Color.web(backgroundColorString.split(" ")[1]));
        backgroundGC.fillRect(0, 0, backgroundCanvas.getWidth(), backgroundCanvas.getHeight());
    }

    public void animateDisplay (ViewableDisplayModel displayModel, int speed) {
        if (displayModel.isToAnimate()) {
            displayModel.setToAnimate(false);
            animations.clear();
            drawObjects(displayModel, speed);
            SequentialTransition st = new SequentialTransition();
            animations.stream().sequential()
                    .collect(Collectors.toCollection( () -> st.getChildren()));
            st.play();
            lastExpressionFrameNumber = displayModel.getViewableTurtleList().get(0).getFrameNumber();
        }
    }

    public void drawObjects (ViewableDisplayModel displayModel, int speed) {
        turtleAnim.setDisplayModel(displayModel);
        stampAnim.setDisplayModel(displayModel);
        for (int i = lastExpressionFrameNumber; i < displayModel.getViewableTurtleList().get(0)
                .getFrameNumber(); i++) {    
            turtleAnim.setUpView(i, speed);
            stampAnim.setUpView(i, speed);
            animations.addAll(turtleAnim.generateAnimations(i, speed));
            animations.addAll(stampAnim.generateAnimations(i, speed));
        }
    }
}