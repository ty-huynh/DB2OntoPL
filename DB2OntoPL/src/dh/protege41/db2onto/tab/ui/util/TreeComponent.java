package dh.protege41.db2onto.tab.ui.util;

import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;

public class TreeComponent extends JTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(TreeComponent.class);
	
	public TreeComponent() {
		super();
	}
	
	public TreeComponent(DefaultMutableTreeNode root) {
		super(root);
	}
	
	public TreeComponent(Object root) {
		super(new DefaultMutableTreeNode(root));
	}
	
	public TreeComponent(Vector<Object> nodes) {
		super(nodes);
	}
	
	public TreeComponent addNodes(DefaultMutableTreeNode parent, Vector<Object> nodes) {
//		parent.add(processHierarchy(nodes));
		return this;
	}
	
//	//vector to tree
//	@SuppressWarnings("unchecked")
//	public static DefaultMutableTreeNode processHierarchy(Vector<Object> nodes) {
//		DefaultMutableTreeNode root = new DefaultMutableTreeNode(nodes.get(0));
//		log.info("root: " + root);
//		DefaultMutableTreeNode child;
//		for(Object obj : nodes ) {
//			if(obj instanceof Vector) {
//				child = processHierarchy((Vector<Object>)obj); //node have children
//			} else {
//				child = new DefaultMutableTreeNode(obj); //leaf
//				log.info("child: " + child);
//			}
//			root.add(child);
//		}
//		return root;
//	}
}













