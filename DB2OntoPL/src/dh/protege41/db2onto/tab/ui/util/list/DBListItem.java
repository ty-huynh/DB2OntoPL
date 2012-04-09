package dh.protege41.db2onto.tab.ui.util.list;

import org.protege.editor.core.ui.list.MListItem;

public class DBListItem implements MListItem {

	Object object;
	
	public DBListItem(Object o) {
		object = o;
	}
	public String toString() {
		return "LI : " + object.toString(); 
	}
	
	public Object getObject() {
		return object;
	}
	@Override
	public String getTooltip() {
		// TODO Auto-generated method stub
		return "tooltip: " + object.toString();
	}

	@Override
	public boolean handleDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handleEdit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDeleteable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return true;
	}

}
