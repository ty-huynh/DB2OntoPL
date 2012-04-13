package dh.protege41.db2onto.owl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.entity.OWLEntityCreationException;
import org.protege.editor.owl.model.entity.OWLEntityCreationSet;
import org.protege.editor.owl.model.entity.OWLEntityFactory;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

public class OWLOperationImpl implements OWLOperation {
	private OWLEditorKit _owlEditorKit;
	private OWLModelManager _owlModelManager;
	private OWLEntityFactory _owlEntityFactory;
	private OWLDataFactory _owlDataFactory;
	private OWLOntology _activeOntology;
	private List<OWLOntologyChange> operations = new ArrayList<OWLOntologyChange>();
	
	public OWLOperationImpl() {
	}
	
	public OWLOperationImpl(OWLEditorKit ekit) {
		_owlEditorKit = ekit;
		_owlModelManager = _owlEditorKit.getOWLModelManager();
		_owlEntityFactory = _owlModelManager.getOWLEntityFactory();
		_owlDataFactory = _owlModelManager.getOWLDataFactory();
		_activeOntology = _owlModelManager.getActiveOntology();
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
		return _activeOntology.getOntologyID().getOntologyIRI();
	}

	@Override
	public OWLOntologyChange createOWLOntologyChange(OWLAxiom axiom) {
		return new AddAxiom(_activeOntology, axiom);
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
	public void addOWLOperation(OWLOntologyChange change) {
		operations.add(change);
	}
	
	public List<OWLOntologyChange> getOWLOperations() {
		return operations;
	}
}
