package dh.protege41.db2onto.tab;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import dh.database.api.DBEnumType;
import dh.database.api.DBOperationImplement;
import dh.database.api.exception.DHConnectionException;
import dh.protege41.db2onto.event.dbobject.DBObject;
import dh.protege41.db2onto.event.dboperation.DBOperationEventType;
import dh.protege41.db2onto.event.dboperation.DBOperationObject;
import dh.protege41.db2onto.tab.ui.DatabasePanel;
import dh.protege41.db2onto.tab.ui.util.DBIcons;
import dh.protege41.db2onto.tab.ui.util.FontUtility;
import dh.protege41.db2onto.tab.ui.util.dialog.DialogUtility;
import dh.protege41.db2onto.tab.ui.util.form.FormUtility;
import dh.protege41.db2onto.tab.ui.util.panel.PanelUtil;

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
		private JPasswordField tfPassword;
		
		private JButton btnChange;
		private JButton btnConnect;
		private JButton btnCancel;
		
		private FormUtility formUtil = new FormUtility();
		public DatabaseConfigurePanel() {
			initComponents();
			attachComponents();
			initEventListeners();
		}
		
		public void initComponents() {
			setLayout(new BorderLayout());
			panelCenter = new JPanel(new GridBagLayout());
			panelBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
			
			cbbDBType = new JComboBox(DBEnumType.getAllDBTypes());
			cbbDBType.setSelectedItem(DBEnumType.SQLSERVER.getType());
			
			tfDriver = new JTextField(DBEnumType.getDBEnumByType((String)cbbDBType.getSelectedItem()).getDriver(), 30);
			
			tfDBName = new JTextField("School", 30);
			tfHost = new JTextField("localhost", 30);
			tfPort = new JTextField("1433", 30);
			tfUsername = new JTextField("sa", 30);
			tfPassword = new JPasswordField("12345", 30);
			
			btnChange = new JButton("Change");
			btnConnect = new JButton("Connect");
			FontUtility.setSmallLabel(btnConnect, Font.BOLD);
			btnConnect.setIcon(DBIcons.getIcon(DBIcons.ICON_CONNECT_BUTTON));
			
			btnCancel = new JButton("Cancel");
		}
		
		public void attachComponents() {
			//attach components to center panel
			formUtil.addLastField(new JLabel("Database Type"), panelCenter);
			formUtil.addLastField(cbbDBType, panelCenter);
			formUtil.addLastField(new JLabel("JDBC Driver Class Name (required)"), panelCenter);
			formUtil.addLastField(tfDriver, panelCenter);
			formUtil.addLastField(new JLabel("Database Name (ex. access: c:\\folder\\db.mdb)"), panelCenter);
			formUtil.addLastField(tfDBName, panelCenter);
			formUtil.addLastField(new JLabel("Host (work for mysql, sqlserver)"), panelCenter);
			formUtil.addLastField(tfHost, panelCenter);
			formUtil.addLastField(new JLabel("Port (work for mysql, sqlserver)"), panelCenter);
			formUtil.addLastField(tfPort, panelCenter);
			formUtil.addLastField(new JLabel("Username"), panelCenter);
			formUtil.addLastField(tfUsername, panelCenter);
			formUtil.addLastField(new JLabel("Password"), panelCenter);
			formUtil.addLastField(tfPassword, panelCenter);
//			panelCenter.add(new JLabel("Database Type"));
//			panelCenter.add(cbbDBType);
//			panelCenter.add(new JLabel("JDBC Driver Class Name (required)"));
//			panelCenter.add(tfDriver);
//			panelCenter.add(new JLabel("Database Name (ex. access: c:\\folder\\db.mdb)"));
//			panelCenter.add(tfDBName);
//			panelCenter.add(new JLabel("Host (work for mysql, sqlserver)"));
//			panelCenter.add(tfHost);
//			panelCenter.add(new JLabel("Port (work for mysql, sqlserver)"));
//			panelCenter.add(tfPort);
//			panelCenter.add(new JLabel("Username"));
//			panelCenter.add(tfUsername);
//			panelCenter.add(new JLabel("Password"));
//			panelCenter.add(tfPassword);
			
			//attach components to bottom panel
			panelBottom.add(btnChange);
			panelBottom.add(btnConnect);
//			panelBottom.add(btnCancel);
			//attach components to main panel
			add(PanelUtil.createScroll(PanelUtil.createJPanelBorderLayout(panelCenter, BorderLayout.NORTH)), BorderLayout.CENTER);
			add(panelBottom, BorderLayout.SOUTH);
			enableDisconnectedComponents();
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
					DatabaseConfigureViewComponent.setGlobalDBOperationObject(new DBOperationObject(DBOperationEventType.DB_OPERATION_CHANGE));
				}
			});
			
			btnConnect.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getSource() == btnConnect) {
						if(btnConnect.getText().contains("Disconnect")) {
							DatabaseConfigureViewComponent.setGlobalDBOperationObject(new DBOperationObject(DBOperationEventType.DB_OPERATION_DISCONNECT));
						} else {
							DatabaseConfigureViewComponent.setGlobalDBOperationObject(new DBOperationObject(DBOperationEventType.DB_OPERATION_CONNECT));
						}
					}
				}
			});
			
			btnCancel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					handleEvents(DBOperationEventType.DB_OPERATION_CANCEL);
				}
			});
		}

		@Override
		public void handleEvents(String event) {
			if(event.equals(DBOperationEventType.DB_OPERATION_CONNECT)) {
				enableConnectingComponents();
				Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {
						setupConnectionDatabase();
					}
				});
				t.start();
			} else if(DBOperationEventType.DB_OPERATION_DISCONNECT.equals(event)) {
				handleDisconnect();
			} else if(DBOperationEventType.DB_OPERATION_DISCONNECTED.equals(event)) {
				enableDisconnectedComponents();
			} else if(DBOperationEventType.DB_OPERATION_CONNECTED.equals(event)) {
				enableConnectedComponents();
			}
		}
		
		private void handleDisconnect() {
			if(getDBOperationImpl() != null) {
				try {
					getDBOperationImpl().close();
				} catch (SQLException e) {
					DialogUtility.showError("Close connection has error: " + e.getMessage());
				}
				setDBOperationImpl(null);
				DB2OntoPLWorkspaceTab.setConnectStatus(false);
				setGlobalDBOperationObject(new DBOperationObject(DBOperationEventType.DB_OPERATION_DISCONNECTED));
			}
		}
		
		//should use connection pooler
		public void setupConnectionDatabase() {
			String dbType = (String)cbbDBType.getSelectedItem();
			String driver = tfDriver.getText().trim();
			String databaseName = tfDBName.getText().trim();
			String user = tfUsername.getText().trim();
			@SuppressWarnings("deprecation")
			String pass = tfPassword.getText().trim();
			
			String url = "";
			if(dbType.equalsIgnoreCase(DBEnumType.MSACCESS.getType())) {
				url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + databaseName;
			} else if(dbType.equalsIgnoreCase(DBEnumType.SQLSERVER.getType())) {
				url = "jdbc:sqlserver://" + tfHost.getText().trim() + ":" + tfPort.getText().trim() + ";databaseName=" + databaseName + ";selectMethod=cursor";
			}
			if(getDBOperationImpl() != null) {
				try {
					getDBOperationImpl().close();
				} catch (SQLException e) {
					DialogUtility.showError("Close connection has error: " + e.getMessage());
				}
				setDBOperationImpl(null);
			}
			setDBOperationImpl(new DBOperationImplement(driver, url, databaseName, user, pass));
			try {
				getDBOperationImpl().createConnection();
				DB2OntoPLWorkspaceTab.setConnectStatus(true);
				setGlobalDBOperationObject(new DBOperationObject(DBOperationEventType.DB_OPERATION_CONNECTED));
				DialogUtility.showMessages("Connection has been established!");
			} catch (DHConnectionException e) {
				DB2OntoPLWorkspaceTab.setConnectStatus(false);
				DialogUtility.showError("Connecting error");
			}
		}

		public void enableConnectingComponents() {
			btnConnect.setText("Connecting ... ");
			cbbDBType.setEnabled(false);
			tfDriver.setEnabled(false);
			tfDBName.setEnabled(false);
			tfHost.setEnabled(false);
			tfPort.setEnabled(false);
			tfUsername.setEnabled(false);
			tfPassword.setEnabled(false);
			this.revalidate();
		}
		
		public void enableConnectedComponents() {
			btnConnect.setIcon(DBIcons.getIcon(DBIcons.ICON_DISCONNECT_BUTTON));
			btnChange.setEnabled(true);
			btnConnect.setText("Disconnect");
			cbbDBType.setEnabled(false);
			tfDriver.setEnabled(false);
			tfDBName.setEnabled(false);
			tfHost.setEnabled(false);
			tfPort.setEnabled(false);
			tfUsername.setEnabled(false);
			tfPassword.setEnabled(false);
			this.revalidate();
		}
		
		public void enableDisconnectedComponents() {
			btnConnect.setIcon(DBIcons.getIcon(DBIcons.ICON_CONNECT_BUTTON));
			btnChange.setEnabled(false);
			btnConnect.setText("Connect");
			cbbDBType.setEnabled(true);
			tfDriver.setEnabled(true);
			tfDBName.setEnabled(true);
			tfHost.setEnabled(true);
			tfPort.setEnabled(true);
			tfUsername.setEnabled(true);
			tfPassword.setEnabled(true);
			this.revalidate();
		}
	}//end class DatabaseConfigurePanel
	
}
