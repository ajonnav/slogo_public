package model;

import java.util.List;

public interface ViewableHistoryModel {
    
    List<String> getImmutableHistoryList ();
    
    void updateView ();
}
