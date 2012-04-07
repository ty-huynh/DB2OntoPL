package dh.database.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface DBOperation {
	public Connection createConnection() throws Exception;
	public void close() throws Exception;
	public Statement getStatement() throws Exception;
	public ResultSet exeQuery(String query) throws Exception;
	public boolean exeUpdate(String sql) throws Exception;
}
