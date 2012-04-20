package dh.protege41.db2onto.tab.ui.util.tree;

import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;

public class JTreeComponent extends JTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(JTreeComponent.class);
	
	public JTreeComponent() {
		super();
	}
	
	public JTreeComponent(DefaultMutableTreeNode root) {
		super(root);
	}
	
	public JTreeComponent(Object root) {
		super(new DefaultMutableTreeNode(root));
	}
	
	public JTreeComponent(Vector<Object> nodes) {
		super(nodes);
	}
	
	public JTreeComponent addNodes(DefaultMutableTreeNode parent, Vector<Object> nodes) {
//		parent.add(processHierarchy(nodes));
		return this;
	}
	
	public void clear() {
		removeAll();
	}
}
