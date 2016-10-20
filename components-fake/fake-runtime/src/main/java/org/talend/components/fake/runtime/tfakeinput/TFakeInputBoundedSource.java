package org.talend.components.fake.runtime.tfakeinput;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.generic.IndexedRecord;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.Coder;
import org.apache.beam.sdk.io.BoundedSource;
import org.apache.beam.sdk.options.PipelineOptions;

public class TFakeInputBoundedSource extends BoundedSource<IndexedRecord> {

    private String outputSchemaString = null;

    @Override
    public List<? extends BoundedSource<IndexedRecord>> splitIntoBundles(long desiredBundleSizeBytes, PipelineOptions options)
            throws Exception {
        return Arrays.asList(this);
    }

    @Override
    public long getEstimatedSizeBytes(PipelineOptions options) throws Exception {
        return 0;
    }

    @Override
    public boolean producesSortedKeys(PipelineOptions options) throws Exception {
        return false;
    }

    @Override
    public org.apache.beam.sdk.io.BoundedSource.BoundedReader<IndexedRecord> createReader(PipelineOptions options)
            throws IOException {
        return new TFakeInputBoundedReader(this).withOutputSchema(outputSchemaString);
    }

    @Override
    public void validate() {
    }

    @Override
    public Coder<IndexedRecord> getDefaultOutputCoder() {
        Schema.Parser parser = new Schema.Parser();
        return AvroCoder.of(IndexedRecord.class, parser.parse(outputSchemaString));
    }

    public TFakeInputBoundedSource withOutputSchema(Schema outputSchema) {
        this.outputSchemaString = outputSchema.toString();
        return this;
    }


}
