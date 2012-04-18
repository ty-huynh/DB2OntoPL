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
import dh.protege41.db2onto.event.dbobject.DBObjectColumn;
import dh.protege41.db2onto.event.dbobject.DBObjectDatabase;
import dh.protege41.db2onto.event.dbobject.DBObjectEventType;
import dh.protege41.db2onto.event.dbobject.DBObjectTable;
import dh.protege41.db2onto.event.dboperation.DBOperationObject;
import dh.protege41.db2onto.tab.DatabaseDescriptionViewComponent.DatabaseDescriptionPanel;
import dh.protege41.db2onto.tab.ui.DatabasePanel;
import dh.protege41.db2onto.tab.ui.DescriptionPanel;
import dh.protege41.db2onto.tab.ui.util.list.DBList;
import dh.protege41.db2onto.tab.ui.util.list.DBListHeader;
import dh.protege41.db2onto.tab.ui.util.list.DBListItem;
import dh.protege41.db2onto.tab.ui.util.list.DBMutableListModel;

public class DatabaseTableDescriptionViewComponent extends DatabaseViewComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(DatabaseTableDescriptionViewComponent.class);
	private TableDescriptionPanel dbTableDescriptionComponent;
	@Override
	protected void disposeOWLView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initialiseOWLView() throws Exception {
		super.initialiseOWLView();
		setLayout(new BorderLayout());
		dbTableDescriptionComponent = new TableDescriptionPanel();
		add(dbTableDescriptionComponent, BorderLayout.CENTER);
		log.info("database table description view component initialized!");
	}
	
	@Override
	protected DBObject updateView() {
		DBObject dbObject = getLastDisplayedDBObject();
		if(dbObject instanceof DBObjectTable) {
			dbTableDescriptionComponent.handleEvents(DBObjectEventType.DB_OBJECT_SELECTION_CHANGED);
//			log.info("table object was selected : " + ((DBObjectTable)dbObject).getName());
		}
		return null;
	}
	@Override
	protected void updateHeader() {
		DBObject dbObject = getLastDisplayedDBObject();
		if(dbObject instanceof DBObjectTable) {
			getView().setHeaderText(dbObject.getName());
		}
	}
	@Override
	protected DBOperationObject performOperation() {
		// TODO Auto-generated method stub
		return null;
	}
	
	class TableDescriptionPanel extends DescriptionPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4152218576621382002L;
		
		public TableDescriptionPanel() {
			super();
		}

		@Override
		public void handleEvents(String event) {
			if(event.equals(DBObjectEventType.DB_OBJECT_SELECTION_CHANGED)) {
				resetDBListModel(DatabaseTableDescriptionViewComponent.this.getLastDisplayedDBObject());
			}
		}

		@Override
		public void resetDBListModel(DBObject _dbObject) {
			DBObjectTable dbObject = null;
			if(_dbObject instanceof DBObjectTable) {
				dbObject = (DBObjectTable)_dbObject;
			}
			List<Object> listObjects = new ArrayList<Object>();
			listObjects.add(new DBListHeader(DBObjectTable.CATEGORY));
			if(dbObject != null) listObjects.add(new DBListItem(dbObject.getCategory()));
			listObjects.add(new DBListHeader(DBObjectTable.SCHEM));
			if(dbObject != null) listObjects.add(new DBListItem(dbObject.getSchem()));
			listObjects.add(new DBListHeader(DBObjectTable.TYPE));
			if(dbObject != null) listObjects.add(new DBListItem(dbObject.getType()));
			listObjects.add(new DBListHeader(DBObjectTable.COLUMNS));
			if(dbObject != null) for(DBObjectColumn col : dbObject.getColumns()) {
				listObjects.add(new DBListItem(col.getName()));
			}
			dbList.setListObjects(listObjects);
			dbList.revalidate();
		}
	}

}
