package dh.database.api;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import dh.database.api.exception.DHConnectionException;

public class DBOperationImplement implements DBOperation {

	Connection conn = null;
	DBConnection dbConnection = null;
	private static final Logger log = Logger.getLogger(DBOperationImplement.class);
	
	public DBOperationImplement() {
		dbConnection = new DBConnection();
	}
	
	public DBOperationImplement(String driver, String url, String databaseName) {
		dbConnection = new DBConnection(driver, url, databaseName);
	}
	
	public DBOperationImplement(String driver, String url, String databaseName, String username, String password) {
		dbConnection = new DBConnection(driver, url, databaseName, username, password);
	}
	
	public Connection getCurrentConnection() {
		return conn;
	}
	
	@Override
	public Connection createConnection() throws DHConnectionException {
		// TODO Auto-generated method stub
		if(conn != null) {
			conn = null;
		}
		try {
			this.conn = dbConnection.connect();
		} catch (Exception e) {
			throw new DHConnectionException("connection failure");
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
	public Statement getStatement() throws Exception {
		// TODO Auto-generated method stub
		if(conn == null) {
			createConnection();
		}
		return conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}

	@Override
	public ResultSet exeQuery(String query) throws Exception {
		// TODO Auto-generated method stub
		if(conn == null) {
			createConnection();
		}
		return this.getStatement().executeQuery(query);
	}

	@Override
	public boolean exeUpdate(String sql) throws Exception {
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

	//get metadata
	public DatabaseMetaData getDatabaseMetaData() throws Exception {
		if(conn == null) {
			createConnection();
		}
		return conn.getMetaData();
	}
	public ResultSet getTables() throws Exception {
		ResultSet rs = null;
		try {
			rs = getDatabaseMetaData().getTables(null, null, "%", new String[] {"TABLE"});
			log.info("Get table done");
		} catch (SQLException e) {
			log.info("Get table error");
		}
		return rs;
	}
	public ResultSet getColumns(String table) throws Exception {
		ResultSet rs = null;
		try{
			rs = getDatabaseMetaData().getColumns(null, null, table, "%");
			log.info("Get columns done");
		} catch(SQLException e) {
			log.info("Get columns error");
		}
		return rs;
	}
	public int getResultSetSize(ResultSet rs) {
		int count = 0;
		try {
			while(rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			log.info("get size of resultset error");
		}
		return count;
	}
}
