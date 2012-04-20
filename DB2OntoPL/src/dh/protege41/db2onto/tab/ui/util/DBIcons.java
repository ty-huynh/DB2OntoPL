package dh.protege41.db2onto.tab.ui.util;

import java.net.URL;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import dh.protege41.db2onto.common.DB2OntoPLConstants;

public class DBIcons {
	private static final Logger log = Logger.getLogger(DBIcons.class);
	
	public static ImageIcon getIcon(String name) {
		ImageIcon icon = null;
		try{
			URL imgUrl = DBIcons.class.getResource(DB2OntoPLConstants.RESOURCE_IMAGES + "" + name);
			icon = new ImageIcon(imgUrl);
		} catch(Exception e) {
			log.info("could not find icon : " + name);
		}
		return icon;
	}
	
	public static final String ICON_CREATE_INDI_BUTTON = "add.icon.png";
	public static final String ICON_DATABASE_OBJECT = "database.object.icon.png";
	public static final String ICON_TABLE_OBJECT = "table.object.icon.png";
	public static final String ICON_COLUMN_OBJECT = "column.object.icon.png";
	public static final String ICON_QUERY_BUTTON = "query.icon.png";
	public static final String ICON_CONNECT_BUTTON = "database.connect.icon.png";
	public static final String ICON_DISCONNECT_BUTTON = "database.disconnect.icon.png";
	public static final String ICON_BROWSE_BUTTON = "files.browse.icon.png";
	public static final String ICON_MAPPING_BUTTON = "mapping.icon.png";
	public static final String ICON_ITEM_LIST = "itemlist.icon.png";
	
}
