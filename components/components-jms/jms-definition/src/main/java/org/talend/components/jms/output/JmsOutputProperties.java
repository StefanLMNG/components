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

package org.talend.components.jms.output;

import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.components.common.SchemaProperties;
import org.talend.components.jms.JmsDatasetProperties;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;


import static org.talend.daikon.properties.property.PropertyFactory.newBoolean;
import static org.talend.daikon.properties.property.PropertyFactory.newEnum;

public class JmsOutputProperties extends ComponentPropertiesImpl {

    public enum JmsAdvancedDeliveryMode {
        Non_persistent,
        persistent
    }

    public JmsOutputProperties(String name) {
        super(name);
    }

    public SchemaProperties main = new SchemaProperties("main");

    public Property<String> to = PropertyFactory.newString("to","");

    public Property<JmsAdvancedDeliveryMode> delivery_mode = newEnum("delivery_mode", JmsAdvancedDeliveryMode.class).setRequired();

    public Property<String> pool_max_total = PropertyFactory.newString("pool_max_total","8");

    public Property<String> pool_max_wait = PropertyFactory.newString("pool_max_wait","-1");

    public Property<String> pool_min_Idle = PropertyFactory.newString("pool_min_Idle","0");

    public Property<String> pool_max_Idle = PropertyFactory.newString("pool_max_Idle","8");

    public Property<Boolean> pool_use_eviction = newBoolean("pool_use_eviction",false);

    public Property<String> pool_time_between_eviction = PropertyFactory.newString("pool_time_between_eviction","-1");

    public Property<String> pool_eviction_min_idle_time = PropertyFactory.newString("pool_eviction_min_idle_time","1800000");

    public Property<String> pool_eviction_soft_min_idle_time = PropertyFactory.newString("pool_eviction_soft_min_idle_time","0");

    public JmsDatasetProperties dataset = new JmsDatasetProperties("dataset");

    @Override
    public void setupLayout() {
        super.setupLayout();
        Form mainForm = new Form(this, Form.MAIN);
        mainForm.addRow(main);
        mainForm.addRow(to);

        Form advancedForm = new Form(this, Form.ADVANCED);
        advancedForm.addRow(delivery_mode);
        advancedForm.addRow(pool_max_total);
        advancedForm.addRow(pool_max_wait);
        advancedForm.addRow(pool_min_Idle);
        advancedForm.addRow(pool_max_Idle);
        advancedForm.addRow(pool_use_eviction);
        advancedForm.addRow(pool_time_between_eviction);
        advancedForm.addRow(pool_eviction_min_idle_time);
        advancedForm.addRow(pool_eviction_soft_min_idle_time);
    }

    @Override
    public void refreshLayout(Form form) {
        super.refreshLayout(form);
        if (form.getName().equals(Form.MAIN)) {
            form.getWidget(to.getName()).setHidden(false);
        }
        if (Form.ADVANCED.equals(form.getName())) {
            form.getWidget(delivery_mode.getName()).setVisible();
            form.getWidget(pool_max_total.getName()).setVisible();
            form.getWidget(pool_max_wait.getName()).setVisible();
            form.getWidget(pool_min_Idle.getName()).setVisible();
            form.getWidget(pool_max_Idle.getName()).setVisible();
            form.getWidget(pool_use_eviction.getName()).setVisible();
            if (pool_use_eviction.getValue()) {
                form.getWidget(pool_time_between_eviction.getName()).setVisible();
                form.getWidget(pool_eviction_min_idle_time.getName()).setVisible();
                form.getWidget(pool_eviction_soft_min_idle_time.getName()).setVisible();
            } else {
                form.getWidget(pool_time_between_eviction.getName()).setHidden();
                form.getWidget(pool_eviction_min_idle_time.getName()).setHidden();
                form.getWidget(pool_eviction_soft_min_idle_time.getName()).setHidden();
            }
        }
    }
}
