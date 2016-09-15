package org.talend.components.mongodb.output;

import org.talend.components.api.component.OutputComponentDefinition;
import org.talend.components.api.component.runtime.Sink;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.mongodb.MongoDBDefinition;

public class TMongoDBOutputDefinition extends MongoDBDefinition implements OutputComponentDefinition{
    public static final String COMPONENT_NAME = "tMongoDBOutput"; //$NON-NLS-1$

    public TMongoDBOutputDefinition() {
        super(COMPONENT_NAME);
    }

    @Override
    public Class<? extends ComponentProperties> getPropertyClass() {
        return null;
    }

    @Override
    // TODO Add return MongoDBSink
    public Sink getRuntime() {
        return null;
    }
}
