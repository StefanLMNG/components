package org.talend.components.fake.runtime.tfakeinput;

import org.apache.avro.generic.IndexedRecord;
import org.apache.beam.sdk.io.Read;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;
import org.talend.components.api.component.runtime.RuntimableRuntime;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.fake.tfakeinput.TFakeInputProperties;
import org.talend.daikon.properties.ValidationResult;

public class TFakeInputRuntime extends PTransform<PBegin, PCollection<IndexedRecord>> implements RuntimableRuntime<TFakeInputProperties> {

    private TFakeInputProperties properties;

    public ValidationResult initialize(RuntimeContainer container, TFakeInputProperties componentProperties) {
        this.properties = componentProperties;
        return ValidationResult.OK;
    }

    @Override
    public PCollection<IndexedRecord> apply(PBegin empty) {
        return empty.apply(properties.getName(),
                Read.from(new TFakeInputBoundedSource().withOutputSchema(properties.schemaFlow.schema.getValue())));
    }
}
