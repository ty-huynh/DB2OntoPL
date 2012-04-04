package dh.protege41.db2onto.tab;

import java.awt.BorderLayout;
import org.apache.log4j.Logger;

import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;

import dh.protege41.db2onto.tab.ui.DatabaseTabbed;

public class DatabaseViewComponent extends AbstractOWLViewComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(DatabaseViewComponent.class);
	
	private DatabaseTabbed databaseTabbedComponent;
	
	@Override
	protected void disposeOWLView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initialiseOWLView() throws Exception {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		databaseTabbedComponent = new DatabaseTabbed();
		add(databaseTabbedComponent, BorderLayout.CENTER);
		log.info("Database view component initialized!");
	}

}
