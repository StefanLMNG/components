package org.talend.components.fake.runtime.tfakelog;

import org.apache.avro.generic.IndexedRecord;
import org.apache.beam.sdk.io.Write;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.fake.tfakelog.TFakeLogProperties;
import org.talend.daikon.properties.ValidationResult;

public class TFakeLogRuntime extends PTransform<PCollection<IndexedRecord>, PDone> {

    private TFakeLogProperties properties;

    public ValidationResult initialize(RuntimeContainer container, ComponentProperties componentProperties) {
        this.properties = (TFakeLogProperties) componentProperties;
        return ValidationResult.OK;
    }

    @Override
    public PDone apply(PCollection<IndexedRecord> inputPCollection) {
        return inputPCollection.apply(properties.getName(), Write.to(new TFakeLogSink()));
    }
}
