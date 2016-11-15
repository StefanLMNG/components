package org.talend.components.jms.runtime_1_1;

import org.talend.components.jms.JmsDatasetProperties;
import org.talend.components.jms.JmsMessageType;
import org.talend.components.jms.JmsProcessingMode;

public class JmsDatasetRuntime { //implements DatasetRuntime{

    private JmsDatasetProperties properties;

    private JmsMessageType msgType;

    private JmsProcessingMode processingMode;

    public JmsDatastoreRuntime datastoreRuntime = new JmsDatastoreRuntime();
}
