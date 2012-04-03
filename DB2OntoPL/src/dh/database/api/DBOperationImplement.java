package dh.database.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBOperationImplement implements DBOperation {

	Connection conn = null;
	DBConnection dbConnection = null;
	
	
	public DBOperationImplement() {
		dbConnection = new DBConnection();
	}
	
	public DBOperationImplement(String driver, String url, String databaseName) {
		dbConnection = new DBConnection(driver, url, databaseName);
	}
	
	public DBOperationImplement(String driver, String url, String databaseName, String username, String password) {
		dbConnection = new DBConnection(driver, url, databaseName, username, password);
	}

	@Override
	public Connection createConnection() throws SQLException {
		// TODO Auto-generated method stub
		if(conn != null) {
			conn = null;
		}
		try {
			this.conn = dbConnection.connect();
			System.out.println("connected");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("connection error");
		}
		return conn;
	}
	
	@Override
	public void close() throws SQLException {
		// TODO Auto-generated method stub
		if(conn != null) {
			conn.close();
		}
		else {
			conn = null;
		}
	}

	@Override
	public Statement getStatement() throws SQLException {
		// TODO Auto-generated method stub
		if(conn == null) {
			createConnection();
		}
		return conn.createStatement();
	}

	@Override
	public ResultSet exeQuery(String query) throws SQLException {
		// TODO Auto-generated method stub
		if(conn == null) {
			createConnection();
		}
		return this.getStatement().executeQuery(query);
	}

	@Override
	public boolean exeUpdate(String sql) throws SQLException {
		// TODO Auto-generated method stub
		if(conn == null) {
			createConnection();
		}
		return this.getStatement().execute(sql);
	}

	public DBConnection getDbConnection() {
		return dbConnection;
	}

	public void setDbConnection(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

}
