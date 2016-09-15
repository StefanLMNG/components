package org.talend.components.mongodb.connection;

import org.talend.components.api.component.EndpointComponentDefinition;
import org.talend.components.api.component.runtime.SourceOrSink;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.mongodb.MongoDBConnectionProperties;
import org.talend.components.mongodb.MongoDBDefinition;

/**
 * Created by slemoing on 8/2/2016.
 */
public class TMongoDBConnectionDefinition extends MongoDBDefinition implements EndpointComponentDefinition{

    public static final String COMPONENT_NAME = "tMongoDBConnection"; //$NON-NLS-1$

    public TMongoDBConnectionDefinition() {
        super(COMPONENT_NAME);
    }

    public SourceOrSink getRuntime() {
        // TO DO add return MongoDBSourceOrSink
        try {
            // TODO fix the correct version for runtime
            return (SourceOrSink) Class.forName("org.talend.components.mongodb.runtime_3_2.MonogDBSourceOrSink").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Class<? extends ComponentProperties> getPropertyClass() {
        return MongoDBConnectionProperties.class;
    }
}
