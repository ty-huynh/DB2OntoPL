package dh.protege41.db2onto.event.dboperation;

public interface DBOperationEventType {
	public static final String DB_OPERATION_CHANGE = "DB_OPERATION_CHANGE";
	public static final String DB_OPERATION_SETUP = "DB_OPERATION_SETUP";
	public static final String DB_OPERATION_LOAD = "DB_OPERATION_LOAD";
	public static final String DB_OPERATION_LOADING = "DB_OPERATION_LOADING";
	public static final String DB_OPERATION_LOADED = "DB_OPERATION_LOADED";
	public static final String DB_OPERATION_CANCEL = "DB_OPERATION_CANCEL";
	public static final String DB_OPERATION_CONNECT = "DB_OPERATION_CONNECT";
	public static final String DB_OPERATION_CONNECTED = "DB_OPERATION_CONNECTED";
	public static final String DB_OPERATION_DISCONNECT = "DB_OPERATION_DISCONNECT";
	
	public static final String DB_OPERATION_MAPPING = "DB_OPERATION_MAPPING";
}
