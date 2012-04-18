package dh.protege41.db2onto.event.dbobject;

public interface DBObjectType {
	
	public static final String DB_NULL_OBJECT = "untyped";
	public static final String DB_DATABASE_OBJECT = "database";
	public static final String DB_TABLE_OBJECT = "table";
	public static final String DB_COLUMN_OBJECT = "column";
	public static final String DB_FK_OBJECT = "ForeignKey";
	public static final String DB_PK_OBJECT = "PrimaryKey";
	
	
	public static final int CASE_0 = 0x0;
	public static final int CASE_1 = 0x1;
	public static final int CASE_2 = 0x2;
	public static final int CASE_3 = 0x3;
}
