package dh.protege41.db2onto.tab.ui.util.list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;

import dh.protege41.db2onto.tab.ui.util.DBIcons;

public class DBListCellRenderer extends JPanel implements ListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7910399456117146920L;
	private JLabel lbIcon;
	private static final Color LABEL_COLOR = Color.BLUE.darker();
	private JTextArea contentArea;
	private JLabel lbHeader;
	
	public DBListCellRenderer() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(1, 2, 1, 1));
		
		contentArea = new JTextArea();
		contentArea.setFont(new Font("Dialog", Font.BOLD, 12));
		contentArea.setLineWrap(true);
		contentArea.setWrapStyleWord(true);
		contentArea.setOpaque(false);
		lbHeader = new JLabel();
		lbIcon = new JLabel();
		lbIcon.setIcon(DBIcons.getIcon(DBIcons.ICON_ITEM_LIST));
		
		JPanel contentPanel = new JPanel(new BorderLayout(3, 3));
		contentPanel.setBorder(BorderFactory.createEmptyBorder(1, 2, 1, 1));
		contentPanel.setOpaque(false);
		contentPanel.add(lbHeader, BorderLayout.NORTH);
		contentPanel.add(contentArea, BorderLayout.CENTER);
		contentPanel.add(lbIcon, BorderLayout.WEST);
		
		add(contentPanel, BorderLayout.CENTER);
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		if(value instanceof DBListItem) {
			contentArea.setText(value.toString());
			setForeground(LABEL_COLOR);
		} else if(value instanceof DBListHeader) {
			lbHeader.setText(value.toString());
		}
		return this;
	}
}
