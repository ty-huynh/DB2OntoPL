package dh.protege41.db2onto.tab.ui.util.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.swing.ListCellRenderer;

import org.apache.log4j.Logger;
import org.protege.editor.core.ui.list.MList;


public class DBList extends MList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2188874369022922269L;
	private static final Logger log = Logger.getLogger(DBList.class);
	
	private ListCellRenderer cellRenderer;
	private DBMutableListModel model = new DBMutableListModel();
	public DBList() {
		super.setModel(model);
		cellRenderer = new DBListCellRenderer();
		MListCellRenderer ren = (MListCellRenderer) getCellRenderer();
		ren.setContentRenderer(cellRenderer);
		
	}
	
	public void setCellRenderer(ListCellRenderer cellRenderer) {
		if(!(getCellRenderer() instanceof MListCellRenderer)) {
            super.setCellRenderer(cellRenderer);
        }
        else {
        	cellRenderer = cellRenderer;
        }
	}
	
	public void setListData(final Object[] listData) {
        model.clear();
        for (Object obj : listData) {
                model.addElement(obj);
        }
    }
	
	public void setListData(final Vector<?> listData) {
		model.clear();
		for(Object obj : listData) {
			model.addElement(obj);
		}
	}
	
	public void setListObjects(Collection<Object> objects) {
		model.clear();
		for(Object obj : objects) {
			model.addElement(obj);
		}
	}
	
	public void addListObjects(Collection<Object> objects) {
		for(Object obj : objects) {
			model.addElement(obj);
		}
	}
	
	public void addListObjects(final Object[] objects) {
		for(Object obj : objects) {
			model.addElement(obj);
		}
	}
	
	public void addListObjects(final Vector<?> objects) {
		for(Object obj : objects) {
			model.addElement(obj);
		}
	}
	
	public List<Object> getListItems() {
		List<Object> list = new ArrayList<Object>();
		for(int len = model.getSize(), i = 0; i < len; i++) {
			list.add(model.get(i));
		}
		return list;
	}
	
	public List<Object> getListItems(int from, int to) {
		if(to > model.getSize()) {
			log.info("to = " + to + " is greater than model'size = " + model.size());
			return null;
		}
		List<Object> list = new ArrayList<Object>();
		for(int i = from; i < to; i++) {
			list.add(model.get(i));
		}
		return list;
	}
	
	public Object getSelectedValue() {
		try{
			DBListItem item = ((DBListItem) super.getSelectedValue());
			if(item == null) {
	            return null;
	        }
			return (Object) item.getObject();
		} catch(Exception e) {
			log.info("cannot cast to DBListItem, maybe it is DBListHeader");
			return null;
		}
    }

    public Object getSelectedObject() {
        return getSelectedValue();
    }

    public int getIndexOf(Object object) {
    	return model.indexOf(object);
    }
//    public static void main(String args[]) {
//    	String array[] = {"item 1", "item 2", "item 3"};
//    	final DBList list = new DBList();
//    	list.setListData(array);
//    	DBMutableListModel  model = (DBMutableListModel)list.getModel();
//    	model.addHeader("Header");
//    	list.addListSelectionListener(new ListSelectionListener() {
//			
//			@Override
//			public void valueChanged(ListSelectionEvent e) {
//				// TODO Auto-generated method stub
////				list.getSelectedValue();
//				System.out.println("d");
////				System.out.println(list.getSelectedValue());
//			}
//		});
//    	
//    	JFrame f = new JFrame();
//        f.getContentPane().setLayout(new BorderLayout());
//        f.getContentPane().add(new JScrollPane(list));
//        f.setSize(400, 250);
//        f.setVisible(true);
//    }
}
