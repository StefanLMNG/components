package org.talend.components.fake.runtime.tfakelog;

import org.apache.avro.generic.IndexedRecord;
import org.apache.beam.sdk.io.Sink.WriteOperation;
import org.apache.beam.sdk.io.Sink.Writer;

public class TFakeLogWriter extends Writer<IndexedRecord, Double> {

    private WriteOperation<IndexedRecord, Double> writeOperation;

    public TFakeLogWriter(WriteOperation<IndexedRecord, Double> writeOperation) {
        this.writeOperation = writeOperation;
    }

    @Override
    public void open(String uId) throws Exception {
    }

    @Override
    public void write(IndexedRecord value) throws Exception {
        System.out.println(value);
    }

    @Override
    public Double close() throws Exception {
        return 2.0;
    }

    @Override
    public WriteOperation<IndexedRecord, Double> getWriteOperation() {
        return writeOperation;
    }
}