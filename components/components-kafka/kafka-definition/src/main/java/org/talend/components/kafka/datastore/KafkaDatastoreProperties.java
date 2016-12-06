// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.components.kafka.datastore;

import org.talend.components.common.SslProperties;
import org.talend.components.common.datastore.DatastoreProperties;
import org.talend.components.common.datastore.runtime.DatastoreRuntime;
import org.talend.daikon.properties.PropertiesImpl;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.property.EnumProperty;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;
import org.talend.daikon.runtime.RuntimeInfo;
import org.talend.daikon.runtime.RuntimeUtil;
import org.talend.daikon.sandbox.SandboxedInstance;

public class KafkaDatastoreProperties extends PropertiesImpl implements DatastoreProperties {

    public EnumProperty<KafkaVersion> version = PropertyFactory.newEnum("version", KafkaVersion.class);

    public Property<String> brokers = PropertyFactory.newString("brokers").setRequired();

    public SslProperties ssl = new SslProperties("ssl");

    public KafkaDatastoreProperties(String name) {
        super(name);
    }

    @Override
    public void setupProperties() {
        super.setupProperties();
        version.setValue(KafkaVersion.V_0_10_0_1);
    }

    @Override
    public void setupLayout() {
        super.setupLayout();

        Form mainForm = new Form(this, Form.MAIN);
        mainForm.addRow(version);
        mainForm.addRow(brokers);
        mainForm.addRow(ssl.getForm(Form.MAIN));

    }

    // FIXME doHealthChecks return list of ValidationResult, but validate method only return one
    public ValidationResult validateTestConnection() throws Exception {
        KafkaDatastoreDefinition definition = new KafkaDatastoreDefinition();
        RuntimeInfo runtimeInfo = definition.getRuntimeInfo(this, null);
        try (SandboxedInstance sandboxedInstance = RuntimeUtil.createRuntimeClass(runtimeInfo, getClass().getClassLoader())) {
            DatastoreRuntime runtime = (DatastoreRuntime) sandboxedInstance.getInstance();
            runtime.initialize(null, this);
            Iterable<ValidationResult> iterables = runtime.doHealthChecks(null);
            for (ValidationResult validationResult : iterables) {
                if (validationResult.getStatus() == ValidationResult.Result.ERROR) {
                    return validationResult;
                }
            }
        }

        return ValidationResult.OK;
    }

    public enum KafkaVersion {
        V_0_10_0_1,
        V_0_9_0_1
    }
}
