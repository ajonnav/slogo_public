// This entire file is part of my masterpiece.
// Anirudh Jonnavithula
// I think this code is good because it uses a generic instead of a specific type.
// It is also an interface which means that the implementation details don't affect
// how it is used. Also, the interface extends the Iterable interface which makes
// the new interface iterable as well.
package model;

import java.util.List;

public interface ISyncableListModel<T> extends Iterable<T> {
	
	void add(T object);
	
	T get(int frameNumber);
	
	List<T> getList();
	
	int size();
	
	void remove(int frameNumber);
	
	void sync(int frameNumber);
	
}
