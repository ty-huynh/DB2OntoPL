package dh.protege41.db2onto.tab.ui.util.list;

import org.protege.editor.core.ui.list.MListSectionHeader;

public class DBListHeader implements MListSectionHeader {

	Object object;
	
	public DBListHeader(Object object) {
		this.object = object;
	}
	@Override
	public boolean canAdd() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return object.toString();
	}

}
