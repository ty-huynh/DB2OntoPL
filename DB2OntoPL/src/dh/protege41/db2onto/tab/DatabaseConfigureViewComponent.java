package dh.protege41.db2onto.tab;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;

import dh.protege41.db2onto.event.dbobject.DBObjectEvent;
import dh.protege41.db2onto.event.dbobject.DBObjectEventListener;
import dh.protege41.db2onto.event.dbobject.DBObject;
import dh.protege41.db2onto.tab.ui.DatabaseConfigurePanel;

public class DatabaseConfigureViewComponent extends DatabaseViewComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static Logger log = Logger.getLogger(DatabaseConfigureViewComponent.class);
	
	private DatabaseConfigurePanel databaseConfigureComponent;
	
	@Override
	protected void disposeOWLView() {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void initialiseOWLView() throws Exception {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		databaseConfigureComponent = new DatabaseConfigurePanel();
		add(databaseConfigureComponent, BorderLayout.CENTER);
		log.info("database configure view component initialized!");
		DB2OntoPLWorkspaceTab.getEventManager().addDBEventListener(new DBObjectEventListener() {
			
			@Override
			public void dbObjectChanged(DBObjectEvent event) {
				// TODO Auto-generated method stub
				log.info("DatabaseConfigureViewComponent : " + event.getSelectedDBObject().toString());
			}
		});
		
		databaseConfigureComponent.getJButtonChange().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				log.info("Change button is pressed");
				DB2OntoPLWorkspaceTab.getEventManager().changeDBObject(new DBObject());
			}
		});
	}
}
