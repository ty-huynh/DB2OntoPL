package dh.protege41.db2onto.event.dbobject;

public class DBObject {
	private String _type;
	private String _name;
	
	public String toString() {
		return _name;
	}
	
	public DBObject() {
		_type = DBObjectType.DB_NULL_OBJECT;
		_name = "unnamed";
	}
	
	public DBObject(String name) {
		_type = DBObjectType.DB_NULL_OBJECT;
		_name = name;
	}
	
	public DBObject(String type, String name) {
		_type = type;
		_name = name;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		this._type = type;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}
	
	
}
