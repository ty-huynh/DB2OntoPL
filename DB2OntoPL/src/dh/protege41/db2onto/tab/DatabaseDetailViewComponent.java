package dh.protege41.db2onto.tab;

import java.awt.BorderLayout;

import org.apache.log4j.Logger;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;

import dh.protege41.db2onto.event.dbobject.DBObjectEvent;
import dh.protege41.db2onto.event.dbobject.DBObjectEventListener;
import dh.protege41.db2onto.tab.ui.DatabaseDetailPanel;

public class DatabaseDetailViewComponent extends DatabaseViewComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static Logger log = Logger.getLogger(DatabaseDetailViewComponent.class);
	
	private DatabaseDetailPanel databaseDetailComponent;
	
	@Override
	protected void disposeOWLView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initialiseOWLView() throws Exception {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		databaseDetailComponent = new DatabaseDetailPanel();
		add(databaseDetailComponent, BorderLayout.CENTER);
		log.info("database detail view component initialized!");
		DB2OntoPLWorkspaceTab.getEventManager().addDBEventListener(new DBObjectEventListener() {
			
			@Override
			public void dbObjectChanged(DBObjectEvent event) {
				// TODO Auto-generated method stub
				log.info("DatabaseDetailViewComponent : " + event.getSelectedDBObject().toString());
			}
		});
	}

}
