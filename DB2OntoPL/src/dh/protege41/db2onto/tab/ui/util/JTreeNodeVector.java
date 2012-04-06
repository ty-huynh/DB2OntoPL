package dh.protege41.db2onto.tab.ui.util;

import java.util.Vector;

public class JTreeNodeVector<E> extends Vector<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String name;
	
	public JTreeNodeVector(String name) {
		this.name = name;
	}
	
	public JTreeNodeVector(String name, E elements[]) {
		this.name = name;
		for(int i = 0; i < elements.length; i++) {
			add(elements[i]);
		}
	}
	
	public String toString() {
		return "[" + name + "]";
	}
}