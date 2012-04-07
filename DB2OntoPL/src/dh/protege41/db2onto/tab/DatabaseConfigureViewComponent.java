package dh.protege41.db2onto.tab;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;

import dh.database.api.DBDriver;
import dh.database.api.DBType;
import dh.database.api.TestConnection;
import dh.protege41.db2onto.event.dbobject.DBObjectEvent;
import dh.protege41.db2onto.event.dbobject.DBObjectEventListener;
import dh.protege41.db2onto.event.dbobject.DBObject;
import dh.protege41.db2onto.event.dboperation.DBOperationEventType;
import dh.protege41.db2onto.event.dboperation.DBOperationObject;
import dh.protege41.db2onto.tab.ui.DatabaseConfigurePanel;
import dh.protege41.db2onto.tab.ui.DatabasePanel;

public class DatabaseConfigureViewComponent extends DatabaseViewComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static Logger log = Logger.getLogger(DatabaseConfigureViewComponent.class);
	
	private DatabaseConfigurePanel databaseConfigureComponent;
	
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
		private JButton btnLoad;
		private JButton btnCancel;
		
		private final String[] dbTypes = {DBType.MSACCESS, DBType.SQLSERVER};
		private final String[] dbDrivers = {DBDriver.MSACCESS, DBDriver.SQLSERVER};
		
		public DatabaseConfigurePanel() {
			initComponents();
			attachComponents();
			initEventListeners();
		}
		
		public void initComponents() {
			setLayout(new BorderLayout());
			panelCenter = new JPanel(new GridLayout(14, 0, 5, 5));
			panelBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
			
			cbbDBType = new JComboBox(dbTypes);
			cbbDBType.setSelectedIndex(0);
			
			tfDriver = new JTextField(dbDrivers[cbbDBType.getSelectedIndex()], 30);
			
			tfDBName = new JTextField(30);
			tfHost = new JTextField(30);
			tfPort = new JTextField(30);
			tfUsername = new JTextField(30);
			tfPassword = new JTextField(30);
			
			btnChange = new JButton("Change");
			btnLoad = new JButton("Load");
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
			panelBottom.add(btnLoad);
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
					
				}
			});
			
			btnChange.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
//					handleEvents(DBOperationEventType.DB_OPERATION_CHANGE);
					DatabaseConfigureViewComponent.setGlobalDBOperationOBject(new DBOperationObject(DBOperationEventType.DB_OPERATION_CHANGE));
				}
			});
			
			btnLoad.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					handleEvents(DBOperationEventType.DB_OPERATION_LOADING);
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
			
			putMessages("Event " + event);
		}
		
		public void putMessages(String message) {
			JOptionPane.showMessageDialog(null, message);
		}
		
		//should use connection pooler
		public void loadDatabase() {
			String driver = tfDriver.getText().trim();
			String databaseName = tfDBName.getText().trim();
			String url = "jdbc:sqlserver://" + tfHost.getText().trim() + ":" + tfPort.getText().trim() + ";databaseName=" + databaseName + ";selectMethod=cursor";
			String user = tfUsername.getText().trim();
			String pass = tfPassword.getText().trim();
			
			log.info(driver + ", " + url + ", " + user + ", " + pass);
			TestConnection test = new TestConnection();
			test.DatabaseConnectionTest(driver, databaseName, url, user, pass);
			try {
				test.ExeTest();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void handleEvents(int event) {
			// TODO Auto-generated method stub
			
		}
		
//		public static void main(String args[]) {
//			JFrame frame = new JFrame("Design Test");
//			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			
//			DatabaseConfigurePanel main = new DatabaseConfigurePanel();
//			
//			frame.add(main);
//			
//			frame.setSize(400, 600);
//			frame.setVisible(true);
//		}

		

		
	}
}
