package dh.protege41.db2onto.tab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.protege.editor.core.ui.util.CheckTableModel;
import org.protege.editor.core.ui.util.JOptionPaneEx;

import dh.database.api.DBOperationImplement;
import dh.protege41.db2onto.common.DB2OntoPLConstants;
import dh.protege41.db2onto.event.dbobject.DBObject;
import dh.protege41.db2onto.event.dbobject.DBObjectEventType;
import dh.protege41.db2onto.event.dbobject.DBObjectTable;
import dh.protege41.db2onto.event.dboperation.DBOperationEventType;
import dh.protege41.db2onto.event.dboperation.DBOperationObject;
import dh.protege41.db2onto.tab.ui.DatabaseExEditorPanel;
import dh.protege41.db2onto.tab.ui.DatabasePanel;
import dh.protege41.db2onto.tab.ui.util.DBIcons;
import dh.protege41.db2onto.tab.ui.util.FontUtility;
import dh.protege41.db2onto.tab.ui.util.dialog.DialogUtility;
import dh.protege41.db2onto.tab.ui.util.table.DBCheckTable;

public class DatabaseRecordsViewComponent extends DatabaseViewComponent {
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
		
		private JButton btnCreateIndi;
		private JLabel lbConnectionStatus;
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
			
			btnCreateIndi = new JButton("Create Individuals");
			FontUtility.setSmallLabel(btnCreateIndi, Font.BOLD);
			btnCreateIndi.setIcon(DBIcons.getIcon(DBIcons.ICON_CREATE_INDI_BUTTON));
			lbConnectionStatus = new JLabel(DB2OntoPLConstants.STATUS_DISCONNECTED);
			FontUtility.setMediumLabel(lbConnectionStatus, Font.BOLD);
			
			btnQuery = new JButton("Query");
			FontUtility.setSmallLabel(btnQuery, Font.BOLD);
			btnQuery.setIcon(DBIcons.getIcon(DBIcons.ICON_QUERY_BUTTON));
			tfQuery = new JTextField(40);
			if(getDBOperationImpl() != null)
				enableConnected();
			else enableDisconnected();
		}

		@Override
		public void attachComponents() {
			// TODO Auto-generated method stub
			//add to top left panel
			topLeftPanel.add(btnCreateIndi);
			topLeftPanel.add(lbConnectionStatus);
			
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
			btnCreateIndi.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					_handleCreateIndividuals();
				}
			});
			btnQuery.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(tfQuery.getText() != null && !"".equals(tfQuery.getText().trim())) 
						handleQuery(tfQuery.getText().trim());
					else 
						DialogUtility.showMessages("Input your query first.");
				}
			});
		}

		@Override
		public void handleEvents(String event) {
			// TODO Auto-generated method stub
			if(event.equals(DBObjectEventType.DB_OBJECT_SELECTION_CHANGED)) {
				if(getLastDisplayedDBObject() instanceof DBObjectTable) {
					handleSelectTable((DBObjectTable)getLastDisplayedDBObject());
				}
//				log.info("records component handle event selection changed");
			} else if (DBOperationEventType.DB_OPERATION_CONNECTED.equals(event)) {
				enableConnected();
			} else if(event.equals(DBOperationEventType.DB_OPERATION_DISCONNECTED)) {
				enableDisconnected();
//				log.info("Error: the connection was lost. Try to connect again");
			} else if(DBOperationEventType.DB_OPERATION_DISCONNECT.equals(event)) {
				remove(centerPanel);
				revalidate();
			} else if(DBOperationEventType.DB_OPERATION_CONNECT.equals(event)) {
				remove(centerPanel);
				revalidate();
			}
		}
		
		public void handleQuery(String query) {
			try {
				getView().setHeaderText("Query");
				DBOperationImplement opImpl = getDBOperationImpl();
				if(opImpl != null) {
					ResultSet rs = opImpl.exeQuery(query);
					rebuildTable(rs);
				}
			} catch (Exception e) {
				DialogUtility.showError("Bad query.Please check your query string");
			}
		}
		
		public void handleSelectTable(DBObjectTable dbTable) {
			try {
				DBOperationImplement opImpl = getDBOperationImpl();
				if(opImpl != null) {
					ResultSet rs = opImpl.exeQuery("SELECT * FROM " + dbTable.getName());
					rebuildTable(rs);
				}
			} catch (Exception e) {
				DialogUtility.showError(e.getMessage());
			}
		}
		
		public void rebuildTable(ResultSet rs) throws SQLException {
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
			while(rs.next()) {
				for(int k = 1; k <= totalCols; k++) {
					//rememeber: in ResultSet columns start 1 position
					data.get(k - 1).add(rs.getString(k));
				}
			}
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

		private void _initTableListener() {
			table.addMouseMotionListener(new MouseMotionListener() {
				@Override
				public void mouseMoved(MouseEvent e) {
					_handleMouseMoved();
				}
				@Override
				public void mouseDragged(MouseEvent e) {
				}
			});
		}

		private void _handleMouseMoved() {
			Point pt = table.getMousePosition();
			if(pt != null) {
				int row = table.rowAtPoint(pt);
				int col = table.columnAtPoint(pt);
				if(!(col == table.getLastColumn() && row == table.getLastRow()) && (col != 0)) {
					table.setLastColumn(col);
					table.setLastRow(row);
					String content = tableModel.getValueAt(row, col).toString();
					table.setTooltip(table.getColumnName(col), content);
				}
			}
		}

		private void _handleCreateIndividuals() {
			if(table == null || table.getSelectedRecords().size() == 0) {
				DialogUtility.showMessages(DB2OntoPLConstants.MESSAGE_TABLE_NULL_CONTENT);
				return;
			}
			DatabaseExEditorPanel panel = new DatabaseExEditorPanel(
					DatabaseRecordsViewComponent.this.getOWLEditorKit(),
					table.getAllColumnsIdentifiers(), 
					table.getSelectedRecords());
			int choice = DialogUtility.showConfirmDialog(null, "Create individuals", panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, false);
			if(choice == JOptionPane.OK_OPTION) {
				panel.handleEvents(DBOperationEventType.DB_OPERATION_CREATE_INDIVIDUAL);
			} else if(choice == JOptionPane.CANCEL_OPTION) {
//				log.info("Cancel selected");
			}
		}

		private void enableDisconnected() {
			tfQuery.setEditable(false);
			btnQuery.setEnabled(false);
			btnCreateIndi.setEnabled(false);
			lbConnectionStatus.setText(DB2OntoPLConstants.STATUS_DISCONNECTED);
			FontUtility.setColorForLabel(lbConnectionStatus, DB2OntoPLConstants.STATUS_DISCONNECTED_COLOR);
			this.revalidate();
		}

		private void enableConnected() {
			tfQuery.setEditable(true);
			btnQuery.setEnabled(true);
			btnCreateIndi.setEnabled(true);
			lbConnectionStatus.setText(DB2OntoPLConstants.STATUS_CONNECTED);
			FontUtility.setColorForLabel(lbConnectionStatus, DB2OntoPLConstants.STATUS_CONNECTED_COLOR);
			this.revalidate();
		}
	}
}
