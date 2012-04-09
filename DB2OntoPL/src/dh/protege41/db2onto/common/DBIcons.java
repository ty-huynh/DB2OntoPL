package dh.protege41.db2onto.common;

import java.net.URL;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

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
}
