package dh.protege41.db2onto.tab;

import java.awt.BorderLayout;

import org.apache.log4j.Logger;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;

import dh.protege41.db2onto.event.dbobject.DBObject;
import dh.protege41.db2onto.event.dboperation.DBOperationObject;
import dh.protege41.owl.Metrics;


public class ExampleViewComponent extends DatabaseViewComponent {
    private static final long serialVersionUID = -4515710047558710080L;
    
    private static final Logger log = Logger.getLogger(ExampleViewComponent.class);
    
    private Metrics metricsComponent;

    @Override
    protected void disposeOWLView() {
    }

    @Override
    protected void initialiseOWLView() throws Exception {
        setLayout(new BorderLayout());
        metricsComponent = new Metrics(getOWLModelManager());
        add(metricsComponent, BorderLayout.CENTER);
        log.info("Example View Component initialized");
    }

	@Override
	protected DBObject updateView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected DBOperationObject performOperation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void updateHeader() {
		// TODO Auto-generated method stub
		
	}

    
}
