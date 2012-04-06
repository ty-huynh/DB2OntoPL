package dh.protege41.db2onto.tab;

import org.apache.log4j.Logger;
import org.protege.editor.owl.ui.OWLWorkspaceViewsTab;

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
	
	public static void hello() {
		log.info("Hello");
	}
}
