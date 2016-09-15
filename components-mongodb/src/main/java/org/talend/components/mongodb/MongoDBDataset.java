package org.talend.components.mongodb;


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
import org.talend.components.mongodb.connection.TMongoDBConnectionDefinition;
import org.talend.components.mongodb.input.TMongoDBInputDefinition;
import org.talend.components.mongodb.input.TMongoDBInputProperties;
import org.talend.components.mongodb.output.TMongoDBOutputDefinition;
import org.talend.components.mongodb.runtime.MongoDBSourceOrSink;
import org.talend.daikon.avro.AvroRegistry;
import org.talend.daikon.avro.converter.IndexedRecordConverter;
import org.talend.daikon.properties.Properties;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import aQute.bnd.annotation.component.Component;

@Component(name = Constants.DATASET_BEAN_PREFIX + MongoDBDataset.DATASET_NAME, provide =
        DatasetDefinition.class)
public class MongoDBDataset extends AbstractDatasetDefinition{

    public static final String DATASET_NAME = "MongoDBDataset"; //$NON-NLS-1$
    private static final Logger LOG = LoggerFactory.getLogger(MongoDBDataset.class);

    public MongoDBDataset() {
        super(DATASET_NAME);
    }

    public MongoDBDataset(String datasetName) {
        super(datasetName);
    }

    @Override
    public Class<? extends ComponentProperties> getPropertyClass() {
        return MongoDBSchemaProperties.class;
    }

    @Override
    public String[] getFamilies() {
        return new String[]{ "Databases/MongoDB", "Big Data/MongoDB"}; //$NON-NLS-1$  //$NON-NLS-2$
    }

    @Override
    public String[] getComponents() {
        return new String[]{new TMongoDBInputDefinition().getName(), new TMongoDBOutputDefinition().getName()};
    }

    @Override
    public String getSample(Properties datasetProps, Integer size) {
        /*try {
            MongoDBSchemaProperties datasetProperties = (MongoDBSchemaProperties) datasetProps;
            TMongoDBInputProperties inputProps = new TMongoDBInputProperties("inputProps");
            // TODO
            inputProps.setConnectionProperties(datasetProperties.getConnectionProperties());
            inputProps.setSchemaProperties(datasetProperties);
            inputProps.query.setValue("db." + datasetProperties.collection.getValue() + ".find()");
            TMongoDBInputDefinition inputDefinition = new TMongoDBInputDefinition();
            BoundedSource runtime = (BoundedSource) inputDefinition.getRuntime();
            runtime.initialize(null, inputProps);
            BoundedReader reader = runtime.createReader(null);
            boolean available = false;
            available = reader.start();
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
            }catch(IOException e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return null;
    }

    private Schema retrieveSchema(MongoDBSchemaProperties props) {
        try {
            TMongoDBConnectionDefinition connectionDefinition = new
                    TMongoDBConnectionDefinition();
            MongoDBSourceOrSink runtime = (MongoDBSourceOrSink) connectionDefinition
                    .getRuntime();
            runtime.initialize(null, (ComponentProperties) props);

            /*Schema schema = runtime.getSchema(null, props.keyspace.getValue(), props
                    .columnFamily.getValue());*/
            //return schema;
            return null;
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
        return null;
        //return JsonUtil.toJson(createProperties(), true);
    }

    @Override
    public String getMavenGroupId() {
        return "org.talend.components"; //$NON-NLS-1$
    }

    @Override
    public String getMavenArtifactId() {
        return "component-mongodb"; //$NON-NLS-1$
    }
}
