package dh.protege41.editor.owl.tab.database;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class DatabaseConfigurePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DatabaseConfigurePanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER));
		add(new JButton("This is database configure form"));
	}
}
