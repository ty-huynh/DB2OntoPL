package dh.protege41.db2onto.tab;

import java.awt.BorderLayout;

import org.apache.log4j.Logger;

import dh.protege41.db2onto.event.dbobject.DBObject;
import dh.protege41.db2onto.event.dbobject.DBObjectDatabase;
import dh.protege41.db2onto.event.dboperation.DBOperationObject;
import dh.protege41.db2onto.tab.DatabaseRecordsViewComponent.DatabaseRecordsPanel;

public class DatabaseMappingViewComponent extends DatabaseViewComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3091175062620357322L;
	
	private static final Logger log = Logger.getLogger(DatabaseMappingViewComponent.class);
	private DBObjectDatabase databaseInfos;
	@Override
	protected void disposeOWLView() {
		// TODO Auto-generated method stub
		//remove all listeners here
	}

	@Override
	protected void initialiseOWLView() throws Exception {
		super.initialiseOWLView();
		setLayout(new BorderLayout());
		databaseInfos = getDatabaseInfos();
		log.info("database mapping view component initialized!");
	}

	@Override
	protected void updateHeader() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected DBObject updateView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected DBOperationObject performOperation() {
		// TODO Auto-generated method stub
		return null;
	}

}
