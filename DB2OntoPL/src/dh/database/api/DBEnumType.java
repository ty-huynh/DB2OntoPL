package dh.database.api;

public enum DBEnumType {
	
	MSACCESS("Microsoft Access Database", "sun.jdbc.odbc.JdbcOdbcDriver"),
	SQLSERVER("Microsoft SQL Server Database", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
	
	private String _type;
	private String _driver;
	
	DBEnumType(String type, String driver) {
		_type = type;
		_driver = driver;
	}
	
	public String getType() {
		return _type;
	}
	
	public String getDriver() {
		return _driver;
	}
	
	public static DBEnumType getDBEnumByType(String type) {
		for(DBEnumType obj : DBEnumType.values()) {
			if(obj.getType().equalsIgnoreCase(type)) {
				return obj;
			}
		}
		return null;
	}
	
	public static DBEnumType getDBEnumByDriver(String driver) {
		for(DBEnumType obj : DBEnumType.values()) {
			if(obj.getDriver().equalsIgnoreCase(driver)) {
				return obj;
			}
		}
		return null;
	}
	
	public static String[] getAllDBTypes() {
		DBEnumType[] dbEnum = DBEnumType.values();
		String dbTypes[] = new String[dbEnum.length];
		for(int i = 0, n = dbEnum.length; i < n; i++) {
			dbTypes[i] = dbEnum[i].getType();
		}
		return dbTypes;
	}
	
	public static String[] getAllDBDrivers() {
		DBEnumType[] dbEnum = DBEnumType.values();
		String dbDrivers[] = new String[dbEnum.length];
		for(int i = 0, n = dbEnum.length; i < n; i++) {
			dbDrivers[i] = dbEnum[i].getDriver();
		}
		return dbDrivers;
	}

	public String toString() {
		return _type + " has driver " + _driver;
	}
}
