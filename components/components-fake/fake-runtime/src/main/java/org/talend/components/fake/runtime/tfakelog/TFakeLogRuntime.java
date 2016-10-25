package org.talend.components.fake.runtime.tfakelog;

import org.apache.avro.generic.IndexedRecord;
import org.apache.beam.sdk.io.Write;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;
import org.talend.components.api.component.runtime.RuntimableRuntime;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.fake.tfakelog.TFakeLogProperties;
import org.talend.daikon.properties.ValidationResult;

public class TFakeLogRuntime extends PTransform<PCollection<IndexedRecord>, PDone>
        implements RuntimableRuntime<TFakeLogProperties> {

    private TFakeLogProperties properties;

    public ValidationResult initialize(RuntimeContainer container, TFakeLogProperties componentProperties) {
        this.properties = componentProperties;
        return ValidationResult.OK;
    }

    @Override
    public PDone apply(PCollection<IndexedRecord> inputPCollection) {
        return inputPCollection.apply(properties.getName(), Write.to(new TFakeLogSink()));
    }
}
