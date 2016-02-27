package pane;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MasterPane {

	private Pane myPane;
	private VBox myBox;
	
	public MasterPane(int x, int y) {
		setMyPane(new Pane());
		myBox = new VBox();
		getMyPane().setLayoutX(x);
		getMyPane().setLayoutY(y);
		add(myBox);
	}

	public void add(Node item) {
		getMyPane().getChildren().add(item);
	}

	public Pane getMyPane() {
		return myPane;
	}

	public void setMyPane(Pane myPane) {
		this.myPane = myPane;
	}
	
}
