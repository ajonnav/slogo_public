package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import addons.Features;
import constants.UIConstants;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.DisplayModel;
import model.LineModel;
import model.PenModel;
import model.StampModel;
import model.TurtleModel;


public class DisplayView implements IView {

    private Canvas backgroundCanvas;
    private GraphicsContext backgroundGC;
    private ComboBox<HBox> backgroundColorComboBox;
    private Features features;
    private List<Animation> animations;
    private Group imageViewGroup;
    private int lastExpressionFrameNumber;
    private List<ImageView> turtleViews;
    private List<Group> lineViewGroups;
    private List<Group> stampViewGroups;
    private List<Rectangle> actives;
    private Group root;

    public DisplayView (Group root) {
        this.features = new Features();
        this.backgroundCanvas = features.makeCanvas(UIConstants.CANVAS_X, UIConstants.BORDER_WIDTH,
                                                    UIConstants.CANVAS_SIZE,
                                                    UIConstants.CANVAS_SIZE,
                                                    Color.GREEN);
        this.backgroundGC = backgroundCanvas.getGraphicsContext2D();
        actives = new ArrayList<>();
        animations = new ArrayList<>();
        turtleViews = new ArrayList<>();
        lineViewGroups = new ArrayList<>();
        stampViewGroups = new ArrayList<>();
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
        this.root = root;
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
                // backgroundColorComboBox.setValue(backgroundColorString);
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
                lastExpressionFrameNumber = displayModel.getTurtleList().get(0).getFrameNumber();
            }

        }
    }

    public void drawTurtles (DisplayModel displayModel) {
        for (int i = lastExpressionFrameNumber; i < displayModel.getTurtleList().get(0)
                .getFrameNumber(); i++) {
            for (int j = turtleViews.size(); j < displayModel.getTurtleList().size(); j++) {
                ImageView image = initImageView(displayModel.getTurtleList().get(j), i);
                Rectangle area = new Rectangle(0, 0, 50, 50);
                area.setFill(Color.TRANSPARENT);
                area.setStroke(Color.TRANSPARENT);
                actives.add(area);
                root.getChildren().add(area);
                imageViewGroup.getChildren().add(image);
                turtleViews.add(image);
                lineViewGroups.add(new Group());
                root.getChildren().add(lineViewGroups.get(j));
                stampViewGroups.add(new Group());
                root.getChildren().add(stampViewGroups.get(j));
            }
            for(int j = 0; j < displayModel.getTurtleList().size(); j++) {
                turtleViews.get(j).setImage(getImageFromString(displayModel.getTurtleList().get(j).getImageString(i)));
            }
            for (int j = 0; j < displayModel.getTurtleList().size(); j++) {
                animate(turtleViews.get(j), displayModel.getTurtleList().get(j), i - 1, i, j);
            }
        }
    }

    private void animate (ImageView image, TurtleModel turtle, int prev, int next, int turtleID) {
        if(turtle.getShowStatus(next) == 1) {
            image.setOpacity(1);
        }
        else {
            image.setOpacity(0);
        }
        double translationTime =
                turtle.getPositionX(prev) != turtle.getPositionX(next) ||
                                 turtle.getPositionY(prev) != turtle.getPositionY(next) ? 100 : 1;
        double rotationTime = turtle.getHeading(prev) != turtle.getHeading(next) ? 100 : 1;
        TranslateTransition tt = new TranslateTransition(Duration.millis(translationTime), image);
        RotateTransition rt = new RotateTransition(Duration.millis(rotationTime), image);
        tt.setFromX(getDrawableX(turtle.getPositionX(prev)));
        tt.setFromY(getDrawableY(turtle.getPositionY(prev)));
        tt.setToX(getDrawableX(turtle.getPositionX(next)));
        tt.setToY(getDrawableY(turtle.getPositionY(next)));
        tt.setCycleCount(1);
        rt.setFromAngle(transformHeading(turtle.getHeading(prev)));
        rt.setToAngle(transformHeading(turtle.getHeading(next)));
        rt.setCycleCount(1);
        FadeTransition fs = new FadeTransition(Duration.millis(translationTime), image);
        if(turtle.getShowStatus(next) == 1) {
            fs.setFromValue(1.0);
            fs.setToValue(1.0);
            fs.setCycleCount(1);
        }
        else {
            fs.setFromValue(0.0);
            fs.setToValue(0.0);
            fs.setCycleCount(1);
        }
        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().add(fs);
        if(turtle.isActive()) {
            Rectangle area = actives.get(turtleID);
            area.setStroke(Color.BLACK);
            TranslateTransition at = new TranslateTransition(Duration.millis(translationTime), area);     
            at.setFromX(getDrawableX(turtle.getPositionX(prev)));
            at.setFromY(getDrawableY(turtle.getPositionY(prev)));
            at.setToX(getDrawableX(turtle.getPositionX(next)));
            at.setToY(getDrawableY(turtle.getPositionY(next)));
            pt.getChildren().add(at);
        }
        else {
            actives.get(turtleID).setStroke(Color.TRANSPARENT);
        }
        pt.getChildren().add(tt);
        if (turtle.getLineList(prev).size() + 1 == turtle.getLineList(next).size()) {
            pt.getChildren()
                    .add(drawLine(turtle.getPen(), turtle.getLineList(next).get(turtle.getLineList(next).size() - 1),
                                  translationTime, turtleID));
        }
        if (turtle.getLineList().size() == 0) {
            lineViewGroups.get(turtleID).getChildren().clear();
        }
        if (turtle.getStampList(prev).size() + 1 == turtle.getStampList(next).size()) {
            pt.getChildren().add(drawStamp(
                                           turtle.getStampList(next)
                                                   .get(turtle.getStampList(next).size() - 1),
                                           turtleID));
        }
        if (turtle.getStampList().size() == 0) {
            stampViewGroups.get(turtleID).getChildren().clear();
        }
        animations.add(pt);
        animations.add(rt);
    }

    public FadeTransition drawStamp (StampModel stamp, int turtleID) {
        ImageView image = new ImageView();
        System.out.println(stamp.getImageString());
        image.setImage(getImageFromString(stamp.getImageString()));
        image.setX(getDrawableX(stamp.getPositionX()));
        image.setY(getDrawableY(stamp.getPositionY()));
        image.setFitWidth(50);
        image.setFitHeight(50);
        image.setRotate(transformHeading(stamp.getHeading()));
        image.setOpacity(0);
        stampViewGroups.get(turtleID).getChildren().add(image);
        FadeTransition ft = new FadeTransition(Duration.millis(100), image);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        return ft;
    }

    public SequentialTransition drawLine (PenModel pen, LineModel line, double translationTime, int turtleID) {
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
            l.setTranslateX(50/2);
            l.setTranslateY(50/2);
            l.setStroke(Color.web(pen.getColorString()));
            l.setStrokeWidth(pen.getSize());
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

    public ImageView initImageView (TurtleModel t, int currFrame) {
        ImageView image = new ImageView();
        image.setImage(getImageFromString(t.getImageString(currFrame)));
        image.setFitHeight(50);
        image.setFitWidth(50);
        image.setOpacity(t.getShowStatus(currFrame));
        image.setX(0);
        image.setY(0);
        image.setRotate(transformHeading(t.getHeading(currFrame)));
        return image;
    }

    public double getDrawableX (double x) {
        return transformX(x) + UIConstants.INITIAL_X;
    }

    public double getDrawableY (double y) {
        return transformY(y) + UIConstants.INITIAL_Y;
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
