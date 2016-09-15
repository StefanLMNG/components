package org.talend.components.cassandra.runtime_3_0;

import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.IndexedRecord;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.junit.Test;
import org.talend.components.api.component.runtime.BoundedReader;
import org.talend.components.cassandra.CassandraTestBase;
import org.talend.components.cassandra.input.TCassandraInputDefinition;
import org.talend.components.cassandra.input.TCassandraInputProperties;
import org.talend.components.cassandra.runtime_3_0.CassandraSource;
import org.talend.daikon.avro.converter.IndexedRecordConverter;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;

public class CassandraReaderTestIT extends CassandraTestBase {

    @Test
    public void testRead() throws Throwable {
        TCassandraInputProperties props = (TCassandraInputProperties)getComponentService().getComponentProperties(TCassandraInputDefinition.COMPONENT_NAME);
        setupSchemaProps(props, false, KS_NAME, "example_src");
        setupQueryProps(props);
        CassandraSource cassandraSource = new CassandraSource();
        cassandraSource.initialize(null, props);
        cassandraSource.validate(null);
        BoundedReader reader = cassandraSource.createReader(null);
        assertTrue(reader.start());
        assertNotNull(reader.getCurrent());
        assertTrue(reader.advance());
        reader.close();
    }

    @Test
    public void testReadWithEmptySchema() throws Throwable {
    }

    private void setupQueryProps(TCassandraInputProperties props) {
        props.query.setValue("select * from " + props.schemaProperties.columnFamily.getStringValue());
    }

}