package dh.protege41.db2onto.event.dbobject;

public class DBObjectColumn extends DBObject {
	public DBObjectColumn() {
		super(DBObjectType.DB_COLUMN_OBJECT, "Unknown");
	}
	public DBObjectColumn(String name) {
		super(DBObjectType.DB_COLUMN_OBJECT, name);
	}
}
