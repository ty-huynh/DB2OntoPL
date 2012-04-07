package dh.database.api;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class TestConnection {
	DBOperationImplement dbOperation = null;
	private static final Logger log = Logger.getLogger(TestConnection.class);
	
	public void MSAccessTest () throws SQLException {
		String database = "E:\\ty\\Students.mdb";
		String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + database;
		dbOperation = new DBOperationImplement(DBDriver.MSACCESS, url, database);
		System.out.println(DBType.MSACCESS);
	}
	
	public void SQLServerTest () throws SQLException {
		String database = "DBTest";
		String url = "jdbc:sqlserver://localhost:1433;databaseName=" + database + ";selectMethod=cursor";
		dbOperation = new DBOperationImplement(DBDriver.SQLSERVER, url, database, "sa", "12345");
		System.out.println(DBType.SQLSERVER);
	}
	
	public void DatabaseConnectionTest(String driver, String dbName, String url, String user, String pass) {
		dbOperation = new DBOperationImplement(driver, url, dbName, user, pass);
	}
	
	public void ExeTest() throws SQLException{
		ResultSet rs = null;
		try {
			rs = dbOperation.exeQuery("SELECT * FROM users");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSetMetaData rsmeta = rs.getMetaData();
		System.out.println("column count : " + rsmeta.getColumnCount());
		System.out.println("records count:");
		while(rs.next()) {
			System.out.println(rs.getString("user"));
		}
		System.out.println("==================================================================");
		rs.close();
		dbOperation.close();
		dbOperation = null;
	}
	
//	public static void main(String args[]) throws Exception {
//		TestConnection test = new TestConnection();
////		test.MSAccessTest();
////		test.ExeTest();
//		
//		test.SQLServerTest();
//		test.ExeTest();
//	}
}
