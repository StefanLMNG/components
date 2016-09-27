package ${package};

import aQute.bnd.annotation.component.Component;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.IndexedRecord;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ${package}.api.Constants;
import ${package}.api.component.AbstractDatasetDefinition;
import ${package}.api.component.DatasetDefinition;
import ${package}.api.component.runtime.BoundedReader;
import ${package}.api.component.runtime.BoundedSource;
import ${package}.api.properties.ComponentProperties;
import ${package}.connection.T${componentClass}ConnectionDefinition;
import ${package}.input.T${componentClass}InputDefinition;
import ${package}.input.T${componentClass}InputProperties;
import ${package}.output.T${componentClass}OutputDefinition;
import ${package}.runtime.${componentClass}SourceOrSink;
import org.talend.daikon.avro.AvroRegistry;
import org.talend.daikon.avro.converter.IndexedRecordConverter;
import org.talend.daikon.properties.Properties;
import org.talend.json.schema.JsonUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@Component(name = Constants.DATASET_BEAN_PREFIX + ${componentClass}Dataset.DATASET_NAME, provide =
        DatasetDefinition.class)
public class ${componentClass}Dataset extends AbstractDatasetDefinition {

    public static final String DATASET_NAME = "${componentClass}Dataset"; //$NON-NLS-1$
    private static final Logger LOG = LoggerFactory.getLogger(${componentClass}Dataset.class);

    public ${componentClass}Dataset() {
        super(DATASET_NAME);
    }

    public ${componentClass}Dataset(String datasetName) {
        super(datasetName);
    }

    @Override
    public Class<? extends ComponentProperties> getPropertyClass() {
        return ${componentClass}SchemaProperties.class;
    }

    @Override
    public String[] getFamilies() {
        // TODO Add families : example
        // return new String[]{"Databases/${componentClass}", "Big Data/${componentClass}"}; //$NON-NLS-1$
        return null
        // $NON-NLS-2$
    }

    @Override
    public String[] getComponents() {
        return new String[]{new T${componentClass}InputDefinition().getName(), new
                T${componentClass}OutputDefinition().getName()};
    }

    @Override
    public String getSample(Properties datastetProps, Integer size) {
        /* EXAMPLE FOR CASSANDRA
        try {
            ${componentClass}SchemaProperties datasetProps = (${componentClass}SchemaProperties)datastetProps;
            T${componentClass}InputProperties inputProps = new T${componentClass}InputProperties("inputProps");
            inputProps.setConnectionProperties(datasetProps.getConnectionProperties());
            inputProps.setSchemaProperties(datasetProps);
            inputProps.query.setValue("select * from " + datasetProps.keyspace.getValue() + "." +
                    datasetProps.columnFamily.getValue());
            T${componentClass}InputDefinition inputDefinition = new T${componentClass}InputDefinition();
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
        */
        return null;
    }

    // TODO implemnet getSchema
    @Override
    public String getSchema(String datastoreJsonStr, String datasetJsonStr) {
        return null;
    }

    // TODO implemnet getJSONSSchema
    @Override
    public String getJSONSchema() {
        return JsonUtil.toJson(createProperties(), true);
    }

    // TODO implemnet getMavenGroupId
    @Override
    public String getMavenGroupId() {
        return "${package}"; //$NON-NLS-1$
    }


    @Override
    public String getMavenArtifactId() {
        return "${artifactId}"; //$NON-NLS-1$
    }
}
