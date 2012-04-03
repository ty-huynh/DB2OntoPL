package dh.protege41.editor.owl.tab.database;

import javax.swing.JTabbedPane;



public class DatabaseTabbed extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DatabaseTabbed() {
		add(new DatabaseDetailPanel());
		add(new DatabaseConfigurePanel());
	}
}
