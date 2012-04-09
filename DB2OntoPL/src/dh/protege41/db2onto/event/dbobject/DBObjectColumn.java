package dh.protege41.db2onto.event.dbobject;

public class DBObjectColumn extends DBObject {
	
	public static final String COLUMN_NAME = "COLUMN_NAME";//string
	public static final String COLUMN_SIZE = "COLUMN_SIZE";//int
	public static final String DATA_TYPE = "DATA_TYPE";//int: 4 12 -6
	public static final String TYPE_NAME = "TYPE_NAME";//String name of type
	public static final String IS_NULLABLE = "IS_NULLABLE";//int
	public static final String BUFFER_LENGTH = "BUFFER_LENGTH";//int
	public static final String DECIMAL_DIGIT = "DECIMAL_DIGIT";//invalid
	public static final String NUM_PREC_RADIX = "NUM_PREC_RADIX";//number
	public static final String NULLABLE = "NULLABLE";//int
	public static final String REMARKS = "REMARKS"; //nothing
	public static final String COLUMN_DEF = "COLUMN_DEF"; //string but nothing
	public static final String SQL_DATA_TYPE = "SQL_DATA_TYPE";//int: 4 12 -6
	public static final String SQL_DATETIME_SUB = "SQL_DATETIME_SUB";//nothing
	public static final String CHAR_OCTET_LENGTH = "CHAR_OCTET_LENGTH";//int
	public static final String ORIGINAL_POSITION = "ORIGINAL_POSITION";//invalid

	//header name
	public static final String COL_DATA_TYPE = "Data type";
	public static final String COL_TYPE_NAME = "Type name";
	public static final String COL_SIZE = "Size";
	public static final String COL_NULL = "Is null";
	public static final String COL_PK = "Is primary key";
	public static final String COL_FK = "Is foreign key";
	
	private int dataType;
	private String typeName;
	private int size;
	private boolean isNull;
	
	public DBObjectColumn() {
		super(DBObjectType.DB_COLUMN_OBJECT, "Unknown");
	}
	public DBObjectColumn(String name) {
		super(DBObjectType.DB_COLUMN_OBJECT, name);
	}
	public DBObjectColumn(
			String name, 
			String dataType, 
			String typeName, 
			String size, 
			String isNull) {
		super(DBObjectType.DB_COLUMN_OBJECT, name);
		try{
			this.dataType = Integer.parseInt(dataType);
			this.typeName = typeName;
			this.size = Integer.parseInt(size);
			this.isNull = (Integer.parseInt(isNull) == 0) ? false : true;
		} catch(Exception e) {
			log.info("create column object error: convert error");
		}
	}
	
	public String isPrimaryKey() {
		return (getTypeName().contains("identity") ? "YES" : "NO");
	}
	
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String isNull() {
		return (isNull ? "YES" : "NO");
	}
	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}
	
	
}
