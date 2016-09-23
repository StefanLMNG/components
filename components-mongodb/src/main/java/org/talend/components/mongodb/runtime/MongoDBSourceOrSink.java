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
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;
import java.util.ArrayList;
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
        try {
            connect(container);
        } catch (IOException ex) {
            return new ValidationResult().setStatus(ValidationResult.Result.ERROR).setMessage(ex.getMessage());
        }
        return ValidationResult.OK;
    }

    protected MongoClient connect(RuntimeContainer container) throws IOException {
        String referencedComponentId = properties.getConnectionProperties().getReferencedComponentId();
        if (referencedComponentId != null) {
            // TODO ??? check what have been done for the cassandra TCOMP
        }
        try {
            return new MongoClient(new MongoClientURI(getUri()));
        } catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
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

    // TODO what kind of check we need to do
    boolean doValidate (String name, ComponentProperties properties){
        try {
            MongoDBSourceOrSink mdbsos = new MongoDBSourceOrSink();
            mdbsos.initialize(null, properties);
            mdbsos.getMongoClient(null);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // TODO URI Generator
    public String getUri(){
        MongoDBConnectionProperties connProps = properties.getConnectionProperties();
        String uri = "mongodb://";
        // manage auth option
        if (connProps.needAuth.getValue()){
            uri += connProps.userPassword.getUserPasswordProperties();
        }
        // TODO add replica set mangement
        if (connProps.replicaSet.getValue()){
            uri += connProps.replicaTable.getReplica();
        } else {
            uri += connProps.host.getValue() + ":" + connProps.port.getValue();
        }
        uri += "/" + connProps.database.getValue();

        // TODO manage uri options
        return uri;
    }
    public MongoClient getMongoClient(RuntimeContainer container) throws IOException {
        MongoClient mongoClient = new MongoClient(new MongoClientURI(getUri()));
        return mongoClient;
    }
    public MongoDatabase getDatabase(RuntimeContainer container, String database) throws IOException {
        MongoDBConnectionProperties connProps = properties.getConnectionProperties();
        MongoDatabase mongoDatabase = getMongoClient(container).getDatabase(database);
        return mongoDatabase;
    }

    public List<String> getDatabaseNames(RuntimeContainer container) throws IOException {
        List<String> listMongoDBdatabases = new ArrayList<String>();
        MongoCursor<String> dbsCursor = getMongoClient(container).listDatabaseNames().iterator();
        while(dbsCursor.hasNext()) {
            listMongoDBdatabases.add(dbsCursor.next());
        }
        return listMongoDBdatabases;
    }

    public List<String> getCollectionsName (RuntimeContainer container, String database) throws IOException {
        MongoDBConnectionProperties connProps = properties.getConnectionProperties();
        List<String> listMongoDBcollections = new ArrayList<String>();
        MongoCursor<String> dbsCursor = getMongoClient(container)
                .getDatabase(connProps.database.getValue())
                .listCollectionNames().iterator();
        while(dbsCursor.hasNext()) {
            listMongoDBcollections.add(dbsCursor.next());
        }
        return listMongoDBcollections;
    }

    // TODO check if
    // schema
    public Schema getSchema(RuntimeContainer container, String collection) throws IOException {
        return null;
    }
}
