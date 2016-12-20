package org.talend.components.processing.runtime;

import org.apache.avro.generic.IndexedRecord;
import org.apache.beam.sdk.transforms.DoFn;
import org.talend.components.processing.definition.replicate.ReplicateProperties;
import org.talend.daikon.properties.Properties;

import java.util.List;

import io.netty.util.internal.StringUtil;

public class ReplicateDoFn extends DoFn<IndexedRecord, IndexedRecord> {

    private String propertiesString = null;

    private transient ReplicateProperties properties = null;

    @Setup
    public void setup() throws Exception {
        // This method will instantiate correct Avro Schema object. This is
        // mandatory since the "Schema" object of Avro are not serializable.

        if (propertiesString != null) {
            properties = Properties.Helper.fromSerializedTransient(propertiesString, ReplicateProperties.class).object;
        }
    }

    @ProcessElement
    public void processElement(ProcessContext context) {

    }

    private <T extends Comparable<T>> Boolean checkCondition(Object inputValue, ReplicateProperties condition) {
        return null;
    }

    public ReplicateDoFn withProperties(ReplicateProperties properties) {
        // TODO Do not use this serialization, the result is too big.
        this.propertiesString = (properties).toSerialized();
        return this;
    }
}
