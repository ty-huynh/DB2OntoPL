package dh.protege41.db2onto.tab;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.protege.editor.core.ui.util.UIUtil;
import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.entity.OWLEntityCreationException;
import org.protege.editor.owl.model.entity.OWLEntityCreationSet;
import org.protege.editor.owl.ui.ontology.OntologyPreferences;
import org.protege.editor.owl.ui.selector.OWLClassSelectorPanel;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import dh.protege41.db2onto.common.DBDataType;
import dh.protege41.db2onto.common.MessagesUtility;
import dh.protege41.db2onto.event.dbobject.DBObject;
import dh.protege41.db2onto.event.dbobject.DBObjectColumn;
import dh.protege41.db2onto.event.dbobject.DBObjectDatabase;
import dh.protege41.db2onto.event.dbobject.DBObjectForeignKey;
import dh.protege41.db2onto.event.dbobject.DBObjectPrimaryKey;
import dh.protege41.db2onto.event.dbobject.DBObjectTable;
import dh.protege41.db2onto.event.dbobject.DBObjectType;
import dh.protege41.db2onto.event.dboperation.DBOperationEventType;
import dh.protege41.db2onto.event.dboperation.DBOperationObject;
import dh.protege41.db2onto.owl.OWLOperationImpl;
import dh.protege41.db2onto.preferences.PreferenceManager;
import dh.protege41.db2onto.tab.ui.DatabasePanel;
import dh.protege41.db2onto.tab.ui.util.DBIcons;
import dh.protege41.db2onto.tab.ui.util.FontUtility;
import dh.protege41.db2onto.tab.ui.util.dialog.DialogUtility;
import dh.protege41.db2onto.tab.ui.util.form.FormUtility;

public class DatabaseMappingViewComponent extends DatabaseViewComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3091175062620357322L;
	
	private static final Logger log = Logger.getLogger(DatabaseMappingViewComponent.class);
	private DBObjectDatabase databaseInfos;
	private DatabaseMappingPanel mappingComponent;
	
	@Override
	protected void disposeOWLView() {
		// TODO Auto-generated method stub
		//remove all listeners here
	}

	@Override
	protected void initialiseOWLView() throws Exception {
		super.initialiseOWLView();
		setLayout(new BorderLayout());
		databaseInfos = getDatabaseInfos();
		mappingComponent = new DatabaseMappingPanel(getOWLEditorKit());
		add(mappingComponent, BorderLayout.CENTER);
		log.info("database mapping view component initialized!");
	}

	@Override
	protected void updateHeader() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected DBObject updateView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected DBOperationObject performOperation() {
		mappingComponent.handleEvents(getLastPerformedDBOperation().getOperation());
		return null;
	}

	
	class DatabaseMappingPanel extends JPanel implements DatabasePanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1441700254696108652L;
		
		private JPanel bottomPanel;
		private JPanel centerPanel;
		
		private JButton btnMapping;
		private JPanel mainPanel;
		private MappingConfigurationPanel mappingConfigPanel;
		private TableSelectorPanel tableSelector;
		private OWLClassSelectorPanel classSelector;
		
		private OWLEditorKit owlEditorKit;
		
		private FormUtility formUtil = new FormUtility();
		private MessagesUtility messagesUtil = new MessagesUtility();
		
		public DatabaseMappingPanel(OWLEditorKit ekit) {
			this.owlEditorKit = ekit;
			initComponents();
			attachComponents();
			initEventListeners();
		}
		
		@Override
		public void initComponents() {
			this.setLayout(new BorderLayout());
			classSelector = new OWLClassSelectorPanel(owlEditorKit, false);
			mappingConfigPanel = new MappingConfigurationPanel(owlEditorKit);
			tableSelector = new TableSelectorPanel(databaseInfos.getTables());
			bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			mainPanel = new JPanel(new BorderLayout());
			btnMapping = new JButton(" Mapping to Ontology ");
			FontUtility.setSmallLabel(btnMapping, Font.BOLD);
			btnMapping.setIcon(DBIcons.getIcon(DBIcons.ICON_MAPPING_BUTTON));
			if(getDBOperationImpl() != null) btnMapping.setEnabled(true);
			else btnMapping.setEnabled(false);
		}

		@Override
		public void attachComponents() {
			bottomPanel.add(btnMapping);
			mainPanel.add(new JScrollPane(mappingConfigPanel), BorderLayout.WEST);
			JPanel pnClass = new JPanel(new GridBagLayout());
			JLabel superClass = new JLabel("Select super class");
			FontUtility.setSmallLabel(superClass, Font.BOLD);
			formUtil.addLastField(superClass, pnClass);
			formUtil.addLastField(new JScrollPane(classSelector), pnClass);
			mainPanel.add(pnClass, BorderLayout.CENTER);
//			mainPanel.add(new JScrollPane(tableSelector), BorderLayout.CENTER);
			
			this.add(new JScrollPane(mainPanel), BorderLayout.CENTER);
			this.add(bottomPanel, BorderLayout.SOUTH);
		}

		@Override
		public void initEventListeners() {
			btnMapping.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					DatabaseMappingViewComponent.setGlobalDBOperationObject(new DBOperationObject(DBOperationEventType.DB_OPERATION_MAPPING));
				}
			});
			
		}

		@Override
		public void handleEvents(String event) {
			if(DBOperationEventType.DB_OPERATION_MAPPING.equals(event)) {
				_handleMapping();
			} else if(DBOperationEventType.DB_OPERATION_CONNECTED.equals(event)) {
				btnMapping.setEnabled(true);
			} else if(DBOperationEventType.DB_OPERATION_DISCONNECTED.equals(event)) {
				btnMapping.setEnabled(false);
			}
		}
		
		private void _handleMapping() {
			try {
				this.messagesUtil.clearAll();
				boolean inActive = false;
				if(MappingConfigurationPanel.IN_ACTIVE_ONTOLOGY.equals(mappingConfigPanel.getSelectedChoice())) {
					if(classSelector.getSelectedObject() == null) {
						DialogUtility.showMessages("<html>If you want to import to active ontology,<br />You must specify a <b>super class</b><br />Select a super class in class hierachy in mapping tab</html>");
						return;
					}
					inActive = true;
				}
				OWLOperationImpl owlFactory = new OWLOperationImpl(getOWLEditorKit());
				//create ontology
				if(!inActive) {
					String ontoID = mappingConfigPanel.getOntologyID();
					String ontoLocation = mappingConfigPanel.getOntologyLocation();
					if(ontoID == null || "".equals(ontoID.trim()) || ontoLocation == null || "".equals(ontoLocation.trim())) {
						DialogUtility.showError("Ontology ID and Ontology location could not be empty");
						return;
					}
					try {
						owlFactory.createNewOntology(ontoID, ontoLocation);
						this.messagesUtil.addInfo("OWLOntology creation", "done");
					} catch (OWLOntologyCreationException e) {
						DialogUtility.showError("Ontology creation failure");
						return;
					}
				}
				//create classes
				mappingClass(owlFactory, inActive);
				
				owlFactory.applyOWLOperations();
				//create properties: object properties and data properties
				mappingProperties(owlFactory);
				
				owlFactory.applyOWLOperations();
			} catch(Exception e) {
				this.messagesUtil.addError("Other error", e.getMessage());
			}
			if(this.messagesUtil.hasErrors()) {
				DialogUtility.showInfos("Mapping done but have some errors" + "\nLogging: " + this.messagesUtil.infos() + "\nSome errors: " + this.messagesUtil.errors(), "Mapping logging");
			} else {
				DialogUtility.showInfos("Mapping done and have no errors" + "\nLogging: " + this.messagesUtil.infos(), "Mapping logging");
//				DialogUtility.showInfos(this.messagesUtil.successes(), "Mapping logging");
			}
		}
		/**
		 * Mapping tables to classes
		 * @param owlFactory
		 * @param inActive
		 */
		public void mappingClass(OWLOperationImpl owlFactory, boolean inActive) {
			//create classes
			DatabaseMappingViewComponent.this.databaseInfos.sortTablesByCase();
			for(DBObjectTable table : DatabaseMappingViewComponent.this.databaseInfos.getTables()) {
				try {
					if(table.getTableCase() == DBObjectType.CASE_3) {
						owlFactory.addOWLOperation(owlFactory.createOWLClass(table.getName()).getOntologyChanges().get(0));
						this.messagesUtil.addInfo("Created owl class " + table.getName(), "done");
						if(inActive) {
							handleCreateSubClassAxiom(owlFactory, table, classSelector.getSelectedObject());
							this.messagesUtil.addInfo("Created subclass axiom " + table.getName() + " subclass of " + classSelector.getSelectedObject().getIRI().getFragment(), "done");
						}
					} else if(table.getTableCase() == DBObjectType.CASE_2) {
						owlFactory.addOWLOperation(owlFactory.createOWLClass(table.getName()).getOntologyChanges().get(0));
						this.messagesUtil.addInfo("Created owl class " + table.getName(), "done");
						handleCreateSubClassAxiom(owlFactory, table);
					}
				} catch (OWLEntityCreationException e) {
					this.messagesUtil.addError("OWLClass creation exception for " + table.getName(), e.getMessage());
				}
			}
		}
		/**
		 * Mapping columns to properties
		 * @param owlFactory
		 */
		public void mappingProperties(OWLOperationImpl owlFactory) {
			IRI iri = owlFactory.getDataTypeBaseIRI();
			for(DBObjectTable table : DatabaseMappingViewComponent.this.databaseInfos.getTables()) {
				if(table.getTableCase() == DBObjectType.CASE_3) {
					for(DBObjectColumn col : table.getColumns()) {
						if(col.isForeignKey()) {
							DBObjectForeignKey fk = table.getFKByColumnName(col.getName());
							handleCreateObjectProperty(owlFactory, "has" + fk.getFKColumn(), fk.getFKTable(), fk.getToTable());
						} else if(!col.isPrimaryKey()) {
							handleCreateDataProperty(owlFactory, table, col, iri);
						}
					}
				} else if(table.getTableCase() == DBObjectType.CASE_2) {
					for(DBObjectColumn col : table.getColumns()) {
						if(col.isForeignKey() && !col.isPrimaryKey()) {
							DBObjectForeignKey fk = table.getFKByColumnName(col.getName());
							handleCreateObjectProperty(owlFactory, "has" + fk.getFKColumn(), fk.getFKTable(), fk.getToTable());
						} else if(!col.isPrimaryKey() && !col.isForeignKey()) {
							handleCreateDataProperty(owlFactory, table, col, iri);
						}
					}
				} else if(table.getTableCase() == DBObjectType.CASE_1) {
					List<DBObjectForeignKey> listFK = table.getForeignKeys();
					if(listFK.size() >= 2) {
						String pkTable = listFK.get(0).getToTable();
						String pkTableInverse = listFK.get(1).getToTable();
						OWLObjectProperty op = handleCreateObjectProperty(owlFactory, "has" + table.getName(), pkTable, pkTableInverse);
						OWLObjectProperty opInverse = handleCreateObjectProperty(owlFactory, "is" + table.getName() + "Of", pkTableInverse, pkTable);
						if(!(op == null || opInverse == null)) {
							OWLAxiom inverse = (OWLAxiom)owlFactory.getInverseObjectPropertiesAxiom(op, opInverse);
							owlFactory.addOWLOperation(owlFactory.createOWLOntologyChange(inverse));
							this.messagesUtil.addInfo("Created inverse axiom for " + " has" + table.getName() + " inverse with " + "is" + table.getName() + "Of", "done");
						}
					}
				} else {
					log.info("the " + table.getName() + " is not classified yet ");
				}
			}
		}
		
		//need be add: add message error to capture the errors
		//add handle when class or data property or object property exist
		public void handleCreateSubClassAxiom(OWLOperationImpl owlFactory, DBObjectTable table, OWLClass superClass) {
			try {
				OWLAxiom ax = (OWLAxiom) owlFactory.getSubClassAxiom(owlFactory.getOWLClass(table.getName()), superClass);
				OWLOntologyChange change = owlFactory.createOWLOntologyChange(ax);
				owlFactory.addOWLOperation(change);
			} catch (URISyntaxException e) {
				this.messagesUtil.addError("Create subclass axiom exception " + table.getName(), e.getMessage());
			}
		}
		
		public void handleCreateSubClassAxiom(OWLOperationImpl owlFactory, DBObjectTable table) {
			List<DBObjectPrimaryKey> pks = table.getPrimaryKeys();
			for(DBObjectPrimaryKey obj : pks) {
				try {
					DBObjectForeignKey fk = table.getFKByColumnName(obj.getColumn());
					OWLAxiom ax = (OWLAxiom) owlFactory.getSubClassAxiom(fk.getFKTable(), fk.getToTable());
					OWLOntologyChange change = owlFactory.createOWLOntologyChange(ax);
					owlFactory.addOWLOperation(change);
					this.messagesUtil.addInfo("Created subclass axiom " + fk.getFKTable() + " subclass of " + fk.getToTable(), "done");
				} catch (URISyntaxException e) {
					this.messagesUtil.addError("Create subclass axiom exception " + table.getName(), e.getMessage());
				}
			}
		}
		
		public OWLObjectProperty handleCreateObjectProperty(OWLOperationImpl owlFactory, String propName, String domain, String range) {
			OWLObjectProperty entity = null;
			boolean hasError = false;
			try {
				if(owlFactory.getOWLObjectProperty(propName) != null) {
					entity = owlFactory.getOWLObjectProperty(propName);
					this.messagesUtil.addInfo("Created object property " + propName, "done");
				} else {
					OWLEntityCreationSet<OWLObjectProperty> set = owlFactory.createOWLObjectProperty(propName);
					log.info("" + propName);
					owlFactory.addOWLOperation(set.getOntologyChanges().get(0));
					entity = set.getOWLEntity();
				}
				OWLAxiom axDom = (OWLAxiom) owlFactory.getObjectPropertyDomainAxiom(entity, owlFactory.getOWLClass(domain));
				owlFactory.addOWLOperation(owlFactory.createOWLOntologyChange(axDom));
				OWLAxiom axRan = (OWLAxiom) owlFactory.getObjectPropertyRangeAxiom(entity, owlFactory.getOWLClass(range));
				owlFactory.addOWLOperation(owlFactory.createOWLOntologyChange(axRan));
			} catch (OWLEntityCreationException e) {
				hasError = true;
				this.messagesUtil.addError("OWL object property creation exception " + propName, e.getMessage());
			} catch (URISyntaxException e) {
				hasError = true;
				this.messagesUtil.addError("Get object property domain/range axiom " + propName, e.getMessage());
			}
			return (hasError ? null : entity);
		}
		
		public OWLDataProperty handleCreateDataProperty(OWLOperationImpl owlFactory, DBObjectTable table, DBObjectColumn col, IRI iri) {
			OWLDataProperty entity = null;
			String propName = "has" + col.getName();
			boolean hasError = false;
			try {
				if(owlFactory.getOWLDataProperty(propName) != null) {
					entity = owlFactory.getOWLDataProperty(propName);
					this.messagesUtil.addInfo("Created data property " + propName, "done");
				} else {
					OWLEntityCreationSet<OWLDataProperty> set = owlFactory.createOWLDataProperty(propName);
					owlFactory.addOWLOperation(set.getOntologyChanges().get(0));
					entity = set.getOWLEntity();
				}
				OWLAxiom axDom = (OWLAxiom) owlFactory.getDataPropertyDomainAxiom(entity, owlFactory.getOWLClass(table.getName()));
				owlFactory.addOWLOperation(owlFactory.createOWLOntologyChange(axDom));
				String type = DBDataType.getRealType(col.getTypeName());
				OWLDataRange dr = owlFactory.getDataRange(iri + "#" + type);
				OWLAxiom axRan = (OWLAxiom) owlFactory.getDataPropertyRangeAxiom(entity, dr);
				owlFactory.addOWLOperation(owlFactory.createOWLOntologyChange(axRan));
			} catch (OWLEntityCreationException e) {
				hasError = true;
				this.messagesUtil.addError("OWL data property creation exception " + propName, e.getMessage());
			} catch (URISyntaxException e) {
				hasError = true;
				this.messagesUtil.addError("Get OWL class exception " + propName, e.getMessage());
			}
			return (hasError ? null : entity);
		}
		
		public void handleCreateCardinality(OWLOperationImpl owlFactory, DBObjectColumn col, OWLEntity prop) {
			if(col.isPrimaryKey() || col.isForeignKey()) {
				if(prop instanceof OWLObjectProperty) {
					owlFactory.getObjectMinCardinality(1, (OWLObjectProperty)prop);
					owlFactory.getObjectMaxCardinality(1, (OWLObjectProperty)prop);
				} else if (prop instanceof OWLDataProperty) {
					owlFactory.getDataMinCardinality(1, (OWLDataProperty)prop);
					owlFactory.getDataMaxCardinality(1, (OWLDataProperty)prop);
				}
			} else {
				if(!col.isNullable()) {
					if(prop instanceof OWLObjectProperty) {
						owlFactory.getObjectMinCardinality(1, (OWLObjectProperty)prop);
					} else if (prop instanceof OWLDataProperty) {
						owlFactory.getDataMinCardinality(1, (OWLDataProperty)prop);
					}
				}
				if(col.isUnique()) {
					if(prop instanceof OWLObjectProperty) {
						owlFactory.getObjectMaxCardinality(1, (OWLObjectProperty)prop);
					} else if (prop instanceof OWLDataProperty) {
						owlFactory.getDataMaxCardinality(1, (OWLDataProperty)prop);
					}
				}
			}
		}
	}
	
	class MappingConfigurationPanel extends JPanel implements DatabasePanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8861117542308676959L;
		private static final String IN_NEW_ONTOLOGY = "In the new ontology";
		private static final String IN_ACTIVE_ONTOLOGY = "In the active ontology";
		private static final String INDIVIDUALNAME_CLASSNAME_UUID = "Class name + random ID";
		private static final String INDIVIDUALNAME_CLASSNAME_PKVALUE = "Class name + primary key value";
		
		private JLabel lbMappingConfiguration;
		private ButtonGroup group;
		private JRadioButton rbActiveOntology;
		private JRadioButton rbNewOntology;
		private JCheckBox cbxIncludeContent;
		private JComboBox cbbNameOfIndividuals;
		
		private JTextField tfOntologyID;
		private JTextField tfOntologyLocation;
		private JButton btnBrowse;
		
		private JPanel configPanel;
		private FormUtility formUtil = new FormUtility();
		
		private OWLEditorKit owlEditorKit;
		public MappingConfigurationPanel(OWLEditorKit ekit) {
			this.owlEditorKit = ekit;
			initComponents();
			attachComponents();
			initEventListeners();
		}
		@Override
		public void initComponents() {
			this.setLayout(new BorderLayout());
			configPanel = new JPanel(new GridBagLayout());
			lbMappingConfiguration = new JLabel("Mapping configuration");
			FontUtility.setSmallLabel(lbMappingConfiguration, Font.BOLD);
			
			group = new ButtonGroup();
			rbActiveOntology = new JRadioButton(IN_ACTIVE_ONTOLOGY, false);
			group.add(rbActiveOntology);
			rbNewOntology = new JRadioButton(IN_NEW_ONTOLOGY, true);
			group.add(rbNewOntology);
			cbxIncludeContent = new JCheckBox("Include table content", false);
			FontUtility.setSmallLabel(cbxIncludeContent, Font.BOLD);
			
			String ontoID = PreferenceManager.getPreferences().get(PreferenceManager.DEFAULT_ONTOLOGY_BASE_IRI, OntologyPreferences.getInstance().generateURI().toString());
			tfOntologyID = new JTextField(ontoID, 40);
			String location = PreferenceManager.getPreferences().get(PreferenceManager.DEFAULT_ONTOLOGY_BASE_LOCATION, new File(new File(System.getProperty("user.home")), "ontologies").toString());
			tfOntologyLocation = new JTextField(location + "\\" + getOntologyLocalName(), 30);
			btnBrowse = new JButton("Browse");
			FontUtility.setSmallLabel(btnBrowse, Font.BOLD);
			btnBrowse.setIcon(DBIcons.getIcon(DBIcons.ICON_BROWSE_BUTTON));
		}

		@Override
		public void attachComponents() {
			formUtil.addLastField(lbMappingConfiguration, configPanel);
			formUtil.addLastField(rbActiveOntology, configPanel);
			formUtil.addLastField(rbNewOntology, configPanel);
			formUtil.addLastField(new JLabel("Ontology ID"), configPanel);
			formUtil.addLastField(tfOntologyID, configPanel);
			formUtil.addLastField(new JLabel("Ontology Location"), configPanel);
			formUtil.addLabel(tfOntologyLocation, configPanel);
			formUtil.addLastField(btnBrowse, configPanel);
			formUtil.addLastField(cbxIncludeContent, configPanel);
			this.add(configPanel, BorderLayout.NORTH);
		}

		@Override
		public void initEventListeners() {
			// TODO Auto-generated method stub
			btnBrowse.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					MappingConfigurationPanel.this.browseForLocation();
				}
			});

			class SelectActionListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getSource() == rbActiveOntology) {
						tfOntologyID.setEnabled(false);
						tfOntologyLocation.setEnabled(false);
						btnBrowse.setEnabled(false);
					} else if(e.getSource() == rbNewOntology) {
						String ontoID = PreferenceManager.getPreferences().get(PreferenceManager.DEFAULT_ONTOLOGY_BASE_IRI, OntologyPreferences.getInstance().generateURI().toString());
						String location = PreferenceManager.getPreferences().get(PreferenceManager.DEFAULT_ONTOLOGY_BASE_LOCATION, new File(new File(System.getProperty("user.home")), "ontologies").toString());
						tfOntologyID.setEnabled(true);
						tfOntologyID.setText(ontoID);
						tfOntologyLocation.setEnabled(true);
						tfOntologyLocation.setText(location);
						btnBrowse.setEnabled(true);
					}
				}
			}
			SelectActionListener selectListener = new SelectActionListener();
			rbActiveOntology.addActionListener(selectListener);
			rbNewOntology.addActionListener(selectListener);
		}

		@Override
		public void handleEvents(String event) {
			// TODO Auto-generated method stub
			
		}
		
		public String getSelectedChoice() {
			if(rbActiveOntology.isSelected())
				return rbActiveOntology.getText();
			else return rbNewOntology.getText();
		}
		
		private void browseForLocation() {
			Set<String> exts = new HashSet<String>();
			exts.add(".owl");
			exts.add(".rdf");
			File file = UIUtil.saveFile(new JFrame(), "Select a file", "OWL File", exts, getOntologyLocalName());
			if (file != null)
			this.tfOntologyLocation.setText(file.toString());
		}
		
		private String getOntologyLocalName() {
			return tfOntologyID.getText().substring(tfOntologyID.getText().lastIndexOf('/') + 1);
		}
		
		public String getOntologyID() {
			return tfOntologyID.getText();
		}
		
		public String getOntologyLocation() {
			return tfOntologyLocation.getText();
		}
	}
	
	class TableSelectorPanel extends JPanel implements DatabasePanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3444771727778550433L;
		private List<DBObjectTable> tables;
		private JList tableList;
		private DefaultListModel model;
		
		private JLabel lbSelectTable;
		private JCheckBox cbxAllTable;
		
		private FormUtility formUtil = new FormUtility();
		
		public TableSelectorPanel(List<DBObjectTable> _tables) {
			this.tables = _tables;
			initComponents();
			attachComponents();
			initEventListeners();
			
		}
		@Override
		public void initComponents() {
			this.setLayout(new BorderLayout());
			model = new DefaultListModel();
			if(this.tables != null) {
				for(DBObjectTable tb : this.tables) {
					model.addElement(tb.getName());
				}
			}
			tableList = new JList(model);
			
			lbSelectTable = new JLabel("Select tables");
			cbxAllTable = new JCheckBox("All tables", true);
		}

		@Override
		public void attachComponents() {
			JPanel pn = new JPanel(new GridBagLayout());
			formUtil.addLabel(lbSelectTable, pn);
			formUtil.addLastField(cbxAllTable, pn);
			formUtil.addLastField(new JScrollPane(tableList), pn);
			
			this.add(pn, BorderLayout.NORTH);
		}

		@Override
		public void initEventListeners() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void handleEvents(String event) {
			// TODO Auto-generated method stub
			
		}
	}
}
