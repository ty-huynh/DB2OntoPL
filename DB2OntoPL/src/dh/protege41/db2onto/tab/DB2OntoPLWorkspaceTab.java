package dh.protege41.db2onto.tab;

import org.apache.log4j.Logger;
import org.protege.editor.owl.ui.OWLWorkspaceViewsTab;

import com.sun.xml.internal.ws.message.saaj.SAAJHeader;

import dh.database.api.DBOperationImplement;
import dh.database.api.exception.DHConnectionException;
import dh.protege41.db2onto.event.dbobject.DBObjectEventManager;
import dh.protege41.db2onto.event.dboperation.DBOperationEventManager;
import dh.protege41.db2onto.event.dboperation.DBOperationEventType;
import dh.protege41.db2onto.event.dboperation.DBOperationObject;

public class DB2OntoPLWorkspaceTab extends OWLWorkspaceViewsTab{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(DB2OntoPLWorkspaceTab.class);
	
	//Create an instance of LISTENER in here
	// - each ViewComponent will get workSpaceTab (have only one object) 
	// - fire events to this listener 
	// - a handler will take fired event and dispatch to other ViewComponent
	// - ?? how to get ViewComponent from WorkSpaceTab
	// - event is DBSelectionChanged
	// - listener is DBSelectionListener
	// - in each ViewComponent declare a method that handle DBSelectionChanged event
	private static DBObjectEventManager _dbObjectEventManager = new DBObjectEventManager();
	private static DBOperationEventManager _dbOperationEventManager = new DBOperationEventManager();
	private static DBOperationImplement _dbOperation = null;
	private static boolean _connected = false;
	
	public static boolean isConnected() {
		return _connected;
	}
	public static void setConnectStatus(boolean status) {
		_connected = status;
	}
	public static void checkConnect() {
		if(_connected) {
			try {
				_dbOperation.createConnection();
				log.info("connection is ok");
			} catch (DHConnectionException e) {
				_connected = false;
				log.info("connection was lost");
				_dbOperationEventManager.selectOperation(new DBOperationObject(DBOperationEventType.DB_OPERATION_DISCONNECT));
			}
		}
	}
	public static DBOperationImplement getDBOperationImplement() {
		return _dbOperation;
	}
	
	public static void setDBOperationImplement(DBOperationImplement paramDBOperation) {
		_dbOperation = paramDBOperation;
	}
	
	public static DBObjectEventManager getDBObjectEventManager() {
		return _dbObjectEventManager;
	}
	
	public static DBOperationEventManager getDBOperationEventManager() {
		return _dbOperationEventManager;
	}
	
}
