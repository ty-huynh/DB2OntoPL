package dh.protege41.db2onto.tab.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;
import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.entity.OWLEntityCreationException;
import org.protege.editor.owl.model.entity.OWLEntityCreationSet;
import org.protege.editor.owl.model.hierarchy.OWLAnnotationPropertyHierarchyProvider;
import org.protege.editor.owl.ui.UIHelper;
import org.protege.editor.owl.ui.selector.OWLAnnotationPropertySelectorPanel;
import org.protege.editor.owl.ui.selector.OWLClassSelectorPanel;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;

import dh.protege41.db2onto.common.DBColumnSize;
import dh.protege41.db2onto.common.DBRandomGenerator;
import dh.protege41.db2onto.event.dboperation.DBOperationEventType;
import dh.protege41.db2onto.owl.OWLOperation;
import dh.protege41.db2onto.owl.OWLOperationImpl;
import dh.protege41.db2onto.tab.ui.util.dialog.DialogUtility;
import dh.protege41.db2onto.tab.ui.util.form.FormUtility;
import dh.protege41.db2onto.tab.ui.util.panel.PanelUtil;

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
		mainPanel.add(PanelUtil.createJPanelBorderLayout(PanelUtil.createScroll(centerPanel), BorderLayout.NORTH), BorderLayout.CENTER);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		this.add(splitPane);
		splitPane.setLeftComponent(selectorPanel);
		splitPane.setRightComponent(mainPanel);
	}

	@Override
	public void initEventListeners() {
		// TODO Auto-generated method stub
		cbCheckAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cbCheckAll.isSelected()) {
					for(RowPanel rp : DatabaseExEditorPanel.this.listRowPanel) {
						rp.select(true);
					}
				} else {
					for(RowPanel rp : DatabaseExEditorPanel.this.listRowPanel) {
						rp.select(false);
					}
				}
			}
		});
	}

	@Override
	public void handleEvents(String event) {
		if(DBOperationEventType.DB_OPERATION_CREATE_INDIVIDUAL.equals(event)) {
			_handleSaveIndividuals();
		}
	}

	private void _handleSaveIndividuals() {
		String className = tfClass.getText();
		if(className == null || "".equals(className)) {
			DialogUtility.showMessages("<html>Class can not be null.<br />You must select a class at class selector panel</html>");
			return;
		}
		try {
			OWLOperation operation = new OWLOperationImpl(_owlEditorKit);
			for(List<String> row : selectedRecords) {
				OWLEntityCreationSet<OWLNamedIndividual> indi = operation.createOWLIndividual(className + "_" + DBRandomGenerator.createUUID());
				operation.addOWLOperation(operation.createOWLOntologyChange(operation.createClassTypeForIndividual(classSelector.getSelectedObject(), indi.getOWLEntity())));
				for(int j = 0; j < row.size(); j++) {
					RowPanel rp = listRowPanel.get(j);
					if(rp.isSelected()){
						OWLAnnotationSubject subject = operation.getAnnotationSubject(indi.getOWLEntity());
						OWLAnnotationProperty AnnoProperty = (OWLAnnotationProperty)rp.getSelectedAnnotationProperty();
						OWLAnnotationValue value = null;
						if(rp.getSelectedLang() != null) {
							value = operation.createAnnotationValue(row.get(j), (String)rp.getSelectedLang());
						} else if(rp.getSelectedDataType() != null) {
							value = operation.createAnnotationValue(row.get(j), (OWLDatatype)rp.getSelectedDataType());
						} else {
							value = operation.createAnnotationValue(row.get(j));
						}
						operation.addOWLOperation(operation.createOWLOntologyChange(operation.createAnnotationForIndividual(subject, AnnoProperty, value)));
					}
				}
			}
			operation.applyOWLOperations();
			DialogUtility.showMessages("The individuals have been created");
		} catch (OWLEntityCreationException e) {
			log.error("Could not create individuals");
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
			cbSelect = new JCheckBox("", true);
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
			formUtility.addLabel(cbSelect, this);
			formUtility.addLabel(lbColumn, this);
			formUtility.addLabel(cbbAnnotationProperty, this);
			formUtility.addLabel(cbbType, this);
			formUtility.addLabel(cbbLang, this);
		}

		@Override
		public void initEventListeners() {
			this.cbbType.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(cbbType.getSelectedItem() != null) {
						cbbLang.setSelectedItem(null);
					}
				}
			});
			this.cbbLang.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(cbbLang.getSelectedItem() != null) {
						cbbType.setSelectedItem(null);
					}
				}
			});
			this.cbSelect.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!cbSelect.isSelected()) {
						DatabaseExEditorPanel.this.cbCheckAll.setSelected(false);
					}
				}
			});
		}

		@Override
		public void handleEvents(String event) {
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
//							log.info("last anno pro selected :  " + e.getItem());
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
		
		public void select(boolean check) {
			cbSelect.setSelected(check);
		}
		
		public boolean isSelected() {
			return cbSelect.isSelected();
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
