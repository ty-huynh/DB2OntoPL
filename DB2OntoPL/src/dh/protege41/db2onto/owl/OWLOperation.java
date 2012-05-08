package dh.protege41.db2onto.owl;

import java.net.URL;
import java.util.List;

import org.protege.editor.owl.model.entity.OWLEntityCreationException;
import org.protege.editor.owl.model.entity.OWLEntityCreationSet;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

public abstract interface OWLOperation {

	public abstract OWLAnnotationSubject getAnnotationSubject(OWLEntity entity);

	public abstract OWLAnnotationValue createAnnotationValue(boolean constant);

	public abstract OWLAnnotationValue createAnnotationValue(int constant);

	public abstract OWLAnnotationValue createAnnotationValue(float constant);

	public abstract OWLAnnotationValue createAnnotationValue(double constant);

	public abstract OWLAnnotationValue createAnnotationValue(String constant);

	public abstract OWLAnnotationValue createAnnotationValue(String constant,
			String language);

	public abstract OWLAnnotationValue createAnnotationValue(String constant,
			OWLDatatype dataType);

	public abstract OWLAnnotationValue createAnnotationValue(String constant,
			OWL2Datatype dataType);

	public abstract OWLAnnotationProperty getAnnotationProperty(String url)
			throws Exception;

	public abstract OWLAnnotationProperty getAnnotationProperty(URL url)
			throws Exception;

	public abstract OWLAnnotationProperty getAnnotationProperty(IRI iri);

	public abstract OWLClassAssertionAxiom createClassTypeForIndividual(
			OWLClass cls, OWLIndividual indi);

	public abstract OWLAnnotationAssertionAxiom createAnnotationForIndividual(
			OWLAnnotationSubject subject, OWLAnnotationProperty property,
			OWLAnnotationValue value);

	public OWLDataPropertyAssertionAxiom createDataPropertyAssertion(
			OWLDataProperty prop, OWLIndividual subject, OWLLiteral value);

	public abstract OWLEntityCreationSet<OWLNamedIndividual> createOWLIndividual(
			String name, IRI baseIri) throws OWLEntityCreationException;

	public abstract OWLEntityCreationSet<OWLNamedIndividual> createOWLIndividual(
			String name) throws OWLEntityCreationException;

	public abstract IRI getBaseIRI();

	public abstract OWLOntologyChange createOWLOntologyChange(OWLAxiom axiom);

	public abstract OWLOntologyChange createOWLAxiom(OWLAxiom axiom);

	public abstract void applyOWLOperations();

	public void clearOWLOperations();

	public abstract void addOWLOperation(OWLOntologyChange change);

	public List<OWLOntologyChange> getOWLOperations();
}
