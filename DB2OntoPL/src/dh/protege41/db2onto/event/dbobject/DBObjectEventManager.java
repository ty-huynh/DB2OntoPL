package dh.protege41.db2onto.event.dbobject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DBObjectEventManager {
	
	private DBObject _dbObject;
	private List<DBObjectEventListener> _listeners = new ArrayList<DBObjectEventListener>();
	
	public synchronized void changeDBObject(DBObject dbObject) {
		_dbObject = dbObject;
		_fireDBEvent();
	}
	
	public synchronized void addDBEventListener(DBObjectEventListener paramDBEventListener) {
		_listeners.add(paramDBEventListener);
	}
	
	public synchronized void removeDBEventListener(DBObjectEventListener paramDBEventListener) {
		_listeners.remove(paramDBEventListener);
	}
	
	private synchronized void _fireDBEvent() {
		DBObjectEvent dbEvent = new DBObjectEvent(this, _dbObject);
		Iterator<DBObjectEventListener> iterator = _listeners.iterator();
		while(iterator.hasNext()) {
			iterator.next().dbObjectChanged(dbEvent);
		}
	}
	
}
