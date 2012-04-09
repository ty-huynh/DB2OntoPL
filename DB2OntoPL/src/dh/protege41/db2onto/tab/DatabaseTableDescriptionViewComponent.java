package dh.protege41.db2onto.tab;

import org.apache.log4j.Logger;

import dh.protege41.db2onto.event.dbobject.DBObject;
import dh.protege41.db2onto.event.dbobject.DBObjectDatabase;
import dh.protege41.db2onto.event.dbobject.DBObjectTable;
import dh.protege41.db2onto.event.dboperation.DBOperationObject;

public class DatabaseTableDescriptionViewComponent extends DatabaseViewComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(DatabaseTableDescriptionViewComponent.class);
	@Override
	protected void disposeOWLView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initialiseOWLView() throws Exception {
		super.initialiseOWLView();
		log.info("database table description view component initialized!");
	}
	
	@Override
	protected DBObject updateView() {
		// TODO Auto-generated method stub
		DBObject dbObject = getLastDisplayedDBObject();
		if(dbObject instanceof DBObjectTable) {
			log.info("table object was selected : " + ((DBObjectTable)dbObject).getName());
		}
		return null;
	}
	@Override
	protected void updateHeader() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected DBOperationObject performOperation() {
		// TODO Auto-generated method stub
		return null;
	}

}
