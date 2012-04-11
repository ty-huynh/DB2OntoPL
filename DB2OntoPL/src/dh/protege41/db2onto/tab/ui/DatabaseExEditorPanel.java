package dh.protege41.db2onto.tab.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.selector.OWLAnnotationPropertySelectorPanel;
import org.protege.editor.owl.ui.selector.OWLClassSelectorPanel;

import dh.protege41.db2onto.common.DBColumnSize;
import dh.protege41.db2onto.tab.ui.util.form.FormUtility;

public class DatabaseExEditorPanel extends JPanel implements DatabasePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6170154270196711627L;
	private static final Logger log = Logger.getLogger(DatabaseExEditorPanel.class);
	private JPanel topPanel;
	private JPanel selectorPanel;
	private JPanel centerPanel;
	private JPanel mainPanel;
	
	private JTextField tfClass;
	private JTextField tfIndividual;
	
	private JCheckBox cbCheckAll;
	private JLabel lbColumnHeader;
	private JLabel lbAnnoHeader;
	private JLabel lbTypeHeader;
	private JLabel lbLangHeader;
	
	private FormUtility formUtility = new FormUtility();
	
	private List<String> colIdentifiers;
	private List<List<String>> selectedRecords;
	private OWLEditorKit _owlEditorKit;
	
	private OWLClassSelectorPanel classSelector;
	private OWLAnnotationPropertySelectorPanel annoPropertySelector;
	
	public DatabaseExEditorPanel(OWLEditorKit owlEditorKit, List<String> colIds, List<List<String>> records) {
		super();
		_owlEditorKit = owlEditorKit;
		colIdentifiers = colIds;
		selectedRecords = records;
		loadSelectors();
		initComponents();
		attachComponents();
		initEventListeners();
	}
	
	public void loadSelectors() {
		classSelector = new OWLClassSelectorPanel(_owlEditorKit, false);
		annoPropertySelector = new OWLAnnotationPropertySelectorPanel(_owlEditorKit, true);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		selectorPanel = new JPanel(new BorderLayout());
		selectorPanel.add(splitPane);
		splitPane.setLeftComponent(classSelector);
		splitPane.setRightComponent(annoPropertySelector);
	}
	
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		setSize(getPreferredSize().width, 600);
		
		topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 0));
		
		centerPanel = new JPanel(new GridBagLayout());
		mainPanel = new JPanel(new BorderLayout());
		
		tfClass = new JTextField(15);
		tfClass.setEditable(false);
		
		cbCheckAll = new JCheckBox("", true);
		formUtility.setPreferredSize(cbCheckAll, DBColumnSize.DB_COLUMN_SIZE_2);
		
		lbColumnHeader = new JLabel("Columns");
		formUtility.setPreferredSize(lbColumnHeader, DBColumnSize.DB_COLUMN_SIZE_4);
		
		lbAnnoHeader = new JLabel("Annotation Property");
		formUtility.setPreferredSize(lbAnnoHeader, DBColumnSize.DB_COLUMN_SIZE_5);
		
		lbTypeHeader = new JLabel("Type");
		formUtility.setPreferredSize(lbTypeHeader, DBColumnSize.DB_COLUMN_SIZE_4);
		
		lbLangHeader = new JLabel("Lang");
		formUtility.setPreferredSize(lbLangHeader, 2 * DBColumnSize.DB_COLUMN_SIZE_2);
//		tfIndividual = new JTextField(15);
	}

	@Override
	public void attachComponents() {
		// TODO Auto-generated method stub
		topPanel.add(new JLabel("Class "));
		topPanel.add(tfClass);
		
		JPanel pnHeader = new JPanel(new GridBagLayout());
		formUtility.addLabel(cbCheckAll, pnHeader);
		formUtility.addLabel(lbColumnHeader, pnHeader);
		formUtility.addLabel(lbAnnoHeader, pnHeader);
		formUtility.addLabel(lbTypeHeader, pnHeader);
		formUtility.addLabel(lbLangHeader, pnHeader);
		
		formUtility.addLastField(pnHeader, centerPanel);
		formUtility.addLastField(new RowPanel(), centerPanel);
		formUtility.addLastField(new RowPanel(), centerPanel);
		formUtility.addLastField(new RowPanel(), centerPanel);
		formUtility.addLastField(new RowPanel(), centerPanel);
		formUtility.addLastField(new RowPanel(), centerPanel);
		formUtility.addLastField(new RowPanel(), centerPanel);
		formUtility.addLastField(new RowPanel(), centerPanel);
		formUtility.addLastField(new RowPanel(), centerPanel);
		formUtility.addLastField(new RowPanel(), centerPanel);
		formUtility.addLastField(new RowPanel(), centerPanel);
		formUtility.addLastField(new RowPanel(), centerPanel);
		formUtility.addLastField(new RowPanel(), centerPanel);
		formUtility.addLastField(new RowPanel(), centerPanel);
		formUtility.addLastField(new RowPanel(), centerPanel);
		formUtility.addLastField(new RowPanel(), centerPanel);
		formUtility.addLastField(new RowPanel(), centerPanel);
		formUtility.addLastField(new RowPanel(), centerPanel);
		formUtility.addLastField(new RowPanel(), centerPanel);

		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(new JScrollPane(centerPanel), BorderLayout.CENTER);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		this.add(splitPane);
		splitPane.setLeftComponent(selectorPanel);
		splitPane.setRightComponent(mainPanel);
	}

	@Override
	public void initEventListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEvents(String event) {
		// TODO Auto-generated method stub
		_handleSaveIndividuals();
	}

	@Override
	public void handleEvents(int event) {
		// TODO Auto-generated method stub
		
	}
	
	private void _handleSaveIndividuals() {
		log.info(annoPropertySelector.getSelectedObject());
		log.info(classSelector.getSelectedObject());
	}
	
//	public static void main(String[] args){
//		JFrame frame = new JFrame("editor extral");
//		frame.add(new DatabaseExEditorPanel(""));
//		frame.setSize(500, 600);
//		frame.setVisible(true);
//	}
	
	class RowPanel extends JPanel implements DatabasePanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 8177361719884143514L;
		private JCheckBox cbSelect;
		private JLabel lbColumn;
		private JComboBox cbbAnnotationProperty;
		private JComboBox cbbType;
		private JComboBox cbbLang;
		
		
		public RowPanel() {
			super(new GridBagLayout());
			initComponents();
			attachComponents();
			initEventListeners();
		}
		
		public RowPanel(boolean isSelected, String column, Object[] annoList, Object[] types, Object[] langs) {
			super(new GridBagLayout());
			cbSelect = new JCheckBox("", true);
			lbColumn = new JLabel(column);
			cbbAnnotationProperty = new JComboBox(annoList);
			cbbType = new JComboBox(types);
			cbbLang = new JComboBox(langs);
			resetPreferredSize();
			attachComponents();
			initEventListeners();
		}
		@Override
		public void initComponents() {
//			// TODO Auto-generated method stub
			cbSelect = new JCheckBox("", true);
			lbColumn = new JLabel("Column");
			cbbAnnotationProperty = new JComboBox(new String[] {"anno 1", "anno 2", "annotation property"});
			cbbType = new JComboBox(new String[] {"int", "float", "unsigned integer"});
			cbbLang = new JComboBox(new String[] {"vi", "en", "fr"});
			resetPreferredSize();
		}
		
		public void resetPreferredSize() {
			formUtility.setPreferredSize(cbSelect, DBColumnSize.DB_COLUMN_SIZE_2);
			formUtility.setPreferredSize(lbColumn, DBColumnSize.DB_COLUMN_SIZE_4);
			formUtility.setPreferredSize(cbbAnnotationProperty, DBColumnSize.DB_COLUMN_SIZE_5);
			formUtility.setPreferredSize(cbbType, DBColumnSize.DB_COLUMN_SIZE_4);
			formUtility.setPreferredSize(cbbLang, 2 * DBColumnSize.DB_COLUMN_SIZE_2);
		}
		
		@Override
		public void attachComponents() {
			// TODO Auto-generated method stub
			formUtility.addLabel(cbSelect, this);
			formUtility.addLabel(lbColumn, this);
			formUtility.addLabel(cbbAnnotationProperty, this);
			formUtility.addLabel(cbbType, this);
			formUtility.addLabel(cbbLang, this);
		}

		@Override
		public void initEventListeners() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void handleEvents(String event) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void handleEvents(int event) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
