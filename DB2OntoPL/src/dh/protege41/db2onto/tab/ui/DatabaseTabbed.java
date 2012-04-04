package dh.protege41.db2onto.tab.ui;

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
