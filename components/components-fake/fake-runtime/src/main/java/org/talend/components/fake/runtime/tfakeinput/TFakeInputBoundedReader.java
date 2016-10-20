package org.talend.components.fake.runtime.tfakeinput;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Random;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.avro.generic.IndexedRecord;
import org.apache.beam.sdk.io.BoundedSource;
import org.apache.beam.sdk.io.BoundedSource.BoundedReader;

public class TFakeInputBoundedReader extends BoundedReader<IndexedRecord> {

    private String outputSchemaString = null;

    private Schema outputSchema = null;

    private static String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    private Random rnd = new Random();

    private int numberOfElement = 0;

    private TFakeInputBoundedSource source;

    public TFakeInputBoundedReader(TFakeInputBoundedSource source) {
        this.source = source;
    }

    protected String getSaltString() {
        StringBuilder salt = new StringBuilder(18);
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }

    /**
     * This method will instantiate correct Avro Schema object. This is mandatory since the "Schema" object of Avro are
     * not serializable.
     */
    public void deserializeSchema() {
        if (outputSchema == null) {
            Schema.Parser parser = new Schema.Parser();
            outputSchema = parser.parse(outputSchemaString);
        }
    }

    public TFakeInputBoundedReader withOutputSchema(String outputSchema) {
        this.outputSchemaString = outputSchema;
        return this;
    }

    @Override
    public BoundedSource<IndexedRecord> getCurrentSource() {
        return source;
    }

    @Override
    public boolean start() throws IOException {
        deserializeSchema();
        return true;
    }

    @Override
    public boolean advance() throws IOException {
        numberOfElement++;
        return numberOfElement < 10;
    }

    @Override
    public IndexedRecord getCurrent() throws NoSuchElementException {
        GenericRecord outputRecord = new GenericRecordBuilder(outputSchema).set("value1", getSaltString())
                .set("value2", getSaltString()).build();
        return outputRecord;
    }

    @Override
    public void close() throws IOException {
    }

}
