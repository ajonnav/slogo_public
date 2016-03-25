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
