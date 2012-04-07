package dh.protege41.db2onto.event.dbobject;

public class DBObjectTable extends DBObject {
	public static final String TABLE_CAT = "TABLE_CAT"; //string: show name of database
	public static final String TABLE_SCHEM = "TABLE_SCHEM";//string dbo
	public static final String TABLE_NAME = "TABLE_NAME"; //string name of table
	public static final String TABLE_TYPE = "TABLE_TYPE";//string
	
	private String category;
	private String schem;
	private String type;
	
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
	
}
