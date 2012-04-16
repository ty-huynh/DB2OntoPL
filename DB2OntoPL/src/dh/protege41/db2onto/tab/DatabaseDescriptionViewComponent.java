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

	class DatabaseDescriptionPanel extends JPanel implements DatabasePanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8699223617787665067L;

		private JPanel contentPanel;
		private DBList dbList;
		
		public DatabaseDescriptionPanel() {
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
				resetDBListModel((DBObjectDatabase)DatabaseDescriptionViewComponent.this.getLastDisplayedDBObject());
			}
		}

		@Override
		public void handleEvents(int event) {
			
		}

		public void resetDBListModel(DBObjectDatabase dbObject) {
			List<Object> listObjects = new ArrayList<Object>();
			listObjects.add(new DBListHeader(DBObjectDatabase.PRODUCT_NAME));
			listObjects.add(new DBListItem(dbObject.getProductName()));
//			listObjects.add(new DBListHeader(DBObjectDatabase.PRODUCT_VERSION));
//			listObjects.add(new DBListItem(dbObject.getProductVersion()));
			listObjects.add(new DBListHeader(DBObjectDatabase.DRIVER_NAME));
			listObjects.add(new DBListItem(dbObject.getDriverName()));
//			listObjects.add(new DBListHeader(DBObjectDatabase.DRIVER_VERSION));
//			listObjects.add(new DBListItem(dbObject.getDriverVersion()));
//			listObjects.add(new DBListHeader(DBObjectDatabase.URL));
//			listObjects.add(new DBListItem(dbObject.getUrl()));
			listObjects.add(new DBListHeader(DBObjectDatabase.USERNAME));
			listObjects.add(new DBListItem(dbObject.getUsername()));
			listObjects.add(new DBListHeader(DBObjectDatabase.READONLY));
			listObjects.add(new DBListItem(dbObject.isReadOnly()));
			listObjects.add(new DBListHeader(DBObjectDatabase.TABLES));
			for(DBObjectTable table : dbObject.getTables()) {
				listObjects.add(new DBListItem(table.getName()));
			}
			dbList.setListObjects(listObjects);
			dbList.revalidate();
		}
	}
}
