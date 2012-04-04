package dh.protege41.db2onto.tab.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class DatabaseDetailPanel extends JPanel implements DatabasePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel topPanel;
	private JPanel centerPanel;
	
	private JTree dbTree;
	private DefaultMutableTreeNode root;
	private DefaultMutableTreeNode table_1;
	private DefaultMutableTreeNode table_2;
	private DefaultMutableTreeNode table_3;
	private DefaultMutableTreeNode field_11;
	private DefaultMutableTreeNode field_12;
	private DefaultMutableTreeNode field_21;
	private DefaultMutableTreeNode field_31;
	private DefaultMutableTreeNode field_32;
	private DefaultMutableTreeNode field_33;
	
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	
	public DatabaseDetailPanel() {
		initComponents();
		attachComponents();
	}
	
	
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		centerPanel = new JPanel(new GridLayout(1, 1));
		centerPanel.setBackground(Color.WHITE);
		
		root = new DefaultMutableTreeNode("Database");
		table_1 = new DefaultMutableTreeNode("Table 1");
		table_2 = new DefaultMutableTreeNode("Table 2");
		table_3 = new DefaultMutableTreeNode("Table 3");
		field_11 = new DefaultMutableTreeNode("Field 11");
		field_12 = new DefaultMutableTreeNode("Field 12");
		field_21 = new DefaultMutableTreeNode("Field 21");
		field_31 = new DefaultMutableTreeNode("Field 31");
		field_32 = new DefaultMutableTreeNode("Field 32");
		field_33 = new DefaultMutableTreeNode("Field 33");
		
		dbTree = new JTree(root);
		
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
		table_1.add(field_11);
		table_1.add(field_12);
		table_2.add(field_21);
		table_3.add(field_31);
		table_3.add(field_32);
		table_3.add(field_33);
		table_1.add(table_2);
		table_2.add(table_3);
		
		DefaultMutableTreeNode table_4 = new DefaultMutableTreeNode("table 4");
		DefaultMutableTreeNode table_5 = new DefaultMutableTreeNode("table 5");
		DefaultMutableTreeNode table_6 = new DefaultMutableTreeNode("table 6");
		DefaultMutableTreeNode table_7 = new DefaultMutableTreeNode("table 7");
		table_3.add(table_4);
		table_4.add(table_5);
		table_5.add(table_6);
		table_6.add(table_7);
		root.add(table_1);
		
		centerPanel.add(dbTree);
		
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
	
//	public static void main(String args[]) {
//		JFrame frame = new JFrame("Design Test");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		DatabaseDetailPanel main = new DatabaseDetailPanel();
//		
//		frame.add(main);
//		
//		frame.setSize(400, 250);
//		frame.setVisible(true);
//	}
}
