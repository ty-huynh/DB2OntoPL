package dh.protege41.db2onto.event.dbobject;

public class DBObjectUnknown extends DBObject {
	public DBObjectUnknown() {
		super(DBObjectType.DB_NULL_OBJECT);
	}
	public DBObjectUnknown(String name) {
		super(DBObjectType.DB_NULL_OBJECT, name);
	}
}
