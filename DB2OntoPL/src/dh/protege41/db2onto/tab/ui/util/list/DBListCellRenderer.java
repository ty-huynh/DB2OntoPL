package dh.protege41.db2onto.tab.ui.util.list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;

public class DBListCellRenderer extends JPanel implements ListCellRenderer {

	private JLabel lbIcon;
	private static final Color LABEL_COLOR = Color.BLUE.darker();
	private JTextArea contentArea;
	private JLabel lbHeader;
	public DBListCellRenderer() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(1, 2, 1, 1));
		
		contentArea = new JTextArea();
		contentArea.setFont(new Font("Dialog", Font.PLAIN, 12));
		contentArea.setLineWrap(true);
		contentArea.setWrapStyleWord(true);
		contentArea.setOpaque(false);
		
		lbHeader = new JLabel();
		
		lbIcon = new JLabel();
		try{
			URL imgUrl = getClass().getResource("icon.png");
			lbIcon.setIcon(new ImageIcon(imgUrl));
		} catch(Exception e) {
			System.out.println("could not find path icon.png");
		}
		
		
		JPanel contentPanel = new JPanel(new BorderLayout(3, 3));
		contentPanel.setBorder(BorderFactory.createEmptyBorder(2, 20, 2, 2));
		contentPanel.setOpaque(false);
		contentPanel.add(lbHeader, BorderLayout.NORTH);
		contentPanel.add(contentArea, BorderLayout.CENTER);
		contentPanel.add(lbIcon, BorderLayout.WEST);
		
		add(contentPanel, BorderLayout.CENTER);
		
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		if(value instanceof DBListItem) {
			contentArea.setText(value.toString());
			setForeground(LABEL_COLOR);
		} else if(value instanceof DBListHeader) {
			lbHeader.setText(value.toString());
		}
		return this;
	}

}
