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

package org.talend.components.jms;

import static org.talend.daikon.properties.property.PropertyFactory.newEnum;

import org.talend.components.common.dataset.DatasetProperties;
import org.talend.daikon.properties.PropertiesImpl;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.property.Property;

public class JmsDatasetProperties extends PropertiesImpl implements DatasetProperties<JmsDatastoreProperties> {

    public JmsDatasetProperties(String name) {
        super(name);
    }

    public enum AdvancedPropertiesArrayType {
        RAW,
        CONTENT
    }

    public Property<JmsMessageType> msgType = newEnum("msgType", JmsMessageType.class).setRequired();

    public Property<JmsProcessingMode> processingMode = newEnum("processingMode", JmsProcessingMode.class);

    public JmsDatastoreProperties datastore = new JmsDatastoreProperties("datastore");

    @Override
    public JmsDatastoreProperties getDatastoreProperties() {
        return datastore;
    }

    @Override
    public void setDatastoreProperties(JmsDatastoreProperties datastoreProperties) {
        datastore = datastoreProperties;
    }

    @Override
    public void setupLayout() {
        super.setupLayout();
        Form mainForm = new Form(this, Form.MAIN);
        mainForm.addRow(msgType);
        mainForm.addRow(processingMode);
    }
}
