package org.talend.components.kafka.dataset;

import static org.talend.daikon.properties.presentation.Widget.widget;

import java.util.ArrayList;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.talend.components.api.exception.ComponentException;
import org.talend.components.common.SchemaProperties;
import org.talend.components.common.dataset.DatasetProperties;
import org.talend.components.kafka.datastore.KafkaDatastoreProperties;
import org.talend.components.kafka.runtime.IKafkaDatasetRuntime;
import org.talend.daikon.NamedThing;
import org.talend.daikon.SimpleNamedThing;
import org.talend.daikon.properties.PropertiesImpl;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;
import org.talend.daikon.runtime.RuntimeInfo;

public class KafkaDatasetProperties extends PropertiesImpl implements DatasetProperties<KafkaDatastoreProperties> {

    public KafkaDatastoreProperties datastore = new KafkaDatastoreProperties("datastore");

    public Property<String> topic = PropertyFactory.newString("topic");

    public SchemaProperties main = new SchemaProperties("main");

    public KafkaDatasetProperties(String name) {
        super(name);
    }

    @Override
    public void setupLayout() {
        super.setupLayout();

        Form mainForm = new Form(this, Form.MAIN);
        mainForm.addRow(widget(topic).setWidgetType(Widget.NAME_SELECTION_AREA_WIDGET_TYPE));
        mainForm.addRow(main.getForm(Form.MAIN));

    }

    @Override
    public void setupProperties() {
        Schema schema = SchemaBuilder.record("row").namespace("kafka").fields() //
                .name("key").type(Schema.create(Schema.Type.BYTES)).noDefault() //
                .name("value").type(Schema.create(Schema.Type.BYTES)).noDefault() //
                .endRecord();
        main.schema.setValue(schema);
    }

    public ValidationResult beforeTopic() {
        try {
            IKafkaDatasetRuntime runtime = getRuntime();
            List<NamedThing> topics = new ArrayList<>();
            for (String topic : runtime.listTopic()) {
                topics.add(new SimpleNamedThing(topic, topic));
            }
            this.topic.setPossibleValues(topics);
            return ValidationResult.OK;
        } catch (Exception e) {
            return new ValidationResult(new ComponentException(e));
        }
    }

    private IKafkaDatasetRuntime getRuntime() throws Exception {
        KafkaDatasetDefinition definition = new KafkaDatasetDefinition();
        RuntimeInfo runtimeInfo = definition.getRuntimeInfo(this, null);
        IKafkaDatasetRuntime runtime = (IKafkaDatasetRuntime) Class.forName(runtimeInfo.getRuntimeClassName()).newInstance();
        runtime.initialize(null, this);
        return runtime;
    }

    @Override
    public KafkaDatastoreProperties getDatastoreProperties() {
        return datastore;
    }

    @Override
    public void setDatastoreProperties(KafkaDatastoreProperties datastoreProperties) {
        datastore = datastoreProperties;
    }

}
