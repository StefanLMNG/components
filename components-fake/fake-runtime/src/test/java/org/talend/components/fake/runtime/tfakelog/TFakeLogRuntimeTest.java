package org.talend.components.fake.runtime.tfakelog;

import java.util.HashMap;
import java.util.Map;

import org.apache.beam.runners.direct.DirectRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.values.PCollection;
import org.junit.Test;
import org.talend.components.api.runtime.BeamJobBuilderUtil;
import org.talend.components.api.runtime.BeamJobContext;
import org.talend.components.fake.runtime.tfakeinput.TFakeInputRuntime;
import org.talend.components.fake.tfakeinput.TFakeInputProperties;
import org.talend.components.fake.tfakelog.TFakeLogProperties;

public class TFakeLogRuntimeTest {

    @Test
    public void test() {

        PipelineOptions options = PipelineOptionsFactory.create();
        options.setRunner(DirectRunner.class);
        final Pipeline p = Pipeline.create(options);

        TFakeInputRuntime input = new TFakeInputRuntime();
        TFakeInputProperties inputProperties = new TFakeInputProperties("input");
        inputProperties.init();
        input.initialize(null, inputProperties);

        TFakeLogRuntime log = new TFakeLogRuntime();
        TFakeLogProperties logProperties = new TFakeLogProperties("log");
        logProperties.init();
        logProperties.main.schema.setValue(inputProperties.schemaFlow.schema.getValue());
        log.initialize(null, logProperties);

        // A fake, temporary BeamJobContext.
        final Map<String, PCollection> pCollectionStore = new HashMap<>();
        final Map<String, String> linkNameByPortName = new HashMap<>();
        BeamJobContext ctx = new BeamJobContext() {

            @Override
            public PCollection getPCollectionByLinkName(String linkName) {
                return pCollectionStore.get(linkName);
            }

            @Override
            public void putPCollectionByLinkName(String linkName, PCollection pcollection) {
                pCollectionStore.put(linkName, pcollection);
            }

            @Override
            public String getLinkNameByPortName(String portName) {
                return linkNameByPortName.get(portName);
            }

            @Override
            public Pipeline getPipeline() {
                return p;
            }
        };

        linkNameByPortName.clear();
        linkNameByPortName.put("OUT", "row1");
        BeamJobBuilderUtil.getBuilder(input).build(ctx);

        linkNameByPortName.clear();
        linkNameByPortName.put("IN", "row1");
        BeamJobBuilderUtil.getBuilder(log).build(ctx);

        p.run();
    }

}
