package dh.protege41.editor.owl.tab.database;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dh.database.api.DBType;

public class DatabaseConfigurePanel extends JPanel implements DatabasePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel panelCenter;
	private JPanel panelBottom;
	
//	private JLabel lbDataType;
//	private JLabel lbDriver;
//	private JLabel lbDBName;
//	private JLabel lbHost;
//	private JLabel lbPort;
//	private JLabel lbUsername;
//	private JLabel lbPassword;
	
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
	public DatabaseConfigurePanel() {
//		setLayout(new FlowLayout(FlowLayout.CENTER));
//		add(new JButton("This is database configure form"));
		initComponents();
		attachComponents();
	}
	
	public void initComponents() {
		setLayout(new BorderLayout());
		panelCenter = new JPanel(new GridLayout(14, 0, 5, 5));
		panelBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		cbbDBType = new JComboBox(dbTypes);
		
		tfDriver = new JTextField(30);
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
	
//	public static void main(String args[]) {
//		JFrame frame = new JFrame("Design Test");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		DatabaseConfigurePanel main = new DatabaseConfigurePanel();
//		
//		frame.add(main);
//		
//		frame.setSize(400, 250);
//		frame.setVisible(true);
//	}
}
