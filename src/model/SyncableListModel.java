// This entire file is part of my masterpiece.
// Anirudh Jonnavithula
// This class is just a concrete class example of the ISyncableListModel
// interface.
package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/***
 * This class is used in TurtleModel to keep track of all the attributes
 * that change from frame to frame. We keep track of the attributes to
 * help with animation
 * @author aj168
 *
 * @param <T> Type of objects in this list
 */
public class SyncableListModel<T> implements ISyncableListModel<T>{

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

	public Iterator<T> iterator() {
		return list.iterator();
	}
}
