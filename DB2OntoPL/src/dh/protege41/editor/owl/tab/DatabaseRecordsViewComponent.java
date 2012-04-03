package dh.protege41.editor.owl.tab;

import java.awt.BorderLayout;

import org.apache.log4j.Logger;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;

import dh.protege41.editor.owl.tab.database.DatabaseRecordsPanel;

public class DatabaseRecordsViewComponent extends AbstractOWLViewComponent{
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
}
