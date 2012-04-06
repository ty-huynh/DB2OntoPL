package dh.protege41.db2onto.event.dbobject;

public class DBObject {
	private String _type;
	private String _name;
	
	public String toString() {
		return _type + " : " + _name;
	}
	
	public DBObject() {
		_type = DBObjectType.DB_NULL_OBJECT;
		_name = "unnamed";
	}
	
	public DBObject(String type, String name) {
		_type = type;
		_name = name;
	}

	public String get_type() {
		return _type;
	}

	public void set_type(String _type) {
		this._type = _type;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}
	
	
}
