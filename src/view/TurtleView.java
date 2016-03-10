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
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.TurtleModel;


public class TurtleView implements IView {

    private ImageView image;
    private String initImage;
    private Canvas lineLayer;
    private Group root;
    private GraphicsContext lineGC;
    private List<ImageView> stamps;
    private ComboBox<HBox> penChange;
    private ComboBox<HBox> imageChange;
    private Features features;

    public TurtleView (String image, Group root) {
        this.root = root;
        this.features = new Features();
        this.initImage = image;
        this.image = setUpImage(getImageFromString(image), 0, 0);
        this.stamps = new ArrayList<ImageView>();
        setUpLayers();
        setUpPickers();
        addToRoot();
    }
    
    public TurtleView makeNewTurtleView() {
        return new TurtleView(initImage, root);
    }

    public void setUpLayers () {
        this.lineLayer = features.makeCanvas(UIConstants.CANVAS_X, UIConstants.BORDER_WIDTH,
                                             UIConstants.CANVAS_SIZE, UIConstants.CANVAS_SIZE,
                                             Color.TRANSPARENT);
        this.lineGC = lineLayer.getGraphicsContext2D();
    }

    public void setUpPickers () {
        this.penChange =
                features.makeColorPicker(1100, 600,
                                UIConstants.COLOR_SELECTOR_WIDTH, UIConstants.BORDER_WIDTH);
        this.imageChange =
                features.makeColorPicker(1100, 650,
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
        features.updateComboBoxOptionsImage(imageChange, turtleModel.getImageMap());
        String penColor = turtleModel.getPenColorIndex() + " " +
                          turtleModel.getColorMap().get(turtleModel.getPenColorIndex());
        String imageString = turtleModel.getImageIndex() + " " +
                             turtleModel.getImageMap().get(turtleModel.getImageIndex());
        HBox penHB = new HBox();
        penHB.getChildren().add(new Text("Pen Choices"));
        penChange.setValue(penHB);
        
        HBox imageHB = new HBox();
        imageHB.getChildren().add(new Text("Image Choices"));
        imageChange.setValue(imageHB);
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
            turtleModel.setShouldStamp(0);
        }
        if (turtleModel.shouldClearStamp()) {
            for (ImageView i : stamps) {
                i.setImage(null);
            }
            stamps.clear();
            turtleModel.setShouldClearStamp(0);
        }
    }

    public void updateLine (TurtleModel turtleModel) {
        if (turtleModel.shouldClear()) {
            lineGC.clearRect(0, 0, UIConstants.CANVAS_SIZE, UIConstants.CANVAS_SIZE);
            turtleModel.setShouldClear(0);
        }
        if (turtleModel.getPenStatus() == 1) {
            lineGC.strokeLine(image.getX() - UIConstants.INITIAL_X,
                              image.getY() - UIConstants.INITIAL_Y,
                              transformX(turtleModel.getPositionX()),
                              transformY(turtleModel.getPositionY()));
        }
    }

    public void updateImage (TurtleModel turtleModel) {
        image.setOpacity(turtleModel.getShowStatus());
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
