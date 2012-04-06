package dh.protege41.db2onto.event.dboperation;

import java.util.EventObject;

public class DBOperationEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DBOperationObject _dbOperation;
	
	public DBOperationEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
	public DBOperationEvent(Object source, DBOperationObject dbOperation) {
		super(source);
		_dbOperation = dbOperation;
	}
	
	public DBOperationObject getSelectedOperationObject() {
		return _dbOperation;
	}
}
