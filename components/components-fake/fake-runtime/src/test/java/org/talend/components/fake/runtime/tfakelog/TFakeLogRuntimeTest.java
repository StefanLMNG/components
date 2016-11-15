package org.talend.components.fake.runtime.tfakelog;

import org.apache.beam.runners.direct.DirectRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.junit.Test;
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

        p.apply(input).apply(log);

        p.run();
    }

}
