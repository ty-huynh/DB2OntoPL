package dh.protege41.db2onto.tab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import dh.database.api.DBOperationImplement;
import dh.protege41.db2onto.event.dbobject.DBObject;
import dh.protege41.db2onto.event.dbobject.DBObjectColumn;
import dh.protege41.db2onto.event.dbobject.DBObjectDatabase;
import dh.protege41.db2onto.event.dbobject.DBObjectForeignKey;
import dh.protege41.db2onto.event.dbobject.DBObjectPrimaryKey;
import dh.protege41.db2onto.event.dbobject.DBObjectTable;
import dh.protege41.db2onto.event.dboperation.DBOperationEventType;
import dh.protege41.db2onto.event.dboperation.DBOperationObject;
import dh.protege41.db2onto.tab.ui.DatabasePanel;
import dh.protege41.db2onto.tab.ui.util.DBTreeNode;
import dh.protege41.db2onto.tab.ui.util.JTreeComponent;
import dh.protege41.db2onto.tab.ui.util.JTreeNodeVector;

public class DatabaseDetailViewComponent extends DatabaseViewComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1129646544078523653L;
	
	private final static Logger log = Logger.getLogger(DatabaseDetailViewComponent.class);
	
	private DatabaseDetailPanel databaseDetailComponent;
	
	private DBOperationImplement dbOperationImpl;
	private DBObjectDatabase databaseInfos;
	
	@Override
	protected void disposeOWLView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initialiseOWLView() throws Exception {
		// TODO Auto-generated method stub
		super.initialiseOWLView();
		setLayout(new BorderLayout());
		databaseDetailComponent = new DatabaseDetailPanel();
		add(databaseDetailComponent, BorderLayout.CENTER);
		log.info("database detail view component initialized!");
		
	}

	@Override
	protected DBObject updateView() {
		
		return null;
	}
	@Override
	protected void updateHeader() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected DBOperationObject performOperation() {
		// TODO Auto-generated method stub
		databaseDetailComponent.handleEvents(getLastPerformedDBOperation().getOperation());
		return null;
	}
	
	
	class DatabaseDetailPanel extends JPanel implements DatabasePanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private JPanel topPanel;
		private JPanel centerPanel;
		
		private JTreeComponent dbTree;
		private JScrollPane scroll;
		
		private JButton btn1;
		private JButton btn2;
		private JButton btn3;
		
		private TreeSelectionListener treeSelectionListener;
		
		public DatabaseDetailPanel() {
			initComponents();
			attachComponents();
			initEventListeners();
		}
		
		
		@Override
		public void initComponents() {
			// TODO Auto-generated method stub
			setLayout(new BorderLayout());
			topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			centerPanel = new JPanel(new GridLayout(1, 1));
			centerPanel.setBackground(Color.WHITE);
			
			Vector<String> v1 = new JTreeNodeVector<String>("TWO", new String[] {"e1", "e2", "e3"});
			Vector<Object> v2 = new JTreeNodeVector<Object>("THREE", new Object[] {"f1", "f2", "f3"});
			v2.add(System.getProperties());
			Object nodes[] = {v1, v2};
			Vector<Object> v = new JTreeNodeVector<Object>("ROOT", nodes);
			Vector<Object> rootVector = new JTreeNodeVector<Object>("ROOT", new Object[] {v});
			
			dbTree = new JTreeComponent(rootVector);
			scroll = new JScrollPane(dbTree);
			
			btn1 = new JButton("One");
			btn2 = new JButton("Two");
			btn3 = new JButton("Thr");
		}
		
		@Override
		public void attachComponents() {
			// TODO Auto-generated method stub
			//add to top panel
			topPanel.add(btn1);
			topPanel.add(btn2);
			topPanel.add(btn3);
			
			//add to center panel
			centerPanel.add(scroll);
			
			//add to main panel
			add(topPanel, BorderLayout.NORTH);
			add(centerPanel, BorderLayout.CENTER);
		}


		@Override
		public void initEventListeners() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void handleEvents(String event) {
			if(event.equalsIgnoreCase(DBOperationEventType.DB_OPERATION_CONNECTED)) {
				dbOperationImpl = DB2OntoPLWorkspaceTab.getDBOperationImplement();
				processDatabaseMetaData();
				buildDatabaseTree();
			}
//			log.info("Event: " + event);
		}
		/**
		 * Process database meta data
		 */
		public void processDatabaseMetaData() {
			try {
				DatabaseMetaData meta = dbOperationImpl.getDatabaseMetaData();
				databaseInfos = getDatabaseInfos();
				databaseInfos.setName(meta.getDatabaseProductName());
				databaseInfos.setProductName(meta.getDatabaseProductName());
				databaseInfos.setProductVersion(meta.getDatabaseProductVersion());
				databaseInfos.setDriverName(meta.getDriverName());
				databaseInfos.setDriverVersion(meta.getDriverVersion());
				databaseInfos.setUrl(meta.getURL());
				databaseInfos.setUsername(meta.getUserName());
				databaseInfos.setReadOnly(meta.isReadOnly());
				
				ResultSet rsTables = dbOperationImpl.getTables();
				while(rsTables.next()) {
					String tableName = rsTables.getString(DBObjectTable.TABLE_NAME);
					if(DBObjectTable.EXCEPT_TABLE.equalsIgnoreCase(tableName))
						continue;
					String tableCat = rsTables.getString(DBObjectTable.TABLE_CAT);
					String tableSchem = rsTables.getString(DBObjectTable.TABLE_SCHEM);
					String tableType = rsTables.getString(DBObjectTable.TABLE_TYPE);
					DBObjectTable table = new DBObjectTable(tableName, tableCat, tableSchem, tableType);
				
					//get columns
					ResultSet rsColumns = dbOperationImpl.getColumns(tableName);
					while(rsColumns.next()) {
						DBObjectColumn column = new DBObjectColumn(
								rsColumns.getString(DBObjectColumn.COLUMN_NAME),
								rsColumns.getString(DBObjectColumn.TYPE_NAME),
								rsColumns.getString(DBObjectColumn.NULLABLE));
						table.addColumn(column);
					}
					databaseInfos.addTable(table);
				}
				//get keys
				for(DBObjectTable obj : databaseInfos.getTables()) {
					//get primary keys
					ResultSet rspk = meta.getPrimaryKeys(null, null, obj.getName());
					while(rspk.next()) {
						DBObjectPrimaryKey pk = new DBObjectPrimaryKey(rspk.getString(DBObjectPrimaryKey.PK_NAME), rspk.getString(DBObjectPrimaryKey.COLUMN_NAME), rspk.getString(DBObjectPrimaryKey.TABLE_NAME));
						databaseInfos.getTableByName(rspk.getString(DBObjectPrimaryKey.TABLE_NAME)).addPrimaryKey(pk);
						databaseInfos.getTableByName(pk.getTable()).getColumnByName(pk.getColumn()).setPrimaryKey(true);
					}
					//get foreign keys
					ResultSet rsek = meta.getExportedKeys(null, null, obj.getName());
					while(rsek.next()) {
						DBObjectForeignKey fk = new DBObjectForeignKey(rsek.getString(DBObjectForeignKey.FK_NAME), rsek.getString(DBObjectForeignKey.FKTABLE_NAME), rsek.getString(DBObjectPrimaryKey.PKTABLE_NAME), rsek.getString(DBObjectForeignKey.FKCOLUMN_NAME), rsek.getString(DBObjectPrimaryKey.PKCOLUMN_NAME));
						databaseInfos.getTableByName(rsek.getString(DBObjectForeignKey.FKTABLE_NAME)).addForeignKey(fk);
						databaseInfos.getTableByName(fk.getFKTable()).getColumnByName(fk.getFKColumn()).setForeignKey(true);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/**
		 * Create database info tree when connected to database
		 */
		public void buildDatabaseTree() {
			
			DBTreeNode root = new DBTreeNode(databaseInfos);
			for(DBObjectTable table : databaseInfos.getTables()) {
				DBTreeNode tableNode = new DBTreeNode(table);
				for(DBObjectColumn col : table.getColumns()) {
					tableNode.add(new DBTreeNode(col));
				}
				root.add(tableNode);
			}
			centerPanel.remove(scroll);
			dbTree.removeTreeSelectionListener(treeSelectionListener);
			dbTree.clear();
			dbTree = new JTreeComponent(root);
			_initTreeSelectionEventHandler();
			scroll = new JScrollPane(dbTree);
			centerPanel.add(scroll);
			centerPanel.revalidate();
			centerPanel.repaint();
		}
		
		private void _initTreeSelectionEventHandler() {
			treeSelectionListener = new TreeSelectionListener() {
				
				@Override
				public void valueChanged(TreeSelectionEvent event) {
					// TODO Auto-generated method stub
					//get all nodes whose selection status has changed
					TreePath[] paths = event.getPaths();
					//
					for(TreePath obj : paths) {
						if(event.isAddedPath(obj)) {
							DefaultMutableTreeNode node = (DefaultMutableTreeNode) obj.getLastPathComponent();
//							log.info(((DBObject)node.getUserObject()).getType());
							DatabaseViewComponent.setGlobalSelectionObject((DBObject)node.getUserObject());
						} else {
							DefaultMutableTreeNode node = (DefaultMutableTreeNode) obj.getLastPathComponent();
//							log.info(((DBObject)node.getUserObject()).getName());
						}
					}
					
				}
			};
			dbTree.addTreeSelectionListener(treeSelectionListener);
			log.info("setup listener for tree selection event");
		}
		
		public void setDBTree(JTreeComponent newTree) {
			dbTree = newTree;
		}
		public JTreeComponent getDBTree() {
			return dbTree;
		}

		@Override
		public void handleEvents(int event) {
			// TODO Auto-generated method stub
			
		}
	}
}
