package dh.database.api;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import dh.database.api.object.DBObjectColumn;
import dh.database.api.object.DBObjectDatabase;
import dh.database.api.object.DBObjectForeignKey;
import dh.database.api.object.DBObjectPrimaryKey;
import dh.database.api.object.DBObjectTable;


public class TestConnection {
	DBOperationImplement dbOperation = null;
	private static final Logger log = Logger.getLogger(TestConnection.class);
	
	public void MSAccessTest () throws SQLException {
		String database = "E:\\ty\\Students.mdb";
		String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + database;
		dbOperation = new DBOperationImplement(DBDriver.MSACCESS, url, database);
		log.info(DBType.MSACCESS);
	}
	
	public void SQLServerTest () throws SQLException {
		String database = "School";
		String url = "jdbc:sqlserver://localhost:1433;databaseName=" + database + ";selectMethod=cursor";
		dbOperation = new DBOperationImplement(DBDriver.SQLSERVER, url, database, "sa", "12345");
		log.info(DBType.SQLSERVER);
	}
	
	public void DatabaseConnectionTest(String driver, String dbName, String url, String user, String pass) {
		dbOperation = new DBOperationImplement(driver, url, dbName, user, pass);
	}
	
	public void ExeTest() throws Exception {
		log.info("Exported Keys");
		DatabaseMetaData meta = dbOperation.getDatabaseMetaData();
		ResultSet rsTables = dbOperation.getTables();
		while(rsTables.next()) {
			
			String tableName = rsTables.getString("TABLE_NAME");
			log.info("table: " + tableName);
			log.info("+++++++++++++++++++++++++++++++++++++++");
			ResultSet rsdb = meta.getExportedKeys(null, null, tableName);
//			ResultSet rsColumns = dbOperation.getColumns(tableName);
			ResultSetMetaData rsdbmt = rsdb.getMetaData();
			while(rsdb.next()) {
				log.info("---------------");
				for(int i = 1; i <= rsdbmt.getColumnCount(); i++) {
					log.info(rsdbmt.getColumnLabel(i) + " : " + rsdb.getString(i));
				}
			}
		}
		ResultSet rs = null;
		try {
			rs = dbOperation.exeQuery("SELECT * FROM Study");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSetMetaData rsmeta = rs.getMetaData();
		log.info("column count : " + rsmeta.getColumnCount());
		log.info("records count:");
		while(rs.next()) {
			System.out.println(rs.getString("user"));
		}
		log.info("==================================================================");
		rs.close();
//		dbOperation.close();
//		dbOperation = null;
	}
	DBObjectDatabase dbObject;
	
	public void processDatabaseMetaData() {
		try {
			DatabaseMetaData meta = dbOperation.getDatabaseMetaData();
			dbObject = new DBObjectDatabase(
					meta.getDatabaseProductName(),
					meta.getDatabaseProductName(),
					meta.getDatabaseProductVersion(),
					meta.getDriverName(),
					meta.getDriverVersion(),
					meta.getURL(),
					meta.getUserName(),
					meta.isReadOnly());
			ResultSet rsTables = dbOperation.getTables();
			while(rsTables.next()) {
				String tableName = rsTables.getString(DBObjectTable.TABLE_NAME);
				String tableCat = rsTables.getString(DBObjectTable.TABLE_CAT);
				String tableSchem = rsTables.getString(DBObjectTable.TABLE_SCHEM);
				String tableType = rsTables.getString(DBObjectTable.TABLE_TYPE);
				DBObjectTable table = new DBObjectTable(tableName, tableCat, tableSchem, tableType);
				//get columns
				ResultSet rsColumns = dbOperation.getColumns(tableName);
				while(rsColumns.next()) {
					DBObjectColumn column = new DBObjectColumn(
							rsColumns.getString(DBObjectColumn.COLUMN_NAME),
							rsColumns.getString(DBObjectColumn.TYPE_NAME),
							rsColumns.getString(DBObjectColumn.NULLABLE));
					table.addColumn(column);
				}
				dbObject.addTable(table);
			}
			//get keys
			for(DBObjectTable obj : dbObject.getTables()) {
				//get primary keys
				ResultSet rspk = meta.getPrimaryKeys(null, null, obj.getName());
				while(rspk.next()) {
					DBObjectPrimaryKey pk = new DBObjectPrimaryKey(rspk.getString(DBObjectPrimaryKey.PK_NAME), rspk.getString(DBObjectPrimaryKey.COLUMN_NAME), rspk.getString(DBObjectPrimaryKey.TABLE_NAME));
					dbObject.getTableByName(rspk.getString(DBObjectPrimaryKey.TABLE_NAME)).addPrimaryKey(pk);
					dbObject.getTableByName(pk.getTable()).getColumnByName(pk.getColumn()).setPrimaryKey(true);
				}
				//get foreign keys
				ResultSet rsek = meta.getExportedKeys(null, null, obj.getName());
				while(rsek.next()) {
					DBObjectForeignKey fk = new DBObjectForeignKey(rsek.getString(DBObjectForeignKey.FK_NAME), rsek.getString(DBObjectForeignKey.FKTABLE_NAME), rsek.getString(DBObjectPrimaryKey.PKTABLE_NAME), rsek.getString(DBObjectForeignKey.FKCOLUMN_NAME), rsek.getString(DBObjectPrimaryKey.PKCOLUMN_NAME));
					dbObject.getTableByName(rsek.getString(DBObjectForeignKey.FKTABLE_NAME)).addForeignKey(fk);
					dbObject.getTableByName(fk.getFKTable()).getColumnByName(fk.getFKColumn()).setForeignKey(true);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void processExportedKeys() {
		
	}
	
	public static void main(String args[]) {
		TestConnection test = new TestConnection();
//		test.MSAccessTest();
//		test.ExeTest();
		try {
			test.SQLServerTest();
			test.ExeTest();
			test.processDatabaseMetaData();
			for (DBObjectTable to : test.dbObject.getTables()) {
				log.info("---------------");
				log.info("Table : " + to.getName());
				log.info("cos: " + to.getColumns());
				log.info("pks: " + to.getPrimaryKeys());
				log.info("fks: " + to.getForeignKeys());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
