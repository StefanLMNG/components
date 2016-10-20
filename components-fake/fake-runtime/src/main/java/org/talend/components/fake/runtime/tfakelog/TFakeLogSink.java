package org.talend.components.fake.runtime.tfakelog;

import org.apache.avro.generic.IndexedRecord;
import org.apache.beam.sdk.io.Sink;
import org.apache.beam.sdk.options.PipelineOptions;

public class TFakeLogSink extends Sink<IndexedRecord> {


    @Override
    public void validate(PipelineOptions options) {
        
    }

    @Override
    public org.apache.beam.sdk.io.Sink.WriteOperation<IndexedRecord, ?> createWriteOperation(PipelineOptions options) {
        return new TFakeLogWriteOperation(this);
    }
}
