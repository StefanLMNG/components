package org.talend.components.fake.runtime.tfakelog;

import org.apache.avro.generic.IndexedRecord;
import org.apache.beam.sdk.coders.Coder;
import org.apache.beam.sdk.coders.DoubleCoder;
import org.apache.beam.sdk.io.Sink;
import org.apache.beam.sdk.io.Sink.WriteOperation;
import org.apache.beam.sdk.io.Sink.Writer;
import org.apache.beam.sdk.options.PipelineOptions;

public class TFakeLogWriteOperation extends WriteOperation<IndexedRecord, Double> {

    private Sink<IndexedRecord> sink;

    public TFakeLogWriteOperation(Sink<IndexedRecord> sink) {
        this.sink = sink;
    }

    @Override
    public void initialize(PipelineOptions options) throws Exception {
    }

    @Override
    public void finalize(Iterable<Double> writerResults, PipelineOptions options) throws Exception {
    }

    @Override
    public Writer<IndexedRecord, Double> createWriter(PipelineOptions options) throws Exception {
        return new TFakeLogWriter(this);
    }

    @Override
    public Sink<IndexedRecord> getSink() {
        return sink;
    }

    @Override
    public Coder<Double> getWriterResultCoder() {
        return DoubleCoder.of();
    }
}
