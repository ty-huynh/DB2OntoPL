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
import dh.protege41.db2onto.event.dboperation.DBOperationEventType;
import dh.protege41.db2onto.event.dboperation.DBOperationObject;
import dh.protege41.db2onto.tab.ui.DatabasePanel;
import dh.protege41.db2onto.tab.ui.DescriptionPanel;
import dh.protege41.db2onto.tab.ui.util.list.DBList;
import dh.protege41.db2onto.tab.ui.util.list.DBListHeader;
import dh.protege41.db2onto.tab.ui.util.list.DBListItem;
import dh.protege41.db2onto.tab.ui.util.list.DBMutableListModel;

public class DatabaseColumnDescriptionViewComponent extends DatabaseViewComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(DatabaseColumnDescriptionViewComponent.class);
	private ColumnDescriptionPanel dbColumnDescriptionComponent;
	@Override
	protected void disposeOWLView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initialiseOWLView() throws Exception {
		super.initialiseOWLView();
		setLayout(new BorderLayout());
		dbColumnDescriptionComponent = new ColumnDescriptionPanel();
		add(dbColumnDescriptionComponent, BorderLayout.CENTER);
		log.info("database column description view component initialized!");
	}
	
	@Override
	protected DBObject updateView() {
		DBObject dbObject = getLastDisplayedDBObject();
		if(dbObject != null && dbObject instanceof DBObjectColumn) {
			dbColumnDescriptionComponent.handleEvents(DBObjectEventType.DB_OBJECT_SELECTION_CHANGED);
		}
		return null;
	}
	@Override
	protected void updateHeader() {
		DBObject dbObject = getLastDisplayedDBObject();
		if(dbObject instanceof DBObjectColumn) {
			getView().setHeaderText(dbObject.getName());
		}
	}
	@Override
	protected DBOperationObject performOperation() {
		dbColumnDescriptionComponent.handleEvents(getLastPerformedDBOperation().getOperation());
		return null;
	}

	class ColumnDescriptionPanel extends DescriptionPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4566186395503916013L;
		
		public ColumnDescriptionPanel() {
			super();
		}
		
		@Override
		public void handleEvents(String event) {
			if(event.equals(DBObjectEventType.DB_OBJECT_SELECTION_CHANGED)) {
				resetDBListModel(DatabaseColumnDescriptionViewComponent.this.getLastDisplayedDBObject());
			} else if (DBOperationEventType.DB_OPERATION_DISCONNECTED.equals(event)) {
				resetDBListModel(null);
			}
		}

		@Override
		public void resetDBListModel(DBObject _dbObject) {
			DBObjectColumn dbObject = null;
			if(_dbObject instanceof DBObjectColumn) {
				dbObject = (DBObjectColumn)_dbObject;
			}
			List<Object> listObjects = new ArrayList<Object>();
			listObjects.add(new DBListHeader(DBObjectColumn.COL_PK));
			if(dbObject != null) listObjects.add(new DBListItem(dbObject.getPrimaryString()));
			listObjects.add(new DBListHeader(DBObjectColumn.COL_FK));
			if(dbObject != null) listObjects.add(new DBListItem(dbObject.getForeignString()));
//				listObjects.add(new DBListHeader(DBObjectColumn.COL_DATA_TYPE));
//				listObjects.add(new DBListItem(dbObject.getDataType()));
			listObjects.add(new DBListHeader(DBObjectColumn.COL_TYPE_NAME));
			if(dbObject != null) listObjects.add(new DBListItem(dbObject.getTypeName()));
//				listObjects.add(new DBListHeader(DBObjectColumn.COL_SIZE));
//				listObjects.add(new DBListItem(dbObject.getSize()));
			listObjects.add(new DBListHeader(DBObjectColumn.COL_NULLABLE));
			if(dbObject != null) listObjects.add(new DBListItem(dbObject.getNullableString()));
			dbList.setListObjects(listObjects);
			dbList.revalidate();
		}
	}

}
