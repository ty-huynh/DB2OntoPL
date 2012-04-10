package dh.protege41.db2onto.tab.ui.util.table;

import javax.swing.table.DefaultTableCellRenderer;

import org.apache.log4j.Logger;
import org.protege.editor.core.ui.util.CheckTable;
import org.protege.editor.core.ui.util.CheckTableModel;


public class DBCheckTable<O> extends CheckTable<O> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3228730622702064404L;
	private static final Logger log = Logger.getLogger(DBCheckTable.class);
	private int _lastColumn = 0;
	private int _lastRow = 0;
	
	public DBCheckTable(String name) {
		super(name);
	}

	public boolean clearTable() {
		try{
			CheckTableModel<O> model = (CheckTableModel<O>) getModel();
			model.getDataVector().removeAllElements();
			model.fireTableDataChanged();
		} catch(Exception e) {
			log.info("Error: delete data of table fail");
			return false;
		}
		return true;
	}
	
	public boolean clearHeader() {
		try {
			setTableHeader(null);
			revalidate();
		} catch(Exception e) {
			log.info("Error: can not delete header row");
			return false;
		}
		
		return true;
	}
	
	public void setTooltip(String idCol, String text) {
		DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
		cellRender.setToolTipText(text);
		getColumn(idCol).setCellRenderer(cellRender);
		revalidate();
	}

	public int getLastColumn() {
		return _lastColumn;
	}

	public void setLastColumn(int _lastColumn) {
		this._lastColumn = _lastColumn;
	}

	public int getLastRow() {
		return _lastRow;
	}

	public void setLastRow(int _lastRow) {
		this._lastRow = _lastRow;
	}
}
