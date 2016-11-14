package org.talend.components.jms.runtime_1_1;

import org.apache.beam.sdk.io.jms.JmsIO;
import org.apache.beam.sdk.io.jms.JmsRecord;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;
import org.talend.components.api.component.runtime.RuntimableRuntime;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.jms.JmsMessageType;
import org.talend.components.jms.input.JmsInputProperties;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.ValidationResult;

public class JmsInputPTransformRuntime extends PTransform<PBegin, PCollection>
        implements RuntimableRuntime {

    transient private JmsInputProperties properties;

    private JmsDatastoreRuntime datastoreRuntime;

    private JmsMessageType messageType;

    //private String id = UUID.randomUUID().toString();

    @Override public ValidationResult initialize(RuntimeContainer container, Properties properties) {
        this.properties = (JmsInputProperties) properties;
        return ValidationResult.OK;
    }

    public void setMessageType(){
        messageType = properties.dataset.msgType.getValue();
    }

    @Override public PCollection apply(PBegin pBegin) {

        datastoreRuntime = new JmsDatastoreRuntime();
        datastoreRuntime.initialize(null, properties.dataset.datastore);

        PCollection<JmsRecord> jmsCollection = null;
        if (messageType.equals(JmsMessageType.QUEUE)) {
            jmsCollection = pBegin.apply(JmsIO.read()
                    .withConnectionFactory(datastoreRuntime.getConnectionFactory())
                    .withQueue(properties.from.getValue())
                    .withMaxNumRecords(properties.max_msg.getValue()));
        } else if (messageType.equals(JmsMessageType.TOPIC)) {
            // TODO label comes from user
            jmsCollection = pBegin.apply(JmsIO.read()
                    .withConnectionFactory(datastoreRuntime.getConnectionFactory())
                    .withTopic(properties.from.getValue())
                    .withMaxNumRecords(properties.max_msg.getValue()));
        }

        if (jmsCollection != null) {
            PCollection<String> outputCollection = jmsCollection.apply("ExtractString", ParDo.of(new DoFn<JmsRecord, String>() {
                @DoFn.ProcessElement public void processElement(ProcessContext c) throws Exception {
                    c.output(c.element().getPayload());
                }
            }));
            return outputCollection;
        }

        return null;
    }
}
