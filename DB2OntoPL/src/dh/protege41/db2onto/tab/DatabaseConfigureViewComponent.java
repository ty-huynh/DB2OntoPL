package dh.protege41.db2onto.tab;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import dh.database.api.DBEnumType;
import dh.database.api.DBOperationImplement;
import dh.database.api.exception.DHConnectionException;
import dh.protege41.db2onto.event.dbobject.DBObject;
import dh.protege41.db2onto.event.dboperation.DBOperationEventType;
import dh.protege41.db2onto.event.dboperation.DBOperationObject;
import dh.protege41.db2onto.tab.ui.DatabasePanel;

public class DatabaseConfigureViewComponent extends DatabaseViewComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static Logger log = Logger.getLogger(DatabaseConfigureViewComponent.class);
	
	private DatabaseConfigurePanel databaseConfigureComponent;
	private boolean connected = false;
	
	@Override
	protected void disposeOWLView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initialiseOWLView() throws Exception {
		super.initialiseOWLView();
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		databaseConfigureComponent = new DatabaseConfigurePanel();
		add(databaseConfigureComponent, BorderLayout.CENTER);
		log.info("database configure view component initialized!");
	}

	@Override
	protected DBObject updateView() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void updateHeader() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected DBOperationObject performOperation() {
		// TODO Auto-generated method stub
		databaseConfigureComponent.handleEvents(getLastPerformedDBOperation().getOperation());
		return null;
	}
	
	
	class DatabaseConfigurePanel extends JPanel implements DatabasePanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private JPanel panelCenter;
		private JPanel panelBottom;
		
		private JComboBox cbbDBType;
		
		private JTextField tfDriver;
		private JTextField tfDBName;
		private JTextField tfHost;
		private JTextField tfPort;
		private JTextField tfUsername;
		private JTextField tfPassword;
		
		private JButton btnChange;
		private JButton btnConnect;
		private JButton btnCancel;
		
		public DatabaseConfigurePanel() {
			initComponents();
			attachComponents();
			initEventListeners();
		}
		
		public void initComponents() {
			setLayout(new BorderLayout());
			panelCenter = new JPanel(new GridLayout(14, 0, 5, 5));
			panelBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
			
			cbbDBType = new JComboBox(DBEnumType.getAllDBTypes());
			cbbDBType.setSelectedIndex(0);
			
			tfDriver = new JTextField(DBEnumType.getDBEnumByType((String)cbbDBType.getSelectedItem()).getDriver(), 30);
			
			tfDBName = new JTextField("School", 30);
			tfHost = new JTextField("localhost", 30);
			tfPort = new JTextField("1433", 30);
			tfUsername = new JTextField("sa", 30);
			tfPassword = new JTextField("12345", 30);
			
			btnChange = new JButton("Change");
			btnConnect = new JButton("Connect");
			btnCancel = new JButton("Cancel");
		}
		
		public void attachComponents() {
			//attach components to center panel
			panelCenter.add(new JLabel("Database Type"));
			panelCenter.add(cbbDBType);
			panelCenter.add(new JLabel("JDBC Driver Class Name (required)"));
			panelCenter.add(tfDriver);
			panelCenter.add(new JLabel("Database Name (ex. access: c:\\folder\\db.mdb)"));
			panelCenter.add(tfDBName);
			panelCenter.add(new JLabel("Host (work for mysql, sqlserver)"));
			panelCenter.add(tfHost);
			panelCenter.add(new JLabel("Port (work for mysql, sqlserver)"));
			panelCenter.add(tfPort);
			panelCenter.add(new JLabel("Username"));
			panelCenter.add(tfUsername);
			panelCenter.add(new JLabel("Password"));
			panelCenter.add(tfPassword);
			
			//attach components to bottom panel
			panelBottom.add(btnChange);
			panelBottom.add(btnConnect);
			panelBottom.add(btnCancel);
			
			//attach components to main panel
			add(panelCenter, BorderLayout.CENTER);
			add(panelBottom, BorderLayout.SOUTH);
			
		}
		
		
		@Override
		public void initEventListeners() {
			// TODO Auto-generated method stub
			cbbDBType.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					tfDriver.setText(DBEnumType.getDBEnumByType((String)cbbDBType.getSelectedItem()).getDriver());
				}
			});
			
			btnChange.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
//					handleEvents(DBOperationEventType.DB_OPERATION_CHANGE);
					DatabaseConfigureViewComponent.setGlobalDBOperationObject(new DBOperationObject(DBOperationEventType.DB_OPERATION_CHANGE));
				}
			});
			
			btnConnect.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					handleEvents(DBOperationEventType.DB_OPERATION_CONNECT);
				}
			});
			
			btnCancel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					handleEvents(DBOperationEventType.DB_OPERATION_CANCEL);
				}
			});
		}
		

		@Override
		public void handleEvents(String event) {
			// TODO Auto-generated method stub
			if(event.equals(DBOperationEventType.DB_OPERATION_CONNECT)) {
				Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						setupConnectionDatabase();
					}
				});
				t.start();
			}
			
			putMessages("Event " + event);
		}
		
		public void putMessages(String message) {
			JOptionPane.showMessageDialog(null, message);
		}
		
		//should use connection pooler
		public void setupConnectionDatabase() {
			String dbType = (String)cbbDBType.getSelectedItem();
			String driver = tfDriver.getText().trim();
			String databaseName = tfDBName.getText().trim();
			String user = tfUsername.getText().trim();
			String pass = tfPassword.getText().trim();
			
			String url = "";
			if(dbType.equalsIgnoreCase(DBEnumType.MSACCESS.getType())) {
				url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + databaseName;
			} else if(dbType.equalsIgnoreCase(DBEnumType.SQLSERVER.getType())) {
				url = "jdbc:sqlserver://" + tfHost.getText().trim() + ":" + tfPort.getText().trim() + ";databaseName=" + databaseName + ";selectMethod=cursor";
			}
			if(DB2OntoPLWorkspaceTab.getDBOperationImplement() != null) {
				try {
					DB2OntoPLWorkspaceTab.getDBOperationImplement().close();
				} catch (SQLException e) {
					log.info("can not close connection");
				}
				DB2OntoPLWorkspaceTab.setDBOperationImplement(null);
			}
			DB2OntoPLWorkspaceTab.setDBOperationImplement(new DBOperationImplement(driver, url, databaseName, user, pass));
			try {
				DB2OntoPLWorkspaceTab.getDBOperationImplement().createConnection();
				DB2OntoPLWorkspaceTab.setConnectStatus(true);
				setGlobalDBOperationObject(new DBOperationObject(DBOperationEventType.DB_OPERATION_CONNECTED));
				log.info("connected");
			} catch (DHConnectionException e) {
				DB2OntoPLWorkspaceTab.setConnectStatus(false);
				log.info("can not connect");
			}
		}

		@Override
		public void handleEvents(int event) {
			// TODO Auto-generated method stub
			
		}
	}//end class DatabaseConfigurePanel
	
}
