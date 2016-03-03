package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import addons.Features;
import constants.UIConstants;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.TurtleModel;


public class TurtleView implements IView {

    private ImageView image;
    private Canvas lineLayer;
    private Group root;
    private GraphicsContext lineGC;
    private List<ImageView> stamps;
    private ComboBox<String> penChange;
    private ComboBox<String> imageChange;
    private Features features;

    public TurtleView (String image, Group root, Color c) {
        this.root = root;
        this.features = new Features();
        this.image = setUpImage(getImageFromString(image), 0, 0);
        this.stamps = new ArrayList<ImageView>();
        setUpLayers();
        setUpPickers();
        addToRoot();
    }

    public void setUpLayers () {
        this.lineLayer = features.makeCanvas(UIConstants.CANVAS_X, UIConstants.BORDER_WIDTH,
                                             UIConstants.CANVAS_SIZE, UIConstants.CANVAS_SIZE,
                                             Color.TRANSPARENT);
        this.lineGC = lineLayer.getGraphicsContext2D();
    }

    public void setUpPickers () {
        this.penChange =
                features.makeColorPicker(UIConstants.PEN_PICK_X, UIConstants.ZERO,
                                UIConstants.COLOR_SELECTOR_WIDTH, UIConstants.BORDER_WIDTH);
        this.imageChange =
                features.makeColorPicker(UIConstants.IMAGE_SELECT_X, UIConstants.ZERO,
                                UIConstants.IMAGE_SELECT_WIDTH, UIConstants.BORDER_WIDTH);
    }

    public ImageView setUpImage (Image image, double posX, double posY) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setX(transformX(posY) + UIConstants.INITIAL_X);
        imageView.setY(transformY(posX) + UIConstants.INITIAL_Y);
        return imageView;
    }

    public void addToRoot () {
        root.getChildren().add(lineLayer);
        root.getChildren().add(penChange);
        root.getChildren().add(imageChange);
        root.getChildren().add(this.image);
    }

    @Override
    public void update (Observable o, Object arg) {
        if (o instanceof TurtleModel) {
            TurtleModel turtleModel = (TurtleModel) o;
            updateStamp(turtleModel);
            updateStyles(turtleModel);
            updateLine(turtleModel);
            updateImage(turtleModel);
        }
    }

    public void updateStyles (TurtleModel turtleModel) {
        features.updateComboBoxOptions(penChange, turtleModel.getColorMap());
        features.updateComboBoxOptions(imageChange, turtleModel.getImageMap());
        String penColor = turtleModel.getPenColorIndex() + " " +
                          turtleModel.getColorMap().get(turtleModel.getPenColorIndex());
        String imageString = turtleModel.getImageIndex() + " " +
                             turtleModel.getImageMap().get(turtleModel.getImageIndex());
        penChange.setValue(penColor);
        imageChange.setValue(imageString);
        lineGC.setStroke(Color.web(penColor.split(" ")[1]));
        lineGC.setLineWidth(turtleModel.getLineWidth());
        image.setImage(getImageFromString(imageString.split(" ")[1]));
    }

    public void updateStamp (TurtleModel turtleModel) {
        if (turtleModel.shouldStamp()) {
            ImageView stamp =
                    setUpImage(image.getImage(), turtleModel.getPositionY(),
                               turtleModel.getPositionX());
            stamp.setRotate(transformHeading(turtleModel.getHeading()));
            stamps.add(stamp);
            root.getChildren().add(stamp);
            stamp.toFront();
            turtleModel.setShouldStamp(false);
        }
        if (turtleModel.shouldClearStamp()) {
            for (ImageView i : stamps) {
                i.setImage(null);
            }
            stamps.clear();
            turtleModel.setShouldClearStamp(false);
        }
    }

    public void updateLine (TurtleModel turtleModel) {
        if (turtleModel.shouldClear()) {
            lineGC.clearRect(0, 0, UIConstants.CANVAS_SIZE, UIConstants.CANVAS_SIZE);
            turtleModel.setShouldClear(false);
        }
        if (turtleModel.getPenStatus()) {
            lineGC.strokeLine(image.getX() - UIConstants.INITIAL_X,
                              image.getY() - UIConstants.INITIAL_Y,
                              transformX(turtleModel.getPositionX()),
                              transformY(turtleModel.getPositionY()));
        }
    }

    public void updateImage (TurtleModel turtleModel) {
        image.setOpacity(Boolean.compare(turtleModel.getShowStatus(), false));
        image.setX(transformX(turtleModel.getPositionX()) + UIConstants.INITIAL_X);
        image.setY(transformY(turtleModel.getPositionY()) + UIConstants.INITIAL_Y);
        image.setRotate(transformHeading(turtleModel.getHeading()));
    }
    
    public String getX () {
        return Double.toString(image.getX());
    }

    public String getY () {
        return Double.toString(image.getY());
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

    public void setImage (String image) {
        this.image.setImage(getImageFromString(image));
    }

    public Image getImageFromString (String image) {
        return new Image(getClass().getClassLoader().getResourceAsStream(image));
    }

}
