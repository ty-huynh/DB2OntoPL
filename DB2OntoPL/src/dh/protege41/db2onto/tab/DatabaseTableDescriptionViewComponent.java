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
import dh.protege41.db2onto.tab.DatabaseDescriptionViewComponent.DatabaseDescriptionPanel;
import dh.protege41.db2onto.tab.ui.DatabasePanel;
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
	
	class TableDescriptionPanel extends JPanel implements DatabasePanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4152218576621382002L;
		private JPanel contentPanel;
		private DBList dbList;
		
		public TableDescriptionPanel() {
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
				resetDBListModel((DBObjectTable)DatabaseTableDescriptionViewComponent.this.getLastDisplayedDBObject());
			}
		}

		@Override
		public void handleEvents(int event) {
			// TODO Auto-generated method stub
			
		}
		
		public void resetDBListModel(DBObjectTable dbObject) {
			List<Object> listObjects = new ArrayList<Object>();
			listObjects.add(new DBListHeader(DBObjectTable.COLUMNS));
			listObjects.add(new DBListHeader(DBObjectTable.CATEGORY));
			listObjects.add(new DBListItem(dbObject.getCategory()));
			listObjects.add(new DBListHeader(DBObjectTable.SCHEM));
			listObjects.add(new DBListItem(dbObject.getSchem()));
			listObjects.add(new DBListHeader(DBObjectTable.TYPE));
			listObjects.add(new DBListItem(dbObject.getType()));
			dbList.setListObjects(listObjects);
			dbList.revalidate();
		}
	}

}
