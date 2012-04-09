package dh.protege41.db2onto.event.dbobject;

public class DBObjectDatabase extends DBObject {
	
	public static final String PRODUCT_NAME = "Product name";
	public static final String PRODUCT_VERSION = "Product version";
	public static final String DRIVER_NAME = "Driver name";
	public static final String DRIVER_VERSION = "Driver version";
	public static final String URL = "Url";
	public static final String USERNAME = "Username";
	public static final String READONLY = "Read only";
	public static final String RELATIONS = "Relations";
	
	private String productName;
	private String productVersion;
	private String driverName;
	private String driverVersion;
	private String url;
	private String username;
	private boolean isReadOnly;
	
	public DBObjectDatabase() {
		super(DBObjectType.DB_DATABASE_OBJECT, "Unknown");
	}
	public DBObjectDatabase(String name) {
		super(DBObjectType.DB_DATABASE_OBJECT, name);
	}
	public DBObjectDatabase(
			String name, 
			String productName, 
			String productVersion, 
			String driverName, 
			String driverVersion, 
			String url, 
			String username, 
			boolean isReadOnly) {
		super(DBObjectType.DB_DATABASE_OBJECT, name);
		this.productName = productName;
		this.productVersion = productVersion;
		this.driverName = driverName;
		this.driverVersion = driverVersion;
		this.url = url;
		this.username = username;
		this.isReadOnly = isReadOnly;
	}
	
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductVersion() {
		return productVersion;
	}
	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverVersion() {
		return driverVersion;
	}
	public void setDriverVersion(String driverVersion) {
		this.driverVersion = driverVersion;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String isReadOnly() {
		return (isReadOnly? "Yes" : "No");
	}
	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}
}
