package view;

import java.util.Observable;
import addons.Features;
import constants.UIConstants;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.DisplayModel;


public class DisplayView implements IView {

    private Canvas backgroundLayer;
    private GraphicsContext backgroundGC;
    private ComboBox<HBox> backgroundChange;
    private Features features;

    public DisplayView (Group root) {
        this.features = new Features();
        this.backgroundLayer = features.makeCanvas(UIConstants.CANVAS_X, UIConstants.BORDER_WIDTH,
                                                   UIConstants.CANVAS_SIZE, UIConstants.CANVAS_SIZE,
                                                   Color.GREEN);
        this.backgroundGC = backgroundLayer.getGraphicsContext2D();
        this.backgroundChange =
                features.makeColorPicker(1100,
                                         550,
                                         UIConstants.COLOR_SELECTOR_WIDTH,
                                         UIConstants.BORDER_WIDTH);
        root.getChildren().add(backgroundLayer);
        root.getChildren().add(backgroundChange);
    }

    @Override
    public void update (Observable o, Object arg) {
        if (o instanceof DisplayModel) {
            DisplayModel displayModel = (DisplayModel) o;
            features.updateComboBoxOptions(backgroundChange, displayModel.getColorMap());
            String backgroundColor = displayModel.getBackgroundColorIndex() + " " +
                                     displayModel.getColorMap()
                                             .get(displayModel.getBackgroundColorIndex());
            HBox myHB = new HBox();
            myHB.getChildren().add(new Text("Background Choices"));
            backgroundChange.setValue(myHB);
            sceneChange(Color.web(backgroundColor.split(" ")[1]));
        }
    }

    public void sceneChange (Color value) {
        backgroundGC.setFill(value);
        backgroundGC.fillRect(0, 0, backgroundLayer.getWidth(), backgroundLayer.getHeight());
    }

}
