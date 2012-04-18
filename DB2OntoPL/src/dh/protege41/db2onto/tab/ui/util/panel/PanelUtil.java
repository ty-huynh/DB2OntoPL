package dh.protege41.db2onto.tab.ui.util.panel;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PanelUtil {
	public static Component createScroll(Component com) {
		return new JScrollPane(com);
	}
	/**
	 * add a component to NORTH of new JPanel
	 * @param com
	 * @return
	 */
	public static Component createJPanelBorderLayout(Component com, String position) {
		JPanel pn = new JPanel(new BorderLayout());
		pn.add(com, position);
		return pn;
	}
}
