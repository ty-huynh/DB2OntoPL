package dh.protege41.db2onto.tab;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.protege.editor.core.ui.util.CheckTableModel;

import dh.database.api.DBOperationImplement;
import dh.protege41.db2onto.event.dbobject.DBObject;
import dh.protege41.db2onto.event.dbobject.DBObjectEventType;
import dh.protege41.db2onto.event.dbobject.DBObjectTable;
import dh.protege41.db2onto.event.dboperation.DBOperationEventType;
import dh.protege41.db2onto.event.dboperation.DBOperationObject;
import dh.protege41.db2onto.tab.ui.DatabasePanel;
import dh.protege41.db2onto.tab.ui.util.table.DBCheckTable;

public class DatabaseRecordsViewComponent extends DatabaseViewComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8526846759403151802L;

	private final static Logger log = Logger.getLogger(DatabaseDetailViewComponent.class);
	
	private DatabaseRecordsPanel dbRecordsComponent;
	
	@Override
	protected void disposeOWLView() {
		// TODO Auto-generated method stub
		//remove all listeners here
	}

	@Override
	protected void initialiseOWLView() throws Exception {
		super.initialiseOWLView();
		setLayout(new BorderLayout());
		dbRecordsComponent = new DatabaseRecordsPanel();
		add(dbRecordsComponent, BorderLayout.CENTER);
		log.info("database records view component initialized!");
	}

	@Override
	protected DBObject updateView() {
		DBObject dbObject = getLastDisplayedDBObject();
		if(dbObject instanceof DBObjectTable) {
			dbRecordsComponent.handleEvents(DBObjectEventType.DB_OBJECT_SELECTION_CHANGED);
//			log.info("column object was selected : " + ((DBObjectColumn)dbObject).getName());
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
		dbRecordsComponent.handleEvents(getLastPerformedDBOperation().getOperation());
		return null;
	}
	
	class DatabaseRecordsPanel extends JPanel implements DatabasePanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7727632409839392427L;

		private JPanel topPanel;
		private JScrollPane centerPanel;
		private JPanel topLeftPanel;
		private JPanel topRightPanel;
		
		private DBCheckTable<String> table;
		private CheckTableModel<String> tableModel;
		
		private JButton btn1;
		private JButton btn2;
		private JButton btn3;
		
		private JButton btnQuery;
		private JTextField tfQuery;
		
		public DatabaseRecordsPanel() {
			initComponents();
			attachComponents();
			initEventListeners();
		}
		
		@Override
		public void initComponents() {
			// TODO Auto-generated method stub\
			setLayout(new BorderLayout());
			topPanel = new JPanel(new GridLayout(1, 2));
			topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			centerPanel = new JScrollPane(table);
			
			btn1 = new JButton("One");
			btn2 = new JButton("Two");
			btn3 = new JButton("Thr");
			
			btnQuery = new JButton("Query");
			tfQuery = new JTextField(40);	
		}

		@Override
		public void attachComponents() {
			// TODO Auto-generated method stub
			//add to top left panel
			topLeftPanel.add(btn1);
			topLeftPanel.add(btn2);
			topLeftPanel.add(btn3);
			
			//add to top right panel
			topRightPanel.add(tfQuery);
			topRightPanel.add(btnQuery);
			
			//add to top panel
			topPanel.add(topLeftPanel);
			topPanel.add(topRightPanel);
			
			//add to main panel
			add(topPanel, BorderLayout.NORTH);
			add(centerPanel, BorderLayout.CENTER);
		}

		@Override
		public void initEventListeners() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void handleEvents(int event) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void handleEvents(String event) {
			// TODO Auto-generated method stub
			if(event.equals(DBObjectEventType.DB_OBJECT_SELECTION_CHANGED)) {
				rebuildTable((DBObjectTable)getLastDisplayedDBObject());
				log.info("records component handle event selection changed");
			} else if(event.equals(DBOperationEventType.DB_OPERATION_DISCONNECT)) {
				log.info("Error: the connection was lost. Try to connect again");
			}
		}
		
		public void rebuildTable(DBObjectTable dbObject) {
			try {
				DBOperationImplement opImpl = getDBOperationImpl();
				if(opImpl != null) {
					
					ResultSet rs = opImpl.exeQuery("SELECT * FROM " + dbObject.getName());
					ResultSetMetaData rsmd = rs.getMetaData();
					int totalCols = rsmd.getColumnCount();
					List<String> colNames = new ArrayList<String>();
					
					//init vector to store data
					Vector<ArrayList<String>> data = new Vector<ArrayList<String>>();
					for(int i = 1; i <= totalCols; i ++){
						//rememeber: in ResultSet columns start 1 position
						colNames.add(rsmd.getColumnName(i));
						data.add(new ArrayList<String>());
					}
//					table = new DBCheckTable<String>(colNames.get(0));
					while(rs.next()) {
						for(int k = 1; k <= totalCols; k++) {
							//rememeber: in ResultSet columns start 1 position
							data.get(k - 1).add(rs.getString(k));
						}
					}
//					log.info("data size: " + data.size() + " array size: " + data.get(0).size());
					//update table
					remove(centerPanel);
					//first column is exception
					table = new DBCheckTable<String>(colNames.get(0));
					tableModel = table.getModel();
					tableModel.setData(data.get(0), false);
					for(int r = 1; r < totalCols; r++) {
						tableModel.addColumn(colNames.get(r), data.get(r).toArray());
					}
					_initTableListener();
					centerPanel = new JScrollPane(table);
					add(centerPanel, BorderLayout.CENTER);
					revalidate();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		private void _initTableListener() {
			table.addMouseMotionListener(new MouseMotionListener() {
				@Override
				public void mouseMoved(MouseEvent e) {
					// TODO Auto-generated method stub
					Point pt = table.getMousePosition();
					if(pt != null) {
						int row = table.rowAtPoint(pt);
						int col = table.columnAtPoint(pt);
						if(!(col == table.getLastColumn() && row == table.getLastRow()) && (col != 0)) {
							table.setLastColumn(col);
							table.setLastRow(row);
							String content = tableModel.getValueAt(row, col).toString();
							table.setTooltip(table.getColumnName(col), content);
//							setToolTipText(content);
							log.info(content);
						}
					}
				}
				@Override
				public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}
}
