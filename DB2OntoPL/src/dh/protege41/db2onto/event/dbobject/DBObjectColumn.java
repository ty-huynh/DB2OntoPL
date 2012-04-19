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
	public static final String COL_NULLABLE = "Is nullable";
	public static final String COL_PK = "Is primary key";
	public static final String COL_FK = "Is foreign key";
	
//	private int dataType;
	private String typeName;
//	private int size;
	private boolean isNullable;
	private boolean isUnique;
	private boolean isFK;
	private boolean isPK;
//	private DBObjectTable table;

	public DBObjectColumn() {
		super(DBObjectType.DB_COLUMN_OBJECT, "Unknown");
	}
	public DBObjectColumn(String name) {
		super(DBObjectType.DB_COLUMN_OBJECT, name);
	}
	public DBObjectColumn(String name, String typeName, String nullable) {
		super(DBObjectType.DB_COLUMN_OBJECT, name);
		this.typeName = typeName;
//		this.table = table;
		this.isFK = false;
		this.isPK = false;
		try{
			this.isNullable = ((Integer.parseInt(nullable)) == 0 ? false : true);
		}catch(Exception e) {
			
		}
		this.isUnique = false;
	}
	
	public DBObjectColumn(String name, String typeName, String nullable, boolean isUnique, boolean isPK, boolean isFK) {
		super(DBObjectType.DB_COLUMN_OBJECT, name);
		this.typeName = typeName;
//		this.table = table;
		this.isFK = isFK;
		this.isPK = isPK;
		try{
			this.isNullable = ((Integer.parseInt(nullable)) == 0 ? false : true);
		}catch(Exception e) {
		}
		this.isUnique = isUnique;
	}
	
	public String toString() {
		String name = getName();
		if(isPK) {
			name += " [PK]";
		} 
		if(isFK) {
			name += " [FK]";
		}
		return name;
	}
	
	public String getNullableString() {
		return (isNullable ? "YES" : "NO");
	}
	public String getPrimaryString() {
		return (isPK ? "YES" : "NO");
	}
	public String getForeignString() {
		return (isFK ? "YES" : "NO");
	}
//	public int getDataType() {
//		return dataType;
//	}
//	public void setDataType(int dataType) {
//		this.dataType = dataType;
//	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
//	public int getSize() {
//		return size;
//	}
//	public void setSize(int size) {
//		this.size = size;
//	}
	public boolean isNullable() {
		return isNullable;
	}
	public void setNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}
	public boolean isUnique() {
		return isUnique;
	}
	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}
	public boolean isForeignKey() {
		return isFK;
	}
	public void setForeignKey(boolean isFK) {
		this.isFK = isFK;
	}
	public boolean isPrimaryKey() {
		return isPK;
	}
	public void setPrimaryKey(boolean isPK) {
		this.isPK = isPK;
	}
//	public DBObjectTable getTable() {
//		return table;
//	}
//	public void setTable(DBObjectTable table) {
//		this.table = table;
//	}
	
	
}
