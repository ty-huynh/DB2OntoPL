package dh.protege41.db2onto.tab.ui.util.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import org.apache.log4j.Logger;

import dh.protege41.db2onto.event.dbobject.DBObjectColumn;
import dh.protege41.db2onto.event.dbobject.DBObjectDatabase;
import dh.protege41.db2onto.event.dbobject.DBObjectTable;
import dh.protege41.db2onto.tab.ui.util.DBIcons;
import dh.protege41.db2onto.tab.ui.util.FontUtility;

public class DBTreeCellRenderer implements TreeCellRenderer {
	
	public static final Color SELECTION_BACKGROUND = UIManager.getDefaults().getColor("List.selectionBackground");
	public static final Color SELECTION_FOREGROUND = UIManager.getDefaults().getColor("List.selectionForeground");
	public static final Color FOREGROUND = UIManager.getDefaults().getColor("List.foreground");
	DefaultTreeCellRenderer defaultRenderer = new DefaultTreeCellRenderer();
	JLabel renderer = new JLabel();
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		Component returnValue = null;
		if((value != null) && (value instanceof DefaultMutableTreeNode)) {
			
			Object userObject = ((DefaultMutableTreeNode)value).getUserObject();
			renderer.setOpaque(true);
			String title = userObject.toString();
			renderer.setText(title);
			
			if(userObject instanceof DBObjectDatabase) {
				FontUtility.setLargeLabel(renderer, Font.BOLD);
				renderer.setIcon(DBIcons.getIcon(DBIcons.ICON_DATABASE_OBJECT));
			} else if(userObject instanceof DBObjectTable) {
				FontUtility.setMediumLabel(renderer, Font.BOLD);
				renderer.setIcon(DBIcons.getIcon(DBIcons.ICON_TABLE_OBJECT));
			} else if(userObject instanceof DBObjectColumn) {
				FontUtility.setSmallLabel(renderer, Font.BOLD);
				renderer.setIcon(DBIcons.getIcon(DBIcons.ICON_COLUMN_OBJECT));
			}
			if (selected) {
				renderer.setBackground(defaultRenderer.getBackgroundSelectionColor());
			} else {
				renderer.setBackground(defaultRenderer.getBackgroundNonSelectionColor());
			}
			renderer.setBorder(BorderFactory.createEmptyBorder(4, 2, 4, 0));
			renderer.setEnabled(tree.isEnabled());
			returnValue = renderer;
		}
		if (returnValue == null) {
			returnValue = defaultRenderer.getTreeCellRendererComponent(tree,
						value, selected, expanded, leaf, row, hasFocus);
		}
		return returnValue;
	}

}
