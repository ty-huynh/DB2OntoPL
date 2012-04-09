package dh.protege41.db2onto.tab.ui.util.list;

import javax.swing.DefaultListModel;

public class DBMutableListModel extends DefaultListModel{
	public DBMutableListModel() {
		
	}
	
	public void setElementAt(Object object, int index) {
		super.setElementAt(object, index);
	}
	
	public void addElement(Object object) {
		super.addElement(object);
	}
	
	public void add(int index, Object object) {
		super.add(index, object);
	}
	
	public Object set(int index, Object object) {
		return super.set(index, object);
	}
	
	public int getIndex(Object object) {
		return super.indexOf(object);
	}
}
