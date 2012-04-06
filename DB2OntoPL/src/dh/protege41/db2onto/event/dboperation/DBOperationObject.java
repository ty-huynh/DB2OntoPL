package dh.protege41.db2onto.event.dboperation;

public class DBOperationObject {
	
	private String _operation;
	public DBOperationObject(String dbOperation) {
		_operation = dbOperation;
	}
	
	public String toString() {
		return "Operation : " + _operation;
	}
	
	public String getOperation() {
		return _operation;
	}
}
