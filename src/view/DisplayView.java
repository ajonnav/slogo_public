package view;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import addons.Features;
import constants.UIConstants;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.DisplayModel;
import model.LineModel;
import model.StampModel;
import model.TurtleModel;


public class DisplayView implements IView {

    private Canvas backgroundCanvas;
    private GraphicsContext backgroundGC;
    private ComboBox<HBox> backgroundChange;
    private ComboBox<HBox> backgroundColorComboBox;
    private ComboBox<HBox> penChange;
    private ComboBox<HBox> imageChange;
    private Features features;
    private List<Animation> animations;
    private Group imageViewGroup;
    private int lastExpressionFrameNumber;
    private List<ImageView> turtleViews;

    public DisplayView (Group root) {
        this.features = new Features();
        this.backgroundCanvas = features.makeCanvas(UIConstants.CANVAS_X, UIConstants.BORDER_WIDTH,
                                                    UIConstants.CANVAS_SIZE,
                                                    UIConstants.CANVAS_SIZE,
                                                    Color.GREEN);
        this.backgroundGC = backgroundCanvas.getGraphicsContext2D();
        animations = new ArrayList<>();
        turtleViews = new ArrayList<>();
        this.imageViewGroup = new Group();
        this.lastExpressionFrameNumber = 1;
        this.backgroundColorComboBox =
                features.makeColorPicker(UIConstants.BACKGROUND_PICK_X,
                                         UIConstants.ZERO,
                                         UIConstants.COLOR_SELECTOR_WIDTH,
                                         UIConstants.BORDER_WIDTH);
        root.getChildren().add(backgroundCanvas);
        root.getChildren().add(backgroundColorComboBox);
        root.getChildren().add(imageViewGroup);
    }

    public void setUpLayers () {
        this.backgroundCanvas = features.makeCanvas(UIConstants.CANVAS_X, UIConstants.BORDER_WIDTH,
                                                    UIConstants.CANVAS_SIZE,
                                                    UIConstants.CANVAS_SIZE,
                                                    Color.TRANSPARENT);
        this.backgroundGC = backgroundCanvas.getGraphicsContext2D();
    }

    @Override
    public void update (Observable o, Object arg) {
        if (o instanceof DisplayModel) {
            DisplayModel displayModel = (DisplayModel) o;
            if (displayModel.isToAnimate()) {
                displayModel.setToAnimate(false);
                features.updateComboBoxOptions(backgroundColorComboBox, displayModel.getColorMap());
                String backgroundColorString = displayModel.getBackgroundColorIndex() + " " +
                                               displayModel.getBackgroundColor();
                //backgroundColorComboBox.setValue(backgroundColorString);
                backgroundGC.clearRect(0, 0, backgroundCanvas.getWidth(),
                                       backgroundCanvas.getHeight());
                drawBackgroundRectangle(Color.web(backgroundColorString.split(" ")[1]));
                animations.clear();
                drawTurtles(displayModel);
                SequentialTransition st = new SequentialTransition();
                for (Animation a : animations) {
                    st.getChildren().add(a);
                }
                st.play();
                lastExpressionFrameNumber = displayModel.getNumFrames();
            }
        }
    }

    public void drawTurtles (DisplayModel displayModel) {
        for (int i = lastExpressionFrameNumber; i < displayModel.getNumFrames(); i++) {
            for (int j = turtleViews.size(); j < displayModel.getFrame(i - 1).size(); j++) {
                ImageView image = initImageView(displayModel.getFrame(i - 1).get(j));
                imageViewGroup.getChildren().add(image);
                turtleViews.add(image);
            }
            for (int j = 0; j < displayModel.getFrame(i).size(); j++) {
                animate(turtleViews.get(j), displayModel.getFrame(i - 1).get(j),
                        displayModel.getFrame(i).get(j));
            }
        }
        // drawStamps(displayModel.getFrame(i).get(j).getStampList());
        // drawLines(t.getLineList());
    }

    private void animate (ImageView image, TurtleModel prev, TurtleModel next) {
        double translationTime =
                prev.getPositionX() != next.getPositionX() ||
                                 prev.getPositionY() != next.getPositionY() ? 1000 : 1;
        double rotationTime = prev.getHeading() != next.getHeading() ? 1000 : 1;
        TranslateTransition tt = new TranslateTransition(Duration.millis(translationTime), image);
        RotateTransition rt = new RotateTransition(Duration.millis(rotationTime), image);
        tt.setFromX(getDrawableX(prev.getPositionX()));
        tt.setFromY(getDrawableY(prev.getPositionY()));
        tt.setToX(getDrawableX(next.getPositionX()));
        tt.setToY(getDrawableY(next.getPositionY()));
        tt.setCycleCount(1);
        rt.setFromAngle(transformHeading(prev.getHeading()));
        rt.setToAngle(transformHeading(next.getHeading()));
        rt.setCycleCount(1);
        animations.add(tt);
        animations.add(rt);
    }

    public ImageView initImageView (TurtleModel t) {
        ImageView image = new ImageView();
        image.setImage(getImageFromString(t.getImageString()));
        image.setFitHeight(50);
        image.setFitWidth(50);
        image.setOpacity(t.getShowStatus());
        image.setX(0);
        image.setY(0);
        image.setRotate(transformHeading(t.getHeading()));
        return image;
    }

    public double getDrawableX (double x) {
        return transformX(x) + UIConstants.INITIAL_X;
    }

    public double getDrawableY (double y) {
        return transformY(y) + UIConstants.INITIAL_Y;
    }

    public void drawLines (List<LineModel> list) {
        list.stream().forEach(l -> {
            backgroundGC.setLineDashes(l.getStyle());
            backgroundGC.setStroke(Color.web(l.getColor()));
            backgroundGC.setLineWidth(l.getWidth());
            backgroundGC.strokeLine(transformX(l.getX1()), transformY(l.getY1()),
                                    transformX(l.getX2()), transformY(l.getY2()));
        });
    }

    public void drawStamps (List<StampModel> list) {
        list.stream().forEach(s -> {
            Image image = getImageFromString(s.getImageString());
            drawRotatedImage(image, transformX(s.getPositionX()) - 50 / 2,
                             transformY(s.getPositionY()) - 50 / 2,
                             transformHeading(s.getHeading()));
        });
    }

    public void drawBackgroundRectangle (Color value) {
        backgroundGC.setFill(value);
        backgroundGC.fillRect(0, 0, backgroundCanvas.getWidth(), backgroundCanvas.getHeight());
    }

    private double transformX (double x) {
        return x + (double) UIConstants.CANVAS_SIZE / 2;
    }

    private double transformY (double y) {
        return -y + (double) UIConstants.CANVAS_SIZE / 2;
    }

    private double transformHeading (double heading) {
        return 90 - heading;
    }

    public Image getImageFromString (String image) {
        return new Image(getClass().getClassLoader().getResourceAsStream(image));
    }

    private void drawRotatedImage (Image image, double imageX, double imageY, double heading) {
        ImageView iv = new ImageView(image);
        iv.setRotate(heading);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image rotatedImage = iv.snapshot(params, null);
        backgroundGC.drawImage(rotatedImage, imageX, imageY, 50, 50);
    }
}
