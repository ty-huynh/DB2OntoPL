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
import dh.protege41.db2onto.event.dbobject.DBObjectEventType;
import dh.protege41.db2onto.event.dbobject.DBObjectTable;
import dh.protege41.db2onto.event.dboperation.DBOperationObject;
import dh.protege41.db2onto.tab.ui.DatabasePanel;
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
		// TODO Auto-generated method stub
		DBObject dbObject = getLastDisplayedDBObject();
		if(dbObject instanceof DBObjectColumn) {
			dbColumnDescriptionComponent.handleEvents(DBObjectEventType.DB_OBJECT_SELECTION_CHANGED);
//			log.info("column object was selected : " + ((DBObjectColumn)dbObject).getName());
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
		// TODO Auto-generated method stub
		return null;
	}

	class ColumnDescriptionPanel extends JPanel implements DatabasePanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4566186395503916013L;
		private JPanel contentPanel;
		private DBList dbList;
		
		public ColumnDescriptionPanel() {
			initComponents();
			attachComponents();
			initEventListeners();
		}
		@Override
		public void initComponents() {
			setLayout(new BorderLayout());
			
			contentPanel = new JPanel(new BorderLayout());
			dbList = new DBList();
			
			DBMutableListModel model = (DBMutableListModel)dbList.getModel();
			model.addElement(new DBListHeader("Foreign keys exported"));
			model.addElement(new DBListItem(new DBObject("FK_1")));
			model.addElement(new DBListItem(new DBObject("FK_2")));
			model.addElement(new DBListHeader("Primary keys exported"));
			model.addElement(new DBListItem(new DBObject("PK_1")));
			model.addElement(new DBListItem(new DBObject("PK_2")));
			model.add(1, new DBListItem(new DBObject("FK_3")));
		}

		@Override
		public void attachComponents() {
			contentPanel.add(new JScrollPane(dbList), BorderLayout.CENTER);
			add(contentPanel, BorderLayout.CENTER);
		}

		@Override
		public void initEventListeners() {
			dbList.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					// TODO Auto-generated method stub
					DBMutableListModel model = (DBMutableListModel)dbList.getModel();
					
					Object obj = dbList.getSelectedValue();
					if(obj instanceof DBObject) {
						log.info(" is db object : " + ((DBObject)obj).getName());
					} else if(obj != null) {
						log.info(obj.toString());
					} else {
						log.info("null pointer");
					}
				}
			});
		}

		@Override
		public void handleEvents(String event) {
			if(event.equals(DBObjectEventType.DB_OBJECT_SELECTION_CHANGED)) {
				resetDBListModel((DBObjectColumn)DatabaseColumnDescriptionViewComponent.this.getLastDisplayedDBObject());
			}
		}

		@Override
		public void handleEvents(int event) {
			// TODO Auto-generated method stub
			
		}
		
		public void resetDBListModel(DBObjectColumn dbObject) {
			List<Object> listObjects = new ArrayList<Object>();
			listObjects.add(new DBListHeader(DBObjectColumn.COL_PK));
			listObjects.add(new DBListItem(dbObject.isPrimaryKey()));
			listObjects.add(new DBListHeader(DBObjectColumn.COL_FK));
//			listObjects.add(new DBListHeader(DBObjectColumn.COL_DATA_TYPE));
//			listObjects.add(new DBListItem(dbObject.getDataType()));
			listObjects.add(new DBListHeader(DBObjectColumn.COL_TYPE_NAME));
			listObjects.add(new DBListItem(dbObject.getTypeName()));
			listObjects.add(new DBListHeader(DBObjectColumn.COL_SIZE));
			listObjects.add(new DBListItem(dbObject.getSize()));
			listObjects.add(new DBListHeader(DBObjectColumn.COL_NULL));
			listObjects.add(new DBListItem(dbObject.isNull()));
			dbList.setListObjects(listObjects);
			dbList.revalidate();
		}
	}

}
