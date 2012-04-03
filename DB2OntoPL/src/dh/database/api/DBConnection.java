package dh.database.api;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	String driver = "";
	String url = "";
	String server = "127.0.0.1";
	String port = "1433";
	String databaseName = "";
	String username = "";
	String password = "";
	String dbType = "";
	
	
	public DBConnection() {
		
	}
	
	public DBConnection(String driver, String url, String databaseName) {
		this.driver = driver;
		this.url = url;
		this.databaseName = databaseName;
	}
	
	public DBConnection(String driver, String url, String databaseName, String username, String password) {
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
		this.databaseName = databaseName;
	}

	public Connection connect() throws Exception {
		Class.forName(this.driver);
		return DriverManager.getConnection(url, username, password);
	}
	
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
}