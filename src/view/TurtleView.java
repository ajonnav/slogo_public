package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import addons.Features;
import constants.UIConstants;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.TurtleModel;


public class TurtleView implements IView {

    private ImageView image;
    private Canvas lineLayer;
    private Canvas backgroundLayer;
    private Group root;
    private GraphicsContext lineGC;
    private GraphicsContext backgroundGC;
    private List<ImageView> stamps;
    private ComboBox<String> backgroundChange;
    private ComboBox<String> penChange;
    private ComboBox<String> imageChange;

    public TurtleView (String image, Group root, Color c, TurtleModel turtleModel) {
        this.root = root;
        this.image = setUpImage(getImageFromString(image), 0, 0);
        this.stamps = new ArrayList<ImageView>();
        setUpLayers();
        setUpPickers();
        addToRoot();
    }

    public void setUpLayers () {
        Features features = new Features();
        this.lineLayer = features.makeCanvas(UIConstants.CANVAS_X, UIConstants.BORDER_WIDTH,
                                             UIConstants.CANVAS_SIZE, UIConstants.CANVAS_SIZE,
                                             Color.TRANSPARENT);
        this.lineGC = lineLayer.getGraphicsContext2D();
        this.backgroundLayer = features.makeCanvas(UIConstants.CANVAS_X, UIConstants.BORDER_WIDTH,
                                                   UIConstants.CANVAS_SIZE, UIConstants.CANVAS_SIZE,
                                                   Color.GREEN);
        this.backgroundGC = backgroundLayer.getGraphicsContext2D();
    }

    public void setUpPickers () {
        this.backgroundChange =
                makeColorPicker(UIConstants.BACKGROUND_PICK_X,
                                UIConstants.ZERO,
                                UIConstants.COLOR_SELECTOR_WIDTH, UIConstants.BORDER_WIDTH);
        this.penChange =
                makeColorPicker(UIConstants.PEN_PICK_X, UIConstants.ZERO,
                                UIConstants.COLOR_SELECTOR_WIDTH, UIConstants.BORDER_WIDTH);
        this.imageChange =
                makeColorPicker(UIConstants.IMAGE_SELECT_X, UIConstants.ZERO,
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
        root.getChildren().add(backgroundLayer);
        root.getChildren().add(lineLayer);
        root.getChildren().add(backgroundChange);
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
        updateOptions(backgroundChange, turtleModel.getColorMap());
        updateOptions(penChange, turtleModel.getColorMap());
        updateOptions(imageChange, turtleModel.getImageMap());
        String penColor = turtleModel.getPenColorIndex() + " " +
                          turtleModel.getColorMap().get(turtleModel.getPenColorIndex());
        String backgroundColor = turtleModel.getBackgroundColorIndex() + " " +
                                 turtleModel.getColorMap()
                                         .get(turtleModel.getBackgroundColorIndex());
        String imageString = turtleModel.getImageIndex() + " " +
                             turtleModel.getImageMap().get(turtleModel.getImageIndex());
        backgroundChange.setValue(penColor);
        penChange.setValue(backgroundColor);
        imageChange.setValue(imageString);
        lineGC.setStroke(Color.web(penColor.split(" ")[1]));
        lineGC.setLineWidth(turtleModel.getLineWidth());
        sceneChange(Color.web(backgroundColor.split(" ")[1]));
        image.setImage(getImageFromString(imageString.split(" ")[1]));
    }
    
    public void updateStamp(TurtleModel turtleModel) {
        if (turtleModel.shouldStamp()) {
            ImageView stamp = setUpImage(image.getImage(), turtleModel.getPositionY(), turtleModel.getPositionX());
            stamp.setRotate(transformHeading(turtleModel.getHeading()));
            stamps.add(stamp);
            root.getChildren().add(stamp);
            stamp.toFront();
            turtleModel.setShouldStamp(false);
        }
        if(turtleModel.shouldClearStamp()) {
            for(ImageView i : stamps) {
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


    public <T> ComboBox<String> makeColorPicker (double layoutX,
                                                 double layoutY,
                                                 double width,
                                                 double height) {
        ComboBox<String> cb = new ComboBox<String>();
        SingleSelectionModel<String> model = new SingleSelectionModel<String>() {
            @Override
            protected String getModelItem (int index) {
                return null;
            }

            @Override
            protected int getItemCount () {
                return 0;
            }
        };
        cb.setSelectionModel(model);
        cb.setLayoutX(layoutX);
        cb.setLayoutY(layoutY);
        cb.setMinWidth(width);
        cb.setMinHeight(height);
        return cb;
    }

    public void updateOptions (ComboBox<String> cb, Map<Double, String> map) {
        cb.getItems().clear();
        for (Double s : map.keySet()) {
            cb.getItems().add(s + " " + map.get(s));
        }
    }

    public void sceneChange (Color value) {
        backgroundGC.setFill(value);
        backgroundGC.fillRect(0, 0, backgroundLayer.getWidth(), backgroundLayer.getHeight());
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
