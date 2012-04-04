package dh.protege41.db2onto.tab.ui;

public interface DatabasePanel {
	
	//event for database configure panel
	public static final int DB_EVENT_NONE = 0;
	public static final int DB_EVENT_CHANGE = 1;
	public static final int DB_EVENT_LOAD = 2;
	public static final int DB_EVENT_CANCEL = 3;
	public static final int DB_EVENT_DBTYPE_CHANGED = 4;
	
	
	public void initComponents();
	public void attachComponents();
	public void initEventListeners();
	public void handleEvents(int event);
	
}
