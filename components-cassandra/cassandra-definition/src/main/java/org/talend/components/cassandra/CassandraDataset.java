package org.talend.components.cassandra;

import aQute.bnd.annotation.component.Component;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.IndexedRecord;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.talend.components.api.Constants;
import org.talend.components.api.component.AbstractDatasetDefinition;
import org.talend.components.api.component.DatasetDefinition;
import org.talend.components.api.component.runtime.BoundedReader;
import org.talend.components.api.component.runtime.BoundedSource;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.cassandra.connection.TCassandraConnectionDefinition;
import org.talend.components.cassandra.input.TCassandraInputDefinition;
import org.talend.components.cassandra.input.TCassandraInputProperties;
import org.talend.components.cassandra.output.TCassandraOutputDefinition;
import org.talend.components.cassandra.runtime.CassandraSourceOrSink;
import org.talend.daikon.avro.AvroRegistry;
import org.talend.daikon.avro.converter.IndexedRecordConverter;
import org.talend.daikon.properties.Properties;
import org.talend.json.schema.JsonUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@Component(name = Constants.DATASET_BEAN_PREFIX + CassandraDataset.DATASET_NAME, provide =
        DatasetDefinition.class)
public class CassandraDataset extends AbstractDatasetDefinition {

    public static final String DATASET_NAME = "CassandraDataset"; //$NON-NLS-1$
    private static final Logger LOG = LoggerFactory.getLogger(CassandraDataset.class);

    public CassandraDataset() {
        super(DATASET_NAME);
    }

    public CassandraDataset(String datasetName) {
        super(datasetName);
    }

    @Override
    public Class<? extends ComponentProperties> getPropertyClass() {
        return CassandraSchemaProperties.class;
    }

    @Override
    public String[] getFamilies() {
        return new String[]{"Databases/Cassandra", "Big Data/Cassandra"}; //$NON-NLS-1$
        // $NON-NLS-2$
    }

    @Override
    public String[] getComponents() {
        return new String[]{new TCassandraInputDefinition().getName(), new
                TCassandraOutputDefinition().getName()};
    }

    @Override
    public String getSample(Properties datastetProps, Integer size) {
        try {
            CassandraSchemaProperties datasetProps = (CassandraSchemaProperties)datastetProps;
            TCassandraInputProperties inputProps = new TCassandraInputProperties("inputProps");
            inputProps.setConnectionProperties(datasetProps.getConnectionProperties());
            inputProps.setSchemaProperties(datasetProps);
            inputProps.query.setValue("select * from " + datasetProps.keyspace.getValue() + "." +
                    datasetProps.columnFamily.getValue());
            TCassandraInputDefinition inputDefinition = new TCassandraInputDefinition();
            BoundedSource runtime = (BoundedSource) inputDefinition.getRuntime();
            runtime.initialize(null, inputProps);
            BoundedReader reader = runtime.createReader(null);
            boolean available = reader.start();
            List<Object> values = new ArrayList<>();
            for (; available; available = reader.advance()) {
                values.add(reader.getCurrent());
                if (values.size() >= size) {
                    break;
                }
            }

            IndexedRecordConverter<Object, ? extends IndexedRecord> adapterFactory = null;
            Schema schema = null;
            GenericDatumWriter<IndexedRecord> indexedRecordGenericDatumWriter = null;
            List<String> jsonData = new ArrayList<>();

            for (Object value : values) {
                if (adapterFactory == null) {
                    adapterFactory = (IndexedRecordConverter<Object, ? extends IndexedRecord>)
                            new AvroRegistry()
                                    .createIndexedRecordConverter(value.getClass());
                    schema = retrieveSchema(datasetProps);
                    indexedRecordGenericDatumWriter = new GenericDatumWriter<>(schema);
                }

                IndexedRecord indexedRecord = adapterFactory.convertToAvro(value);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                Encoder encoder = EncoderFactory.get().jsonEncoder(schema, out, false);
                indexedRecordGenericDatumWriter.write(indexedRecord, encoder);
                encoder.flush();
                out.close();
                byte[] bytes = out.toByteArray();
                jsonData.add(new String(bytes));
            }
            return jsonData.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Schema retrieveSchema(CassandraSchemaProperties props) {
        try {
            TCassandraConnectionDefinition connectionDefinition = new
                    TCassandraConnectionDefinition();
            CassandraSourceOrSink runtime = (CassandraSourceOrSink) connectionDefinition
                    .getRuntime();
            runtime.initialize(null, (ComponentProperties) props);

            Schema schema = runtime.getSchema(null, props.keyspace.getValue(), props
                    .columnFamily.getValue());
            return schema;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public String getSchema(String datastoreJsonStr, String datasetJsonStr) {
        return null;
    }

    @Override
    public String getJSONSchema() {
        return JsonUtil.toJson(createProperties(), true);
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
