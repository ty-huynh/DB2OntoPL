package dh.protege41.db2onto.tab.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dh.protege41.db2onto.event.dbobject.DBObject;
import dh.protege41.db2onto.tab.ui.util.list.DBList;
import dh.protege41.db2onto.tab.ui.util.list.DBMutableListModel;

public abstract class DescriptionPanel extends JPanel implements DatabasePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7764518981012484350L;

	protected JPanel contentPanel;
	protected DBList dbList;
	
	public DescriptionPanel() {
		initComponents();
		attachComponents();
		initEventListeners();
	}
	@Override
	public void initComponents() {
		setLayout(new BorderLayout());
		
		contentPanel = new JPanel(new BorderLayout());
		dbList = new DBList();
		
		resetDBListModel(null);
	}

	@Override
	public void attachComponents() {
		contentPanel.add(new JScrollPane(dbList), BorderLayout.CENTER);
		add(contentPanel, BorderLayout.CENTER);
	}

	@Override
	public void initEventListeners() {
		dbList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				DBMutableListModel model = (DBMutableListModel)dbList.getModel();
				Object obj = dbList.getSelectedValue();
				if(obj instanceof DBObject) {
//					log.info(" is db object : " + ((DBObject)obj).getName());
				} else if(obj != null) {
//					log.info(obj.toString());
				} else {
//					log.info("null pointer");
				}
			}
		});
	}

	@Override
	public void handleEvents(String event) {
		
	}

	public abstract void resetDBListModel(DBObject _dbObject);

}
