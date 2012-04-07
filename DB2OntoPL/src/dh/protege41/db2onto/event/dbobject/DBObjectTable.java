package dh.protege41.db2onto.event.dbobject;

public class DBObjectTable extends DBObject {
	public DBObjectTable() {
		super(DBObjectType.DB_TABLE_OBJECT, "Unknown");
	}
	public DBObjectTable(String name) {
		super(DBObjectType.DB_TABLE_OBJECT, name);
	}
}
