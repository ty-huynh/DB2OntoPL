package dh.protege41.db2onto.tab;

import java.awt.BorderLayout;

import org.apache.log4j.Logger;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;

import dh.protege41.db2onto.event.dbobject.DBObject;
import dh.protege41.db2onto.event.dboperation.DBOperationObject;
import dh.protege41.db2onto.tab.ui.DatabaseRecordsPanel;

public class DatabaseRecordsViewComponent extends DatabaseViewComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static Logger log = Logger.getLogger(DatabaseDetailViewComponent.class);
	
	private DatabaseRecordsPanel databaseRecordsComponent;
	
	@Override
	protected void disposeOWLView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initialiseOWLView() throws Exception {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		databaseRecordsComponent = new DatabaseRecordsPanel();
		add(databaseRecordsComponent, BorderLayout.CENTER);
		log.info("database records view component initialized!");
	}

	@Override
	protected DBObject updateView() {
		// TODO Auto-generated method stub
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
