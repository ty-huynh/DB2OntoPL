package dh.protege41.db2onto.tab;

import org.apache.log4j.Logger;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;

import dh.database.api.DBOperationImplement;
import dh.protege41.db2onto.event.dbobject.DBObject;
import dh.protege41.db2onto.event.dbobject.DBObjectDatabase;
import dh.protege41.db2onto.event.dbobject.DBObjectEvent;
import dh.protege41.db2onto.event.dbobject.DBObjectEventListener;
import dh.protege41.db2onto.event.dboperation.DBOperationEvent;
import dh.protege41.db2onto.event.dboperation.DBOperationEventListener;
import dh.protege41.db2onto.event.dboperation.DBOperationObject;
/**
 * 
 * @author Administrator
 *	Description: everything are presented at all ViewCompnent (common of all ViewComponent) we will deal with them here
 */
public abstract class DatabaseViewComponent extends AbstractOWLViewComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(DatabaseViewComponent.class);
	
	private DBObjectEventListener _objectListener;
	private DBOperationEventListener _operationListener;
	private DBObject _lastDisplayedDBObject;
	private DBOperationObject _lastPerformedDBOperation;
	private String _header;
	
	@Override
	protected void disposeOWLView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initialiseOWLView() throws Exception {
		// TODO Auto-generated method stub
		_objectListener = new DBObjectEventListener() {
			
			@Override
			public void dbObjectChanged(DBObjectEvent event) {
				// TODO Auto-generated method stub
				DBObject dbObject = event.getSelectedDBObject();
				DatabaseViewComponent.this.updateContentAndHeader(dbObject);
			}
		};
		_operationListener = new DBOperationEventListener() {
			
			@Override
			public void dbOperationPerformed(DBOperationEvent event) {
				// TODO Auto-generated method stub
				DatabaseViewComponent.this._lastPerformedDBOperation = event.getSelectedOperationObject();
				DatabaseViewComponent.this.performOperation();
			}
		};
		
		DB2OntoPLWorkspaceTab.getDBObjectEventManager().addDBEventListener(_objectListener);
		DB2OntoPLWorkspaceTab.getDBOperationEventManager().addDBOperationListener(_operationListener);
	}

	protected void updateContentAndHeader(DBObject dbObject) {
		if(dbObject != null && !dbObject.equals(_lastDisplayedDBObject)){
			_lastDisplayedDBObject = dbObject;
			updateView();
			updateHeader();
		}
	}
	
	protected abstract void updateHeader();
	
	protected abstract DBObject updateView();
	
	protected abstract DBOperationObject performOperation();
	
	protected synchronized DBOperationObject getLastPerformedDBOperation() {
		return _lastPerformedDBOperation;
	}
	protected synchronized DBOperationImplement getDBOperationImpl() {
		return DB2OntoPLWorkspaceTab.getDBOperationImplement();
	}
	protected synchronized void setDBOperationImpl(DBOperationImplement operationImpl) {
		DB2OntoPLWorkspaceTab.setDBOperationImplement(operationImpl);
	}
	
	protected synchronized DBObjectDatabase getDatabaseInfos() {
		return DB2OntoPLWorkspaceTab.getDatabaseInfos();
	}
	protected synchronized DBObject getLastDisplayedDBObject() {
		return _lastDisplayedDBObject;
	}
	protected synchronized static void setGlobalDBOperationObject(DBOperationObject dbOperation) {
		DB2OntoPLWorkspaceTab.getDBOperationEventManager().selectOperation(dbOperation);
	}
	protected synchronized static void setGlobalSelectionObject(DBObject dbObject) {
		DB2OntoPLWorkspaceTab.getDBObjectEventManager().changeDBObject(dbObject);
	}

}
