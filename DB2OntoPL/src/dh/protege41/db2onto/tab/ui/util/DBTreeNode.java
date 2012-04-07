package dh.protege41.db2onto.tab.ui.util;

import javax.swing.tree.DefaultMutableTreeNode;

import dh.protege41.db2onto.event.dbobject.DBObject;

public class DBTreeNode extends DefaultMutableTreeNode {
	
	public DBTreeNode(DBObject dbObject) {
		super(dbObject);
	}
}
