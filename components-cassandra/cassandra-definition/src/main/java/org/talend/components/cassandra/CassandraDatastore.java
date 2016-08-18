package org.talend.components.cassandra;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.talend.components.api.Constants;
import org.talend.components.api.component.AbstractDatastoreDefinition;
import org.talend.components.api.component.DatastoreDefinition;
import org.talend.components.api.component.runtime.SourceOrSink;
import org.talend.components.api.properties.ComponentProperties;

import aQute.bnd.annotation.component.Component;
import org.talend.components.cassandra.connection.TCassandraConnectionDefinition;
import org.talend.components.cassandra.runtime.CassandraSourceOrSink;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.ValidationResult;
import org.talend.json.schema.JsonUtil;

@Component(name = Constants.DATASTORE_BEAN_PREFIX + CassandraDatastore.DATASTORE_NAME, provide = DatastoreDefinition.class)
public class CassandraDatastore extends AbstractDatastoreDefinition {

    private static final Logger LOG = LoggerFactory.getLogger(CassandraDatastore.class);

    public static final String DATASTORE_NAME = "CassandraDatastore"; //$NON-NLS-1$

    public CassandraDatastore() {
        super(DATASTORE_NAME);
    }

    public CassandraDatastore(String datastoreName) {
        super(datastoreName);
    }

    @Override
    public Class<? extends ComponentProperties> getPropertyClass() {
        return CassandraConnectionProperties.class;
    }

    @Override
    public String[] getFamilies() {
        return new String[] { "Databases/Cassandra", "Big Data/Cassandra" }; //$NON-NLS-1$  //$NON-NLS-2$
    }

    @Override
    public String[] getDatasets() {
        return new String[] { new CassandraDataset().getName() };
    }

    @Override
    public String validate(Properties properties) {
        try {
            TCassandraConnectionDefinition connectionDefinition = new
                    TCassandraConnectionDefinition();
            SourceOrSink runtime = connectionDefinition.getRuntime();
            runtime.initialize(null, (ComponentProperties)properties);
            ValidationResult validate = runtime.validate(null);
            return "{\"status\":\"ok\"}";//TODO(bchen) change it!
        } catch (Exception e) {
            return "{\"status\":\"error\", \"message\":"+e.getMessage()+"}";
        }
    }

    @Override
    public String getJSONSchema() {
        return JsonUtil.toJson(createProperties(), true); //true means contains json-ui-schema
    }

    @Override
    public String getMavenGroupId() {
        return "org.talend.components"; //$NON-NLS-1$
    }

    @Override
    public String getMavenArtifactId() {
        return "component-cassandra"; //$NON-NLS-1$
    }

}
