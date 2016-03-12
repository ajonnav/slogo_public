package model;

import java.util.List;
import java.util.Map;
import command.Command;

public interface ViewableCommandsModel {
    
    Map<String, List<Command>> getImmutableCommandsMap ();
    
    Map<String, List<Command>> getImmutableVariablesMap ();
    
    void updateView ();
}
