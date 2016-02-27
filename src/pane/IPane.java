package pane;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public interface IPane {
	
	public Pane getPane();
	
	public void add(Node item);
	
	public void setPane(Pane p);


}