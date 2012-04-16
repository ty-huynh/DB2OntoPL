package dh.protege41.db2onto.event.dbobject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DBObjectTable extends DBObject {
	public static final String TABLE_CAT = "TABLE_CAT"; //string: show name of database
	public static final String TABLE_SCHEM = "TABLE_SCHEM";//string dbo
	public static final String TABLE_NAME = "TABLE_NAME"; //string name of table
	public static final String TABLE_TYPE = "TABLE_TYPE";//string
	public static final String EXCEPT_TABLE = "sysdiagrams";
	//header name
	public static final String CATEGORY = "Category";
	public static final String SCHEM = "Schem";
	public static final String TYPE = "Type";
	public static final String COLUMNS = "Columns";
	public static final String REF_BY = "Referenced by";
	public static final String REF_TO = "Reference to";
	public static final String PRIMARY_KEY = "Primary key";
	public static final String FOREIGN_KEY = "Foreign keys";
	
	private String category;
	private String schem;
	private String type;
	private int tableCase;
	
	private Set<DBObjectColumn> columns = new HashSet<DBObjectColumn>();
	private Set<DBObjectPrimaryKey> primaryKeys = new HashSet<DBObjectPrimaryKey>();
	private Set<DBObjectForeignKey> foreignKeys = new HashSet<DBObjectForeignKey>();
	
	public DBObjectTable() {
		super(DBObjectType.DB_TABLE_OBJECT, "Unknown");
	}
	public DBObjectTable(String name) {
		super(DBObjectType.DB_TABLE_OBJECT, name);
	}
	public DBObjectTable(String name, String cat, String schem, String type) {
		super(DBObjectType.DB_TABLE_OBJECT, name);
		this.category = cat;
		this.schem = schem;
		this.type = type;
	}
	public DBObjectTable(String name, String cat, String schem, String type, List<DBObjectColumn> cols, List<DBObjectForeignKey> fks, List<DBObjectPrimaryKey> pks) {
		super(DBObjectType.DB_TABLE_OBJECT, name);
		this.category = cat;
		this.schem = schem;
		this.type = type;
		this.columns.addAll(cols);
		this.primaryKeys.addAll(pks);
		this.foreignKeys.addAll(fks);
	}
	
	public DBObjectColumn getColumnByName(String colName) {
		if(colName == null) 
			return null;
		for(DBObjectColumn col : this.columns) {
			if(colName.equals(col.getName()))
				return col;
		}
		return null;
	}
	public void addColumn(DBObjectColumn col) {
		for(DBObjectColumn obj : this.columns) {
			if(obj.getName().equals(col.getName()))
				return;
		}
		this.columns.add(col);
	}
	public void addPrimaryKey(DBObjectPrimaryKey pk) {
		for(DBObjectPrimaryKey obj : this.primaryKeys) {
			if(obj.getName().equals(pk.getName()))
				return;
		}
		this.primaryKeys.add(pk);
	}
	public void addForeignKey(DBObjectForeignKey fk) {
		for(DBObjectForeignKey obj : this.foreignKeys) {
			if(obj.getName().equals(fk.getName()))
				return;
		}
		this.foreignKeys.add(fk);
	}
	
	
	
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSchem() {
		return schem;
	}
	public void setSchem(String schem) {
		this.schem = schem;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTableCase() {
		return tableCase;
	}
	public void setTableCase(int tableCase) {
		this.tableCase = tableCase;
	}
	public Set<DBObjectColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<DBObjectColumn> columns) {
		this.columns.addAll(columns);
	}
	public Set<DBObjectPrimaryKey> getPrimaryKeys() {
		return primaryKeys;
	}
	public void setPrimaryKeys(List<DBObjectPrimaryKey> primaryKeys) {
		this.primaryKeys.addAll(primaryKeys);
	}
	public Set<DBObjectForeignKey> getForeignKeys() {
		return foreignKeys;
	}
	public void setForeignKeys(List<DBObjectForeignKey> foreignKeys) {
		this.foreignKeys.addAll(foreignKeys);
	}
	
}
