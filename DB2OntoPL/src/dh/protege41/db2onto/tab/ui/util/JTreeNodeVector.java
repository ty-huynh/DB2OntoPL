package dh.protege41.db2onto.tab.ui.util;

import java.util.Vector;

public class JTreeNodeVector<E> extends Vector<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Object _node;
	
	public JTreeNodeVector(Object node) {
		this._node = node;
	}
	
	public JTreeNodeVector(Object node, E elements[]) {
		this._node = node;
		for(int i = 0; i < elements.length; i++) {
			add(elements[i]);
		}
	}
	
	public String toString() {
		return "[" + _node + "]";
	}
}
