package dh.protege41.db2onto.owl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComboBox;

import org.apache.log4j.Logger;
import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.entity.OWLEntityCreationException;
import org.protege.editor.owl.model.entity.OWLEntityCreationSet;
import org.protege.editor.owl.model.entity.OWLEntityFactory;
import org.protege.editor.owl.model.util.OWLDataTypeUtils;
import org.protege.editor.owl.ui.UIHelper;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import dh.protege41.db2onto.tab.ui.util.dialog.DialogUtility;

public class OWLOperationImpl implements OWLOperation {
	
	private static final Logger log = Logger.getLogger(OWLOperationImpl.class);
	
	private OWLEditorKit _owlEditorKit;
	private OWLModelManager _owlModelManager;
	private OWLEntityFactory _owlEntityFactory;
	private OWLDataFactory _owlDataFactory;
//	private OWLOntology _activeOntology;
	private List<OWLOntologyChange> operations = new ArrayList<OWLOntologyChange>();
	
	
	public OWLOperationImpl() {
	}
	public OWLModelManager getModelManager() {
		return this._owlModelManager;
	}
	public OWLOperationImpl(OWLEditorKit ekit) {
		_owlEditorKit = ekit;
		_owlModelManager = _owlEditorKit.getOWLModelManager();
		_owlEntityFactory = _owlModelManager.getOWLEntityFactory();
		_owlDataFactory = _owlModelManager.getOWLDataFactory();
//		_activeOntology = _owlModelManager.getActiveOntology();
	}

	@Override
	public OWLAnnotationSubject getAnnotationSubject(OWLEntity entity) {
		OWLAnnotationSubject subject = entity.getIRI();
		return subject;
	}
	
	@Override
	public OWLAnnotationValue createAnnotationValue(String constant,
			String language) {
		return _owlDataFactory.getOWLLiteral(constant, language);
	}

	@Override
	public OWLAnnotationValue createAnnotationValue(String constant,
			OWLDatatype dataType) {
		return _owlDataFactory.getOWLLiteral(constant, dataType);
	}

	@Override
	public OWLAnnotationValue createAnnotationValue(String constant,
			OWL2Datatype dataType) {
		return _owlDataFactory.getOWLLiteral(constant, dataType);
	}
	

	@Override
	public OWLAnnotationValue createAnnotationValue(boolean constant) {
		// TODO Auto-generated method stub
		return _owlDataFactory.getOWLLiteral(constant);
	}

	@Override
	public OWLAnnotationValue createAnnotationValue(int constant) {
		// TODO Auto-generated method stub
		return _owlDataFactory.getOWLLiteral(constant);
	}

	@Override
	public OWLAnnotationValue createAnnotationValue(float constant) {
		// TODO Auto-generated method stub
		return _owlDataFactory.getOWLLiteral(constant);
	}

	@Override
	public OWLAnnotationValue createAnnotationValue(double constant) {
		// TODO Auto-generated method stub
		return _owlDataFactory.getOWLLiteral(constant);
	}

	@Override
	public OWLAnnotationValue createAnnotationValue(String constant) {
		return _owlDataFactory.getOWLLiteral(constant);
	}
	
	@Override
	public OWLAnnotationProperty getAnnotationProperty(String url) throws Exception {
		return getAnnotationProperty(new URL(url));
	}

	@Override
	public OWLAnnotationProperty getAnnotationProperty(URL url) throws Exception {
		return getAnnotationProperty(IRI.create(url));
	}

	@Override
	public OWLAnnotationProperty getAnnotationProperty(IRI iri){
		return _owlDataFactory.getOWLAnnotationProperty(iri);
	}

	@Override
	public OWLClassAssertionAxiom createClassTypeForIndividual(OWLClass cls,
			OWLIndividual indi) {
		return _owlDataFactory.getOWLClassAssertionAxiom(cls, indi);
	}

	@Override
	public OWLAnnotationAssertionAxiom createAnnotationForIndividual(
			OWLAnnotationSubject subject, OWLAnnotationProperty property,
			OWLAnnotationValue value) {
		return _owlDataFactory.getOWLAnnotationAssertionAxiom(property, subject, value);
	}
	
	public OWLDataPropertyAssertionAxiom createDataPropertyAssertion(OWLDataProperty prop, OWLIndividual subject, OWLLiteral value) {
		return _owlDataFactory.getOWLDataPropertyAssertionAxiom(prop, subject, value);
	}
	@Override
	public OWLEntityCreationSet<OWLNamedIndividual> createOWLIndividual(
			String name, IRI baseIri) throws OWLEntityCreationException {
		if(baseIri != null) {
			baseIri = getBaseIRI();
		}
		return _owlEntityFactory.createOWLIndividual(name, baseIri);
	}

	@Override
	public OWLEntityCreationSet<OWLNamedIndividual> createOWLIndividual(
			String name) throws OWLEntityCreationException {
		return createOWLIndividual(name, getBaseIRI());
	}

	@Override
	public IRI getBaseIRI() {
		return _owlModelManager.getActiveOntology().getOntologyID().getOntologyIRI();
	}

	@SuppressWarnings("unchecked")
	public IRI getDataTypeBaseIRI() {
		List datatypeList = new ArrayList(new OWLDataTypeUtils(_owlModelManager.getOWLOntologyManager()).getKnownDatatypes(_owlModelManager.getActiveOntologies()));
		Iterator loop = datatypeList.iterator();
		while(loop.hasNext()) {
			Object o = loop.next();
			if(o instanceof OWLDatatype) {
				if(((OWLDatatype)o).isInteger()) {
					OWLDatatype dt = ((OWLDatatype)o);
					String iri = dt.getIRI().toString().substring(0, dt.getIRI().toString().indexOf('#'));
					return IRI.create(iri);
				}
			}
		}
		return null;
	}
	
	@Override
	public OWLOntologyChange createOWLOntologyChange(OWLAxiom axiom) {
		return new AddAxiom(_owlModelManager.getActiveOntology(), axiom);
	}

	@Override
	public OWLOntologyChange createOWLAxiom(OWLAxiom axiom) {
		return createOWLOntologyChange(axiom);
	}

	@Override
	public void applyOWLOperations() {
		_owlModelManager.applyChanges(operations);
		operations.clear();
	}
	
	@Override
	public void clearOWLOperations() {
		this.operations.clear();
	}
	
	@Override
	public void addOWLOperation(OWLOntologyChange change) {
		operations.add(change);
	}
	
	public List<OWLOntologyChange> getOWLOperations() {
		return operations;
	}
	
	public OWLOntology createNewOntology(OWLOntologyID ontoID, URI location) {
		try {
			return _owlModelManager.createNewOntology(ontoID, location);
		} catch (OWLOntologyCreationException e) {
			DialogUtility.showError(e.getMessage());
		}
		return null;
	}
	
	public OWLOntology createNewOntology(String ontoID, String location) throws OWLOntologyCreationException {
		OWLOntologyID owlOntologyID = new OWLOntologyID(IRI.create(ontoID));
		File file = new File(location);
		return _owlModelManager.createNewOntology(owlOntologyID, file.toURI());
	}
	/**
	 * create new ontology
	 * @return
	 */
	public OWLOntology createNewOntology() {
		try {
			log.info(_owlModelManager.getActiveOntology().getOntologyID());
			String ontoName = "Ontology_creation.owl";
			OWLOntologyID ontoID = new OWLOntologyID(IRI.create(new URI("http://www.semanticweb.org/ontologies/2012/3/17/" + ontoName)));
			File file = new File("E:\\ty\\ontologies\\" + ontoName);
			return _owlModelManager.createNewOntology(ontoID, file.toURI());
		} catch (OWLOntologyCreationException e) {
			DialogUtility.showError(e.getMessage());
		} catch (URISyntaxException e1) {
			DialogUtility.showError(e1.getMessage());
		}
		return null;
	}
	/* CLASSES */
	/**
	 * create new owl class
	 * @throws OWLEntityCreationException 
	 */
	public OWLEntityCreationSet<OWLClass> createOWLClass(String className, IRI baseIRI) throws OWLEntityCreationException {
		if(baseIRI == null) {
			baseIRI = getBaseIRI();
		}
		return _owlEntityFactory.createOWLClass(className, baseIRI);
	}
	
	/**
	 * create new owl class
	 * @throws OWLEntityCreationException 
	 */
	public OWLEntityCreationSet<OWLClass> createOWLClass(String className) throws OWLEntityCreationException {
		return this.createOWLClass(className, null);
	}
	
	public OWLClass getOWLClass(String className) throws URISyntaxException {
		return _owlDataFactory.getOWLClass(getEntityIRI(className));
	}
	
	public OWLSubClassOfAxiom getSubClassAxiom(OWLClass sub, OWLClass sup) {
		return _owlDataFactory.getOWLSubClassOfAxiom(sub, sup);
	}
	
	public OWLSubClassOfAxiom getSubClassAxiom(String subClass, String supClass) throws URISyntaxException {
		return this.getSubClassAxiom(getOWLClass(subClass), getOWLClass(supClass));
	}
	
	public IRI getEntityIRI(String entityName) throws URISyntaxException {
		return IRI.create(new URI(getBaseIRI() + "#" + entityName));
	}
	/* PROPERTIES */
	public OWLObjectProperty getOWLObjectProperty(String propName) throws URISyntaxException {
		return _owlDataFactory.getOWLObjectProperty(getEntityIRI(propName));
	}
	public OWLDataProperty getOWLDataProperty(String propName) throws URISyntaxException {
		return _owlDataFactory.getOWLDataProperty(getEntityIRI(propName));
	}
	
	public OWLEntityCreationSet<OWLObjectProperty> createOWLObjectProperty(String propName, IRI baseIRI) throws OWLEntityCreationException {
		if(baseIRI == null) {
			baseIRI = getBaseIRI();
		}
		return _owlEntityFactory.createOWLObjectProperty(propName, baseIRI);
	}
	
	public OWLEntityCreationSet<OWLObjectProperty> createOWLObjectProperty(String propName) throws OWLEntityCreationException {
		return this.createOWLObjectProperty(propName, null);
	}
	
	public OWLEntityCreationSet<OWLDataProperty> createOWLDataProperty(String propName, IRI baseIRI) throws OWLEntityCreationException {
		if(baseIRI == null) {
			baseIRI = getBaseIRI();
		}
		return _owlEntityFactory.createOWLDataProperty(propName, baseIRI);
	}
	
	public OWLEntityCreationSet<OWLDataProperty> createOWLDataProperty(String propName) throws OWLEntityCreationException {
		return this.createOWLDataProperty(propName, null);
	}
	
	public OWLObjectPropertyDomainAxiom getObjectPropertyDomainAxiom(OWLObjectPropertyExpression prop, OWLClassExpression cls) {
		return _owlDataFactory.getOWLObjectPropertyDomainAxiom(prop, cls);
	}
	
	public OWLObjectPropertyRangeAxiom getObjectPropertyRangeAxiom(OWLObjectPropertyExpression prop, OWLClassExpression cls) {
		return _owlDataFactory.getOWLObjectPropertyRangeAxiom(prop, cls);
	}
	
	public OWLDataPropertyDomainAxiom getDataPropertyDomainAxiom(OWLDataPropertyExpression prop, OWLClass cls) {
		return _owlDataFactory.getOWLDataPropertyDomainAxiom(prop, cls);
	}
	
	public OWLDataRange getDataRange(IRI iri) {
		return _owlDataFactory.getOWLDatatype(iri);
	}
	
	public OWLDataRange getDataRange(String iri) {
		return this.getDataRange(IRI.create(iri));
	}
	
	public OWLDataPropertyRangeAxiom getDataPropertyRangeAxiom(OWLDataPropertyExpression prop, OWLDataRange range) {
		return _owlDataFactory.getOWLDataPropertyRangeAxiom(prop, range);
	}
	
	public OWLInverseObjectPropertiesAxiom getInverseObjectPropertiesAxiom(OWLObjectPropertyExpression propForward, OWLObjectPropertyExpression propInverse) {
		return _owlDataFactory.getOWLInverseObjectPropertiesAxiom(propForward, propInverse);
	}
	
	public OWLObjectMinCardinality getObjectMinCardinality(int cardinality, OWLObjectPropertyExpression prop) {
		return _owlDataFactory.getOWLObjectMinCardinality(cardinality, prop);
	}
	
	public OWLObjectMaxCardinality getObjectMaxCardinality(int cardinality, OWLObjectPropertyExpression prop) {
		return _owlDataFactory.getOWLObjectMaxCardinality(cardinality, prop);
	}
	
	public OWLDataMinCardinality getDataMinCardinality(int cardinality, OWLDataPropertyExpression dataProp) {
		return _owlDataFactory.getOWLDataMinCardinality(cardinality, dataProp);
	}
	
	public OWLDataMaxCardinality getDataMaxCardinality(int cardinality, OWLDataPropertyExpression dataProp) {
		return _owlDataFactory.getOWLDataMaxCardinality(cardinality, dataProp);
	}
}
