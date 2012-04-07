package dh.protege41.db2onto.event.dbobject;

public class DBObjectDatabase extends DBObject {
	//hashMap to store info
	public DBObjectDatabase() {
		super(DBObjectType.DB_DATABASE_OBJECT, "Unknown");
	}
	public DBObjectDatabase(String name) {
		super(DBObjectType.DB_DATABASE_OBJECT, name);
	}
}
