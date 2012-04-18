package dh.protege41.db2onto.common;

import java.util.List;

import org.apache.log4j.Logger;

public class DBDataType {
//	INT("int"),
//	INT_IDENTIFIER("int identifier"),
//	SMALL_INT("smallint"),
//	BYTE("byte"),
//	SHORT("short"),
//	LONG("long"),
//	NUMERIC("numeric"),
//	DECIMAL("decimal"),
//	REAL("real"),
//	FLOAT("float"),
//	CHAR("char"),
//	NCHAR("nchar"),
//	VARCHAR("varchar"),
//	NVARCHAR("nvarchar"),
//	NTEXT("ntext"),
//	DATETIME("datatime"),
//	SMALL_DATETIME("smalldatetime"),
//	TIMESTAMP("timestamp")
	
	public static final String[] NUMERIC_INT = new String[] {"int", "int identifier", "smallint", "byte", "short", "tinyint", "decimal"};
	public static final String[] NUMERIC_REAL = new String[] {"real", "numeric", "float", "double"};
	public static final String[] CHARS = new String[] {"char", "nchar", "ntext", "varchar", "nvarchar", "string"};
	public static final String[] DATETIME = new String[] {"datetime", "smalldatetime", "timestamp"};
	
	private List<String> types;
	
	public static final String INT = "integer";
	public static final String DOUBLE = "double";
	public static final String STRING = "string";
	public static final String DATE = "datetime";
	public static final String UNKNOWN = "unknown";
	public static final Logger log = Logger.getLogger(DBDataType.class);
	
	private DBDataType(String[] _types) {
		for(String o: _types) {
			types.add(o);
		}
	}
	
	public List<String> getTypes() {
		return types;
	}
	
	public void setTypes(List<String> types) {
		this.types = types;
	}
	
	public static String getRealType(String type) {
		String realType = getIntegerType(type);
		if(realType != null) {
			return realType;
		}
		realType = getDoubleType(type);
		if(realType != null) {
			return realType;
		}
		realType = getStringType(type);
		if(realType != null) {
			return realType;
		}
		realType = getDateTimeType(type);
		if(realType != null) {
			return realType;
		}
		realType = DBDataType.UNKNOWN;
		
		return realType;
	}
	
	public static String getIntegerType(String type) {
		for(String s : NUMERIC_INT) {
			if(s.equalsIgnoreCase(type)) {
				return DBDataType.INT;
			}
		}
		return null;
	}
	
	public static String getDoubleType(String type) {
		for(String s : NUMERIC_REAL) {
			if(s.equalsIgnoreCase(type)) {
				return DBDataType.DOUBLE;
			}
		}
		return null;
	}
	
	public static String getStringType(String type) {
		for(String s : CHARS) {
			if(s.equalsIgnoreCase(type)) {
				return DBDataType.STRING;
			}
		}
		return null;
	}
	
	public static String getDateTimeType(String type) {
		for(String s : DATETIME) {
			if(s.equalsIgnoreCase(type)) {
				return DBDataType.DATE;
			}
		}
		return null;
	}
}
