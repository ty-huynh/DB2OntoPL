package dh.protege41.db2onto.tab;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import dh.protege41.db2onto.event.dbobject.DBObject;
import dh.protege41.db2onto.event.dbobject.DBObjectDatabase;
import dh.protege41.db2onto.event.dbobject.DBObjectEventType;
import dh.protege41.db2onto.event.dbobject.DBObjectTable;
import dh.protege41.db2onto.event.dboperation.DBOperationObject;
import dh.protege41.db2onto.tab.ui.DatabasePanel;
import dh.protege41.db2onto.tab.ui.DescriptionPanel;
import dh.protege41.db2onto.tab.ui.util.list.DBList;
import dh.protege41.db2onto.tab.ui.util.list.DBListHeader;
import dh.protege41.db2onto.tab.ui.util.list.DBListItem;
import dh.protege41.db2onto.tab.ui.util.list.DBMutableListModel;

public class DatabaseDescriptionViewComponent extends DatabaseViewComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(DatabaseDescriptionViewComponent.class);
	private DatabaseDescriptionPanel dbDescriptionComponent;
	@Override
	protected void disposeOWLView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initialiseOWLView() throws Exception {
		super.initialiseOWLView();
		setLayout(new BorderLayout());
		dbDescriptionComponent = new DatabaseDescriptionPanel();
		add(dbDescriptionComponent, BorderLayout.CENTER);
		log.info("database description view component initialized!" + dbDescriptionComponent.toString());
	}
	@Override
	protected DBObject updateView() {
		DBObject dbObject = getLastDisplayedDBObject();
		if(dbObject instanceof DBObjectDatabase) {
			//log.info("database object was selected : " + ((DBObjectDatabase)dbObject).getName());
			dbDescriptionComponent.handleEvents(DBObjectEventType.DB_OBJECT_SELECTION_CHANGED);
		}
		return null;
	}
	@Override
	protected void updateHeader() {
		DBObject dbObject = getLastDisplayedDBObject();
		if(dbObject instanceof DBObjectDatabase) {
			getView().setHeaderText(((DBObjectDatabase)dbObject).getName());
		}
	}
	@Override
	protected DBOperationObject performOperation() {
		// TODO Auto-generated method stub
		return null;
	}

	class DatabaseDescriptionPanel extends DescriptionPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8699223617787665067L;
		
		public DatabaseDescriptionPanel() {
			super();
		}

		@Override
		public void handleEvents(String event) {
			if(event.equals(DBObjectEventType.DB_OBJECT_SELECTION_CHANGED)) {
				resetDBListModel(DatabaseDescriptionViewComponent.this.getLastDisplayedDBObject());
			}
		}

		@Override
		public void resetDBListModel(DBObject _dbObject) {
			DBObjectDatabase dbObject = null;
			if(_dbObject instanceof DBObjectDatabase) {
				dbObject = (DBObjectDatabase)_dbObject;
			}
			List<Object> listObjects = new ArrayList<Object>();
			listObjects.add(new DBListHeader(DBObjectDatabase.PRODUCT_NAME));
			if(dbObject != null) listObjects.add(new DBListItem(dbObject.getProductName()));
//				listObjects.add(new DBListHeader(DBObjectDatabase.PRODUCT_VERSION));
//				listObjects.add(new DBListItem(dbObject.getProductVersion()));
			listObjects.add(new DBListHeader(DBObjectDatabase.DRIVER_NAME));
			if(dbObject != null) listObjects.add(new DBListItem(dbObject.getDriverName()));
//				listObjects.add(new DBListHeader(DBObjectDatabase.DRIVER_VERSION));
//				listObjects.add(new DBListItem(dbObject.getDriverVersion()));
//				listObjects.add(new DBListHeader(DBObjectDatabase.URL));
//				listObjects.add(new DBListItem(dbObject.getUrl()));
			listObjects.add(new DBListHeader(DBObjectDatabase.USERNAME));
			if(dbObject != null) listObjects.add(new DBListItem(dbObject.getUsername()));
			listObjects.add(new DBListHeader(DBObjectDatabase.READONLY));
			if(dbObject != null) listObjects.add(new DBListItem(dbObject.isReadOnly()));
			listObjects.add(new DBListHeader(DBObjectDatabase.TABLES));
			if(dbObject != null) {
				for(DBObjectTable table : dbObject.getTables()) {
					listObjects.add(new DBListItem(table.getName()));
				}
			}
			dbList.setListObjects(listObjects);
			dbList.revalidate();
		}
	}
}
