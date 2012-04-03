package dh.protege41.editor.owl.tab.database;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DatabaseDetailPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DatabaseDetailPanel() {
		setLayout(new BorderLayout());
		add(new JLabel("Database Type Here"), BorderLayout.NORTH);
		add(new JTextArea("Detail of this database"), BorderLayout.CENTER);
		
	}
	
	public void dispose() {
		
	}
}
