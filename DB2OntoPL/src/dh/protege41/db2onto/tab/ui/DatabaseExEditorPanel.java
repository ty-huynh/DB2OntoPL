package dh.protege41.db2onto.tab.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;
import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.entity.OWLEntityCreationException;
import org.protege.editor.owl.model.entity.OWLEntityCreationSet;
import org.protege.editor.owl.model.event.EventType;
import org.protege.editor.owl.model.event.OWLModelManagerChangeEvent;
import org.protege.editor.owl.model.event.OWLModelManagerListener;
import org.protege.editor.owl.model.hierarchy.OWLAnnotationPropertyHierarchyProvider;
import org.protege.editor.owl.ui.UIHelper;
import org.protege.editor.owl.ui.selector.OWLAnnotationPropertySelectorPanel;
import org.protege.editor.owl.ui.selector.OWLClassSelectorPanel;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAnnotationValueVisitor;
import org.semanticweb.owlapi.model.OWLAnnotationValueVisitorEx;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;

import dh.protege41.db2onto.common.DBColumnSize;
import dh.protege41.db2onto.common.DBRandomGenerator;
import dh.protege41.db2onto.owl.OWLOperation;
import dh.protege41.db2onto.owl.OWLOperationImpl;
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
	
	private List<RowPanel> listRowPanel = new ArrayList<RowPanel>();
	
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
		classSelector.addSelectionListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(classSelector.getSelectedObject() != null) {
					String id = classSelector.getSelectedObject().toStringID();
					tfClass.setText(id.substring(id.indexOf('#') + 1));
				}
			}
		});
		annoPropertySelector = new OWLAnnotationPropertySelectorPanel(_owlEditorKit, true);
		annoPropertySelector.getOWLModelManager().addOntologyChangeListener(new OWLOntologyChangeListener() {
			
			@Override
			public void ontologiesChanged(List<? extends OWLOntologyChange> arg0)
					throws OWLException {
				for(RowPanel rp : listRowPanel) {
					rp.reloadAnnoPropComboBox();
				}
			}
		});
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
		setSize(getPreferredSize().width, 400);
		
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
		formUtility.setPreferredSize(lbTypeHeader, DBColumnSize.DB_COLUMN_SIZE_3 + DBColumnSize.DB_COLUMN_SIZE_4);
		
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
		for(int i = 1; i < colIdentifiers.size(); i++) {
			RowPanel rp = new RowPanel(colIdentifiers.get(i));
			formUtility.addLastField(rp, centerPanel);
			listRowPanel.add(rp);
		}

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
		
		try {
			OWLOperation operation = new OWLOperationImpl(_owlEditorKit);
			
			String className = classSelector.getSelectedObject().toStringID().substring(classSelector.getSelectedObject().toStringID().indexOf('#') + 1);
			log.info("Class name: " + className);
			for(List<String> row : selectedRecords) {
				OWLEntityCreationSet<OWLNamedIndividual> indi = operation.createOWLIndividual(className + "_" + DBRandomGenerator.createUUID());
				operation.addOWLOperation(operation.createOWLOntologyChange(operation.createClassTypeForIndividual(classSelector.getSelectedObject(), indi.getOWLEntity())));
				for(int j = 0; j < row.size(); j++) {
					RowPanel rp = listRowPanel.get(j);
					operation.addOWLOperation(operation.createOWLOntologyChange(operation.createAnnotationForIndividual(operation.getAnnotationSubject(indi.getOWLEntity()), (OWLAnnotationProperty)rp.getSelectedAnnotationProperty(), operation.createAnnotationValue(row.get(j), (OWLDatatype)rp.getSelectedDataType()))));
				}
			}
//			log.info("all changes: " + operation.getOWLOperations());
			operation.applyOWLOperations();
		} catch (OWLEntityCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			
//			OWLEntityCreationSet<OWLNamedIndividual> set = _owlEditorKit.getModelManager().getOWLEntityFactory().createOWLIndividual("newIndividual", null);
//			List<OWLOntologyChange> changes = (List<OWLOntologyChange>) set.getOntologyChanges();
//			OWLClass cls = classSelector.getSelectedObject();
//			OWLLiteral annoValue = _owlEditorKit.getOWLModelManager().getOWLDataFactory().getOWLLiteral("Hey you", "vi");
//			OWLAnnotationProperty annoProp = _owlEditorKit.getOWLModelManager().getOWLDataFactory().getOWLAnnotationProperty(IRI.create(new URI("http://www.owl-ontologies.com/travel.owl#snow")));
//			if (cls != null) {
//				OWLAxiom typeAxiom = _owlEditorKit.getOWLModelManager().getOWLDataFactory().getOWLClassAssertionAxiom(cls, set.getOWLEntity());
//				OWLAxiom annoAxiom = _owlEditorKit.getOWLModelManager().getOWLDataFactory().getOWLAnnotationAssertionAxiom(annoProp, (OWLAnnotationSubject) set.getOWLEntity().getIRI(), annoValue);
//				OWLOntologyChange change = new AddAxiom(_owlEditorKit.getOWLModelManager().getActiveOntology(), typeAxiom);
//				OWLOntologyChange change2 = new AddAxiom(_owlEditorKit.getOWLModelManager().getActiveOntology(), annoAxiom);
//				changes.add(change);
//				changes.add(change2);
//			}
//			_owlEditorKit.getModelManager().applyChanges(changes);
//			log.info("Individual : " + set.getOWLEntity());
//			log.info("annnotation value : " + annoValue);
//			log.info("Class : " + cls);
//			log.info("Changes : " + changes);
//			log.info("Ontology id " + _owlEditorKit.getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI());
//		} catch (OWLEntityCreationException e) {
//			e.printStackTrace();
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
//		log.info(annoPropertySelector.getSelectedObject());
//		log.info(classSelector.getSelectedObject());
	}
	
//	public static void main(String[] args){
//		JFrame frame = new JFrame("editor extra");
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
		
		private String lastAnnoPropSelected = "comment";
		private Map<String, OWLAnnotationProperty> mapAnnotationProperty;
		
		public RowPanel() {
			super(new GridBagLayout());
			initComponents();
			attachComponents();
			initEventListeners();
		}
		
		public RowPanel(String column) {
			super(new GridBagLayout());
			cbSelect = new JCheckBox();
			lbColumn = new JLabel(column);
			_loadDefaultComboBoxes();
			resetPreferredSize();
			attachComponents();
			initEventListeners();
		}
		
		public RowPanel(boolean isSelected, String column, Object[] annoList, Object[] types, Object[] langs) {
			super(new GridBagLayout());
			cbSelect = new JCheckBox("", isSelected);
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
//			cbSelect = new JCheckBox("", true);
//			lbColumn = new JLabel("Column");
//			cbbAnnotationProperty = new JComboBox(new String[] {"anno 1", "anno 2", "annotation property"});
//			cbbType = new JComboBox(new String[] {"int", "float", "unsigned integer"});
//			cbbLang = new JComboBox(new String[] {"vi", "en", "fr"});
//			resetPreferredSize();
		}
		
		public void resetPreferredSize() {
			formUtility.setPreferredSize(cbSelect, DBColumnSize.DB_COLUMN_SIZE_2);
			formUtility.setPreferredSize(lbColumn, DBColumnSize.DB_COLUMN_SIZE_4);
			formUtility.setPreferredSize(cbbAnnotationProperty, DBColumnSize.DB_COLUMN_SIZE_5);
			formUtility.setPreferredSize(cbbType, DBColumnSize.DB_COLUMN_SIZE_4 + DBColumnSize.DB_COLUMN_SIZE_3);
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
		
		private void _loadDefaultComboBoxes() {
			UIHelper helper = new UIHelper(DatabaseExEditorPanel.this._owlEditorKit);
			this.cbbLang = helper.getLanguageSelector();
			this.cbbLang.addItem("vi");
			this.cbbLang.setEditable(false);
			
			this.cbbType = helper.getDatatypeSelector();
			reloadAnnoPropComboBox();
		}

		public void reloadAnnoPropComboBox() {
			
			if(mapAnnotationProperty != null)
				mapAnnotationProperty.clear();
			if(cbbAnnotationProperty != null) {
				cbbAnnotationProperty.removeAllItems();
				this.revalidate();
			} else {
				cbbAnnotationProperty = new JComboBox();
				cbbAnnotationProperty.addItemListener(new ItemListener() {
					
					@Override
					public void itemStateChanged(ItemEvent e) {
						// TODO Auto-generated method stub
						if(e.getStateChange() == ItemEvent.SELECTED) {
							RowPanel.this.lastAnnoPropSelected = new String(e.getItem().toString());
							log.info("last anno pro selected :  " + e.getItem());
						}
					}
				});
			}
			//get OWLAnnotationProperty
			mapAnnotationProperty = new HashMap<String, OWLAnnotationProperty>();
			OWLAnnotationPropertyHierarchyProvider hp = DatabaseExEditorPanel.this._owlEditorKit.getOWLModelManager().getOWLHierarchyManager().getOWLAnnotationPropertyHierarchyProvider();
			for(OWLAnnotationProperty ap : hp.getRoots()) {
				String owlIri = ap.getIRI().toString();
				String owlname = owlIri.substring(owlIri.indexOf('#') + 1);
				mapAnnotationProperty.put(owlname, ap);
				cbbAnnotationProperty.addItem(owlname);
			}
			if(mapAnnotationProperty.containsKey(lastAnnoPropSelected))
				cbbAnnotationProperty.setSelectedItem(lastAnnoPropSelected);
			
			this.revalidate();
		}
		
		public Object getSelectedLang() {
			return cbbLang.getSelectedItem();//String
		}

		public Object getSelectedDataType() {
			return cbbType.getSelectedItem();//OWLDataType
		}

		public Object getSelectedAnnotationProperty() {
			return mapAnnotationProperty.get(cbbAnnotationProperty.getSelectedItem());//OWLAnnotationProperty
		}
	}
}
