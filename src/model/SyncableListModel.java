package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SyncableListModel<T> implements Iterable<T>{

	private List<T> list;
	
	public SyncableListModel() {
		list = new ArrayList<>();
	}

	public SyncableListModel(SyncableListModel<T> listModel) {
		list = new ArrayList<>(listModel.getList());
	}

	public void add(T object) {
		list.add(object);
	}
	
	public T get(int frameNumber) {
		return list.get(frameNumber);
	}
	
	public List<T> getList() {
		return list;
	}
	
	public int size() {
		return list.size();
	}
	
	public void remove(int frameNumber) {
		list.remove(frameNumber);
	}
	
	public void sync(int frameNumber) {
		if(list.size() != frameNumber) {
			list.add(list.get(list.size()-1));
		}
	}

	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}
}
