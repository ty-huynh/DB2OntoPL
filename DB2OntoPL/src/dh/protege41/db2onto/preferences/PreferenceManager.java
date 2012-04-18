package dh.protege41.db2onto.preferences;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

public class PreferenceManager {
	
	private static Preferences prefs = Preferences.userNodeForPackage(PreferenceManager.class);
	
	public static void saveLastConnectionInfo(Date time, String dbType, String dbDriver, String dbName, String dbHost, String dbPort, String dbUser, String dbPass) {
		prefs.put(PreferenceManager.KeyPref.LAST_CONNECTION_TIME, time.toString());
		prefs.put(PreferenceManager.KeyPref.LAST_CONNECTION_DBTYPE, dbType);
		prefs.put(PreferenceManager.KeyPref.LAST_CONNECTION_DRIVER, dbDriver);
		prefs.put(PreferenceManager.KeyPref.LAST_CONNECTION_DBNAME, dbName);
		prefs.put(PreferenceManager.KeyPref.LAST_CONNECTION_HOST, dbHost);
		prefs.put(PreferenceManager.KeyPref.LAST_CONNECTION_PORT, dbPort);
		prefs.put(PreferenceManager.KeyPref.LAST_CONNECTION_USERNAME, dbUser);
		prefs.put(PreferenceManager.KeyPref.LAST_CONNECTION_PASSWORD, dbPass);
	}
	
	public static Preferences getPreferences() {
		return prefs;
	}
	//static constants here
	class KeyPref {
		public static final String LAST_CONNECTION_TIME = "LAST_CONNECTION_TIME";
		public static final String LAST_CONNECTION_DBTYPE = "LAST_CONNECTION_DBTYPE";
		public static final String LAST_CONNECTION_DRIVER = "LAST_CONNECTION_DRIVER";
		public static final String LAST_CONNECTION_DBNAME = "LAST_CONNECTION_DBNAME";
		public static final String LAST_CONNECTION_HOST = "LAST_CONNECTION_HOST";
		public static final String LAST_CONNECTION_PORT = "LAST_CONNECTION_PORT";
		public static final String LAST_CONNECTION_USERNAME = "LAST_CONNECTION_USERNAME";
		public static final String LAST_CONNECTION_PASSWORD = "LAST_CONNECTION_PASSWORD";
	}
	
}
