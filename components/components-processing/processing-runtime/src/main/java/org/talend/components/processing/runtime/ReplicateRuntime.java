package org.talend.components.processing.runtime;

import org.apache.avro.generic.IndexedRecord;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.TupleTag;
import org.talend.components.adapter.beam.BeamJobBuilder;
import org.talend.components.adapter.beam.BeamJobContext;
import org.talend.components.api.component.runtime.RuntimableRuntime;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.processing.definition.replicate.ReplicateProperties;
import org.talend.daikon.properties.ValidationResult;

import io.netty.util.internal.StringUtil;

public class ReplicateRuntime extends PTransform<PCollection<IndexedRecord>, PCollectionTuple>
        implements BeamJobBuilder, RuntimableRuntime<ReplicateProperties> {

    final static TupleTag<IndexedRecord> flowOutput = new TupleTag<IndexedRecord>() {
    };

    private ReplicateProperties properties;

    private boolean hasFlow;

    @Override public PCollectionTuple apply(PCollection<IndexedRecord> indexedRecordPCollection) {

        return null;
    }

    @Override public void build(BeamJobContext ctx) {
        String mainLink = ctx.getLinkNameByPortName("input_" + properties.MAIN_CONNECTOR.getName());
        if (!StringUtil.isNullOrEmpty(mainLink)) {
            PCollection<IndexedRecord> mainPCollection = ctx.getPCollectionByLinkName(mainLink);
            if (mainPCollection != null) {
                String flowLink = ctx.getLinkNameByPortName("output_" + properties.FLOW_CONNECTOR.getName());

                hasFlow = !StringUtil.isNullOrEmpty(flowLink);

                PCollectionTuple outputTuples = apply(mainPCollection);

                if (hasFlow) {
                    ctx.putPCollectionByLinkName(flowLink, outputTuples.get(flowOutput));
                }
            }
        }
    }

    @Override public ValidationResult initialize(RuntimeContainer container, ReplicateProperties componentProperties) {
        this.properties = componentProperties;
        return ValidationResult.OK;
    }
}
