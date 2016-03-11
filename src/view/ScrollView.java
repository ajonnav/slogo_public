package view;

import java.util.Observable;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

public abstract class ScrollView extends View{

	public ScrollPane myPane;
	
	public ScrollView() {
		setMyPane(new ScrollPane(getMyBox()));
		getMyRoot().getChildren().add(getMyPane());
		myPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		myPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
	}

	@Override
	public void update(Observable o, Object arg) {
		
	}

	public void setMyPane(ScrollPane sp){
		myPane = sp;
	}
	
	public ScrollPane getMyPane(){
		return myPane;
	}
}
