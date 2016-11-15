package org.talend.components.kafka.runtime;

import java.util.Arrays;

import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.common.datastore.runtime.DatastoreRuntime;
import org.talend.components.kafka.datastore.KafkaDatastoreProperties;
import org.talend.daikon.properties.ValidationResult;

public class KafkaDatastoreRuntime implements DatastoreRuntime<KafkaDatastoreProperties> {

    protected KafkaDatastoreProperties datastore;

    @Override
    public Iterable<ValidationResult> doHealthChecks(RuntimeContainer container) {
        String bootstraps = datastore.brokers.getValue();
        if (bootstraps == null || "".equals(bootstraps)) {
            return Arrays.asList(new ValidationResult().setStatus(ValidationResult.Result.ERROR)
                    .setMessage("Bootstrap server urls should not be empty"));
        }
        try {
            KafkaConnection.createConsumer(datastore).listTopics();
        } catch (Throwable exception) {
            return Arrays
                    .asList(new ValidationResult().setStatus(ValidationResult.Result.ERROR).setMessage(exception.getMessage()));
        }
        return Arrays.asList(ValidationResult.OK);
    }

    @Override
    public ValidationResult initialize(RuntimeContainer container, KafkaDatastoreProperties properties) {
        this.datastore = properties;
        return ValidationResult.OK;
    }
}
