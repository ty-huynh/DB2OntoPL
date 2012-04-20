package dh.protege41.db2onto.common;

import java.awt.Color;


public interface DB2OntoPLConstants {
	public static final String RESOURCES = "/resources/";
	public static final String RESOURCE_IMAGES = "/resources/images/";
	
	public static final String MESSAGE_TABLE_NULL_TITLE = "Could not create individuals";
	public static final String MESSAGE_TABLE_NULL_CONTENT = "<html>You selected no records. <br />Please connect your database and query records.</html>";
	
	public static final String STATUS_CONNECTED = "Your database was connected";
	public static final String STATUS_DISCONNECTED = "You didn't connect to database yet";

	public static final Color STATUS_CONNECTED_COLOR = Color.GREEN.darker();
	public static final Color STATUS_DISCONNECTED_COLOR = Color.RED;
	
}
