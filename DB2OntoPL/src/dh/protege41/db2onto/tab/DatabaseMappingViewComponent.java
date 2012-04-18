package dh.protege41.db2onto.tab;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.entity.OWLEntityCreationException;
import org.protege.editor.owl.model.entity.OWLEntityCreationSet;
import org.protege.editor.owl.ui.selector.OWLClassSelectorPanel;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyChange;

import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

import dh.protege41.db2onto.common.DBDataType;
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
import dh.protege41.db2onto.tab.DatabaseRecordsViewComponent.DatabaseRecordsPanel;
import dh.protege41.db2onto.tab.ui.DatabasePanel;
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
		
		private JButton btnImport;
		private JPanel mainPanel;
		private MappingConfigurationPanel mappingConfigPanel;
		private TableSelectorPanel tableSelector;
		private OWLClassSelectorPanel classSelector;
		
		private OWLEditorKit owlEditorKit;
		
		private FormUtility formUtil = new FormUtility();
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
			btnImport = new JButton("Import");
		}

		@Override
		public void attachComponents() {
			bottomPanel.add(btnImport);
			mainPanel.add(new JScrollPane(mappingConfigPanel), BorderLayout.WEST);
			JPanel pnClass = new JPanel(new GridBagLayout());
			formUtil.addLastField(new JLabel("Select super class"), pnClass);
			formUtil.addLastField(new JScrollPane(classSelector), pnClass);
			mainPanel.add(pnClass, BorderLayout.CENTER);
//			mainPanel.add(new JScrollPane(tableSelector), BorderLayout.CENTER);
			
			this.add(new JScrollPane(mainPanel), BorderLayout.CENTER);
			this.add(bottomPanel, BorderLayout.SOUTH);
		}

		@Override
		public void initEventListeners() {
			btnImport.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					DatabaseMappingViewComponent.this.setGlobalDBOperationObject(new DBOperationObject(DBOperationEventType.DB_OPERATION_MAPPING));
				}
			});
		}

		@Override
		public void handleEvents(String event) {
			// TODO Auto-generated method stub
			if(DBOperationEventType.DB_OPERATION_MAPPING.equals(event)) {
				_handleMapping();
			}
		}

		private void _handleMapping() {
			try{
				OWLOperationImpl owlFactory = new OWLOperationImpl(getOWLEditorKit());
				//create ontology
				owlFactory.createNewOntology();
				//create classes
				log.info("create classes");
				DatabaseMappingViewComponent.this.databaseInfos.sortTablesByCase();
				for(DBObjectTable table : DatabaseMappingViewComponent.this.databaseInfos.getTables()) {
					log.info(table.getName() + " : " + table.getTableCase());
					if(table.getTableCase() == DBObjectType.CASE_3) {
						owlFactory.addOWLOperation(owlFactory.createOWLClass(table.getName()).getOntologyChanges().get(0));
					} else if(table.getTableCase() == DBObjectType.CASE_2) {
						owlFactory.addOWLOperation(owlFactory.createOWLClass(table.getName()).getOntologyChanges().get(0));
					}
				}
				owlFactory.applyOWLOperations();
				//create subclass
				log.info("create subclass axioms");
				for(DBObjectTable table : DatabaseMappingViewComponent.this.databaseInfos.getTables()) {
					if(table.getTableCase() == DBObjectType.CASE_2) {
						handleCreateSubClassAxiom(owlFactory, table);
					}
				}
				owlFactory.applyOWLOperations();
				//create properties: object properties and data properties
				log.info("create properties and property axioms");
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
							}
							else if(!col.isPrimaryKey() && !col.isForeignKey()) {
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
							OWLAxiom inverse = (OWLAxiom)owlFactory.getInverseObjectPropertiesAxiom(op, opInverse);
							owlFactory.addOWLOperation(owlFactory.createOWLOntologyChange(inverse));
						}
					} else {
						log.info("the " + table.getName() + " is not classified yet ");
					}
				}
				owlFactory.applyOWLOperations();
			} catch(Exception e) {
				e.printStackTrace();
			}
			log.info("created...");
		}
		
		public void handleCreateSubClassAxiom(OWLOperationImpl owlFactory, DBObjectTable table) throws URISyntaxException {
			List<DBObjectPrimaryKey> pks = table.getPrimaryKeys();
			for(DBObjectPrimaryKey obj : pks) {
				DBObjectForeignKey fk = table.getFKByColumnName(obj.getColumn());
				OWLAxiom ax = (OWLAxiom) owlFactory.getSubClassAxiom(fk.getFKTable(), fk.getToTable());
				OWLOntologyChange change = owlFactory.createOWLOntologyChange(ax);
				owlFactory.addOWLOperation(change);
			}
		}
		
		public OWLObjectProperty handleCreateObjectProperty(OWLOperationImpl owlFactory, String propName, String domain, String range) throws OWLEntityCreationException, URISyntaxException {
			OWLEntityCreationSet<OWLObjectProperty> set = owlFactory.createOWLObjectProperty(propName);
			owlFactory.addOWLOperation(set.getOntologyChanges().get(0));
			OWLAxiom axDom = (OWLAxiom) owlFactory.getObjectPropertyDomainAxiom(set.getOWLEntity(), owlFactory.getOWLClass(domain));
			owlFactory.addOWLOperation(owlFactory.createOWLOntologyChange(axDom));
			OWLAxiom axRan = (OWLAxiom) owlFactory.getObjectPropertyRangeAxiom(set.getOWLEntity(), owlFactory.getOWLClass(range));
			owlFactory.addOWLOperation(owlFactory.createOWLOntologyChange(axRan));
			return set.getOWLEntity();
		}
		
		public OWLDataProperty handleCreateDataProperty(OWLOperationImpl owlFactory, DBObjectTable table, DBObjectColumn col, IRI iri) throws OWLEntityCreationException, URISyntaxException {
			OWLEntityCreationSet<OWLDataProperty> set = owlFactory.createOWLDataProperty(table.getName() + "_" + col.getName());
			owlFactory.addOWLOperation(set.getOntologyChanges().get(0));
			OWLAxiom axDom = (OWLAxiom) owlFactory.getDataPropertyDomainAxiom(set.getOWLEntity(), owlFactory.getOWLClass(table.getName()));
			owlFactory.addOWLOperation(owlFactory.createOWLOntologyChange(axDom));
			String type = DBDataType.getRealType(col.getTypeName());
			OWLDataRange dr = owlFactory.getDataRange(iri + "#" + type);
			OWLAxiom axRan = (OWLAxiom) owlFactory.getDataPropertyRangeAxiom(set.getOWLEntity(), dr);
			owlFactory.addOWLOperation(owlFactory.createOWLOntologyChange(axRan));
			return set.getOWLEntity();
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
		
		private JLabel lbMappingConfiguration;
		private ButtonGroup group;
		private JRadioButton rbActiveOntology;
		private JRadioButton rbNewOntology;
		private JCheckBox cbxIncludeContent;
		
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
			group = new ButtonGroup();
			rbActiveOntology = new JRadioButton("In the active ontology", false);
			group.add(rbActiveOntology);
			rbNewOntology = new JRadioButton("In the new ontology", true);
			group.add(rbNewOntology);
			cbxIncludeContent = new JCheckBox("Include table content", false);
			
			tfOntologyID = new JTextField(30);
			tfOntologyLocation = new JTextField(23);
			btnBrowse = new JButton("Browse");
		}

		@Override
		public void attachComponents() {
			formUtil.addLastField(lbMappingConfiguration, configPanel);
			formUtil.addLastField(rbActiveOntology, configPanel);
			formUtil.addLastField(rbNewOntology, configPanel);
			formUtil.addLastField(tfOntologyID, configPanel);
			formUtil.addLabel(tfOntologyLocation, configPanel);
			formUtil.addLastField(btnBrowse, configPanel);
			formUtil.addLastField(cbxIncludeContent, configPanel);
			this.add(configPanel, BorderLayout.NORTH);
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
