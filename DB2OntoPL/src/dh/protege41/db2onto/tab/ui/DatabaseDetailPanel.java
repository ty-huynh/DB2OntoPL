package dh.protege41.db2onto.tab.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dh.database.api.DBType;
import dh.protege41.db2onto.tab.ui.util.JTreeComponent;
import dh.protege41.db2onto.tab.ui.util.JTreeNodeVector;

public class DatabaseDetailPanel extends JPanel implements DatabasePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel topPanel;
	private JPanel centerPanel;
	
	private JTreeComponent dbTree;
	
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	
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
		centerPanel.add(new JScrollPane(dbTree));
		
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
	
	public void setDBTree(JTreeComponent newTree) {
		dbTree = newTree;
	}
	public JTreeComponent getDBTree() {
		return dbTree;
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


	@Override
	public void handleEvents(String event) {
		// TODO Auto-generated method stub
		
	}
}
