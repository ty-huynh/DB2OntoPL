package dh.protege41.db2onto.tab.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

public class DatabaseRecordsPanel extends JPanel implements DatabasePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel topPanel;
	private JScrollPane centerPanel;
	private JPanel topLeftPanel;
	private JPanel topRightPanel;
	
	private JTable table;
	private Vector<Vector> rowData;
	private Vector<String> columnsName;
	
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	
	private JButton btnQuery;
	private JTextField tfQuery;
	
	public DatabaseRecordsPanel() {
		initComponents();
		attachComponents();
	}
	
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub\
		setLayout(new BorderLayout());
		topPanel = new JPanel(new GridLayout(1, 2));
		topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		columnsName = new Vector<String>();
		columnsName.addElement("Index");
		columnsName.addElement("Column 1");
		columnsName.addElement("Column 2");
		columnsName.addElement("Column 3");
		
		Vector row = new Vector<String>();
		row.addElement(11);
		row.addElement("Value 1");
		row.addElement("Value 2");
		row.addElement("Value 3");
		
		rowData = new Vector<Vector>();
		rowData.addElement(row);
		rowData.addElement(row);
		rowData.addElement(row);
		rowData.addElement(row);
		rowData.addElement(row);
		rowData.addElement(row);
		rowData.addElement(row);
		rowData.addElement(row);
		rowData.addElement(row);
		rowData.addElement(row);
		rowData.addElement(row);
		rowData.addElement(row);
		rowData.addElement(row);
		rowData.addElement(row);
		rowData.addElement(row);
		rowData.addElement(row);
		
		table = new JTable(rowData, columnsName);
		TableColumn colIndex = table.getColumnModel().getColumn(0);
		colIndex.setPreferredWidth(5);
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

	public static void main(String args[]) {
		JFrame frame = new JFrame("Design Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DatabaseRecordsPanel main = new DatabaseRecordsPanel();
		
		frame.add(main);
		
		frame.setSize(400, 250);
		frame.setVisible(true);
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
		
	}
}
