package dh.database.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface DBOperation {
	public Connection createConnection() throws Exception;
	public void close() throws SQLException;
	public Statement getStatement() throws SQLException;
	public ResultSet exeQuery(String query) throws SQLException;
	public boolean exeUpdate(String sql) throws SQLException;
}
