package dh.protege41.db2onto.tab;

import org.apache.log4j.Logger;

import dh.protege41.db2onto.event.dbobject.DBObject;
import dh.protege41.db2onto.event.dbobject.DBObjectColumn;
import dh.protege41.db2onto.event.dbobject.DBObjectDatabase;
import dh.protege41.db2onto.event.dboperation.DBOperationObject;

public class DatabaseColumnDescriptionViewComponent extends DatabaseViewComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(DatabaseColumnDescriptionViewComponent.class);
	@Override
	protected void disposeOWLView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initialiseOWLView() throws Exception {
		super.initialiseOWLView();
		log.info("database column description view component initialized!");
	}
	
	@Override
	protected DBObject updateView() {
		// TODO Auto-generated method stub
		DBObject dbObject = getLastDisplayedDBObject();
		if(dbObject instanceof DBObjectColumn) {
			log.info("column object was selected : " + ((DBObjectColumn)dbObject).getName());
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
