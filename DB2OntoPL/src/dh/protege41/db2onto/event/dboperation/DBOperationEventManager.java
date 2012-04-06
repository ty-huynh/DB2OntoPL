package dh.protege41.db2onto.event.dboperation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DBOperationEventManager {
	
	private DBOperationObject _dbOperation;
	private List<DBOperationEventListener> _listeners = new ArrayList<DBOperationEventListener>();
	
	public synchronized void selectOperation(DBOperationObject dbOperation) {
		_dbOperation = dbOperation;
		_fireOperationEvent();
	}
	
	public synchronized void addDBOperationListener(DBOperationEventListener listener) {
		_listeners.add(listener);
	}
	
	public synchronized void removeDBOperationListener(DBOperationEventListener listener) {
		_listeners.remove(listener);
	}
	
	private synchronized void _fireOperationEvent() {
		DBOperationEvent dbOperEvent = new DBOperationEvent(this, _dbOperation);
		Iterator<DBOperationEventListener> iterator = _listeners.iterator();
		while(iterator.hasNext()) {
			iterator.next().dbOperationPerformed(dbOperEvent);
		}
	}
}
