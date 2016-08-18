package org.talend.components.cassandra;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.IndexedRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.io.JsonEncoder;
import org.junit.Rule;
import org.junit.Test;
import org.talend.components.api.component.runtime.BoundedReader;
import org.talend.components.api.properties.ConnectionPropertiesProvider;
import org.talend.components.api.service.ComponentService;
import org.talend.components.api.test.AbstractComponentTest;
import org.talend.components.api.test.SimpleComponentRegistry;
import org.talend.components.api.test.SimpleComponentService;
import org.talend.components.cassandra.connection.TCassandraConnectionDefinition;
import org.talend.components.cassandra.input.TCassandraInputDefinition;
import org.talend.components.cassandra.input.TCassandraInputProperties;
import org.talend.components.cassandra.output.TCassandraOutputDefinition;
import org.talend.components.cassandra.runtime_3_0.CassandraAvroRegistry;
import org.talend.components.cassandra.runtime_3_0.CassandraSource;
import org.talend.daikon.avro.SchemaConstants;
import org.talend.daikon.avro.converter.IndexedRecordConverter;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.test.PropertiesTestUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class CasssandraReadSampleTestIT extends AbstractComponentTest {
    public static final String COLUMN_FAMILY = "columnFamily";

    public static final String KS_NAME = CasssandraReadSampleTestIT.class.getSimpleName().toLowerCase();
    // start the embedded cassandra server and init with data
    public static final String TABLE_SOURCE = "t_src";
    public static final String TABLE_DEST = "t_dest";
    public static final int ROW_COUNT = 100;

    @Rule
    public EmbeddedCassandraSimpleDataResource mCass = new EmbeddedCassandraSimpleDataResource
            (KS_NAME, TABLE_SOURCE, TABLE_DEST, ROW_COUNT);


    @Test
    public void testAvro() throws Throwable{
        TCassandraInputProperties props = (TCassandraInputProperties)getComponentService().getComponentProperties(TCassandraInputDefinition.COMPONENT_NAME);
        setupSchemaProps(props, false, KS_NAME, TABLE_SOURCE);
        setupQueryProps(props);
        CassandraSource cassandraSource = new CassandraSource();
        cassandraSource.initialize(null, props);
        cassandraSource.validate(null);
        BoundedReader reader = cassandraSource.createReader(null);
        assertTrue(reader.start());
        Object current = reader.getCurrent();
        assertNotNull(current);
        assertTrue(reader.advance());
        reader.close();

        IndexedRecordConverter<Object, ? extends IndexedRecord> adapterFactory =
                (IndexedRecordConverter<Object, ? extends IndexedRecord>) CassandraAvroRegistry
                        .get().createIndexedRecordConverter(current.getClass());

        IndexedRecord indexedRecord = adapterFactory.convertToAvro(current);
        Schema schema = cassandraSource.getSchema(null, KS_NAME, TABLE_SOURCE);
        GenericDatumWriter<IndexedRecord> indexedRecordGenericDatumWriter = new
                GenericDatumWriter<>(schema);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Encoder encoder = EncoderFactory.get().jsonEncoder(schema, out, false);

        indexedRecordGenericDatumWriter.write(indexedRecord, encoder);
        encoder.flush();
        out.close();
        byte[] bytes = out.toByteArray();

        System.out.println(new String(bytes));
    }


    private ComponentService componentService;

    @Override
    public ComponentService getComponentService() {
        if (componentService == null) {
            SimpleComponentRegistry simpleComponentRegistry = new SimpleComponentRegistry();
            simpleComponentRegistry.addComponent(TCassandraConnectionDefinition.COMPONENT_NAME, new TCassandraConnectionDefinition());
            simpleComponentRegistry.addComponent(TCassandraInputDefinition.COMPONENT_NAME, new TCassandraInputDefinition());
            simpleComponentRegistry.addComponent(TCassandraOutputDefinition.COMPONENT_NAME, new TCassandraOutputDefinition());
            componentService = new SimpleComponentService(simpleComponentRegistry);
        }
        return componentService;
    }

    protected void setupSchemaProps(CassandraIOBasedProperties props, boolean includeAllFields, String ks, String cf) throws Throwable {
        initConnectionProps(props);
        props.getSchemaProperties().keyspace.setValue(ks);
        props.getSchemaProperties().columnFamily.setValue(cf);
        if(includeAllFields){
            props.getSchemaProperties().main.schema.setValue(SchemaBuilder.builder().record("test")
                    .prop(SchemaConstants.INCLUDE_ALL_FIELDS, "true").fields().endRecord());
        }else {
            Form schemaRefForm = props.getSchemaProperties().getForm(Form.REFERENCE);
            PropertiesTestUtils.checkAndAfter(getComponentService(), schemaRefForm, COLUMN_FAMILY, schemaRefForm.getProperties());
        }
    }

    protected static void initConnectionProps(ConnectionPropertiesProvider<CassandraConnectionProperties> props){
        props.getConnectionProperties().host.setValue(EmbeddedCassandraResource.HOST);
        props.getConnectionProperties().port.setValue(EmbeddedCassandraResource.PORT);
    }

    private void setupQueryProps(TCassandraInputProperties props) {
        props.query.setValue("select * from " + props.schemaProperties.columnFamily.getStringValue());

    }
}
