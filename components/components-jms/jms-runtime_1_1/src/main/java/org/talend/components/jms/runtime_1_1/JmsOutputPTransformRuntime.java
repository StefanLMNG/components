package org.talend.components.jms.runtime_1_1;

import org.apache.avro.generic.IndexedRecord;

import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.jms.JmsIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.Partition;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionList;
import org.apache.beam.sdk.values.PDone;

import org.talend.components.api.component.runtime.RuntimableRuntime;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.jms.JmsMessageType;
import org.talend.components.jms.output.JmsOutputProperties;

import org.talend.daikon.avro.AvroRegistry;
import org.talend.daikon.avro.converter.IndexedRecordConverter;
import org.talend.daikon.exception.error.CommonErrorCodes;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.exception.TalendRuntimeException;

import java.util.Hashtable;
import java.util.UUID;

import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JmsOutputPTransformRuntime extends PTransform<PCollection<Object>, PDone>
        implements RuntimableRuntime {

    transient private JmsOutputProperties properties;

    private JmsDatastoreRuntime datastoreRuntime;

    private JmsMessageType messageType;

    private String id = UUID.randomUUID().toString();

    @Override public PDone apply(PCollection<Object> objectPCollection) {
        PCollection<IndexedRecord> indexedCollection = objectPCollection.apply("ExtractIndexedRecord", ParDo.of(new DoFn<Object, IndexedRecord>() {
            IndexedRecordConverter converter;
            @DoFn.ProcessElement public void processElement(ProcessContext c) throws Exception {
                if (c.element() == null){
                    return;
                }
                if (converter == null){
                    converter = new AvroRegistry().createIndexedRecordConverter(c.element().getClass());
                }
                c.output((IndexedRecord)converter.convertToAvro(c.element()));
            }
        }));
        indexedCollection.setCoder(LazyAvroCoder.<IndexedRecord>of(id));
        
        PCollection<String> jmsCollection = indexedCollection.apply("ExtractString", ParDo.of(new DoFn<IndexedRecord, String>() {
            @DoFn.ProcessElement public void processElement(ProcessContext c) throws Exception {
                c.output(c.element().get(0).toString());
            }
        }));

        datastoreRuntime = new JmsDatastoreRuntime();
        datastoreRuntime.initialize(null, properties.dataset.datastore);
        if (messageType.equals(JmsMessageType.QUEUE)) {
            return jmsCollection.apply(JmsIO.write()
                    .withConnectionFactory(datastoreRuntime.getConnectionFactory())
                    .withQueue(properties.to.getValue()));
        } else if (messageType.equals(JmsMessageType.TOPIC)) {
            // TODO label comes from user
           return jmsCollection.apply("writeToJms", JmsIO.write()
                  .withConnectionFactory(datastoreRuntime.getConnectionFactory())
                  .withTopic(properties.to.getValue()));
        }
        return null;
    }

    @Override public ValidationResult initialize(RuntimeContainer container, Properties properties) {
        this.properties = (JmsOutputProperties) properties;
        return ValidationResult.OK;
    }

    public void setMessageType(){
        messageType = properties.dataset.msgType.getValue();
    }
}
