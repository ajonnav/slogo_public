package pane;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

interface IPane {
	
	Pane getPane();
	
	void add(Node item);
	
	void setPane(Pane p);


}