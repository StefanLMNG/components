package org.talend.components.mongodb.runtime;

import org.apache.avro.Schema;
import org.talend.components.api.component.runtime.SourceOrSink;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.api.properties.ConnectionPropertiesProvider;
import org.talend.components.mongodb.MongoDBConnectionProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.properties.ValidationResult;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;
import java.util.List;

/**
 * Created by slemoing on 8/8/2016.
 */
public class MongoDBSourceOrSink implements SourceOrSink{

    protected static final String DATABASE_GLOBALMAP_KEY = "database"; //FIXME only for Studio?

    protected transient ConnectionPropertiesProvider<MongoDBConnectionProperties> properties;

    @Override
    public void initialize(RuntimeContainer container, ComponentProperties properties) {
        this.properties = (ConnectionPropertiesProvider<MongoDBConnectionProperties>) properties;
    }

    @Override
    public ValidationResult validate(RuntimeContainer container) {
        return null;
    }

    protected MongoClient connect(RuntimeContainer container) throws IOException {
        String referencedComponentId = properties.getConnectionProperties().getReferencedComponentId();
        MongoDBConnectionProperties connProps = properties.getConnectionProperties();
        String uri = "mongodb://";
        if (referencedComponentId != null) {
            if (connProps.replicaSet.getValue()){
                // loop on replicaSets values
            } else {
                uri += connProps.host + ":" + connProps.port;
            }
            uri += "/" + connProps.database;
            /*
            if (connProps.ssl.getValue()){
                uri += "?ssl=true"
            }
             */
            // options part
            if (connProps.needAuth.getValue()) {
                uri += "";
            }

        }
        return new MongoClient(new MongoClientURI(uri));
    }



    public ConnectionPropertiesProvider<MongoDBConnectionProperties> getProperties() {
        return properties;
    }

    @Override
    public List<NamedThing> getSchemaNames(RuntimeContainer container) throws IOException {
        return null;
    }

    @Override
    public Schema getEndpointSchema(RuntimeContainer container, String schemaName) throws IOException {
        return null;
    }
}
