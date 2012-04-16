package dh.protege41.db2onto.event.dbobject;

public class DBObjectPrimaryKey extends DBObject {
	
	//INFO IN getExportedKey
	public static final String PKTABLE_CAT = "PKTABLE_CAT";
	public static final String PKTABLE_SCHEM = "PKTABLE_SCHEM";
	public static final String PKTABLE_NAME = "PKTABLE_NAME";
	public static final String PKCOLUMN_NAME = "PKCOLUMN_NAME";
	public static final String UPDATE_RULE = "UPDATE_RULE";
	public static final String DELETE_RULE = "DELETE_RULE";
	
	//INFO IN getPrimaryKey
	public static final String TABLE_CAT = "TABLE_CAT";
	public static final String TABLE_SCHEM = "TABLE_SCHEM";
	public static final String TABLE_NAME = "TABLE_NAME";
	public static final String COLUMN_NAME = "COLUMN_NAME";
	
	public static final String PK_NAME = "PK_NAME";
	public static final String KEY_SEQ = "KEY_SEQ";
	
	private String column;
	private String table;
	
	public DBObjectPrimaryKey(String name, String col, String tab) {
		super(DBObjectType.DB_PK_OBJECT, name);
		this.column = col;
		this.table = tab;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}
}
