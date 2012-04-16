package dh.protege41.db2onto.event.dbobject;

public class DBObjectForeignKey extends DBObject {
	
	public static final String FKTABLE_CAT = "FKTABLE_CAT";
	public static final String FKTABLE_SCHEM = "FKTABLE_SCHEM";
	public static final String FKTABLE_NAME = "FKTABLE_NAME";
	public static final String FKCOLUMN_NAME = "FKCOLUMN_NAME";
	public static final String FK_NAME = "FK_NAME";
	
	private String FKTable;
	private String toTable;
	private String FKColumn;
	private String toColumn;
	
	public DBObjectForeignKey(String name, String fkTable, String toTable, String fkColumn, String toColumn) {
		super(DBObjectType.DB_FK_OBJECT, name);
		this.FKTable = fkTable;
		this.toTable = toTable;
		this.FKColumn = fkColumn;
		this.toColumn = toColumn;
	}

	public String getFKTable() {
		return FKTable;
	}

	public void setFKTable(String fKTable) {
		FKTable = fKTable;
	}

	public String getToTable() {
		return toTable;
	}

	public void setToTable(String toTable) {
		this.toTable = toTable;
	}

	public String getFKColumn() {
		return FKColumn;
	}

	public void setFKColumn(String fKColumn) {
		FKColumn = fKColumn;
	}

	public String getToColumn() {
		return toColumn;
	}

	public void setToColumn(String toColumn) {
		this.toColumn = toColumn;
	}
	
	
}
