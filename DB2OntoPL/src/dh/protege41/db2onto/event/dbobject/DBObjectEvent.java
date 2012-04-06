package dh.protege41.db2onto.event.dbobject;

import java.util.EventObject;

public class DBObjectEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DBObject _dbObject;
	
	public DBObjectEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
	public DBObjectEvent(Object source, DBObject dbObject) {
		super(source);
		_dbObject = dbObject;
	}
	
	public DBObject getSelectedDBObject() {
		return _dbObject;
	}
}
