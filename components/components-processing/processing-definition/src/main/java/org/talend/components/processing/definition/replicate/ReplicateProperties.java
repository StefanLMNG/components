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
package org.talend.components.processing.definition.replicate;

import org.apache.avro.Schema;
import org.talend.components.api.component.Connector;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.common.SchemaProperties;
import org.talend.components.util.SchemaUtil;
import org.talend.daikon.properties.PropertiesImpl;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;

import java.util.Set;

import static org.talend.daikon.properties.property.PropertyFactory.newBoolean;
import static org.talend.daikon.properties.property.PropertyFactory.newEnum;

public class ReplicateProperties extends PropertiesImpl {

    // input schema
    public transient PropertyPathConnector MAIN_CONNECTOR = new PropertyPathConnector(Connector.MAIN_NAME, "main");

    public SchemaProperties main = new SchemaProperties("main") {

        @SuppressWarnings("unused")
        public void afterSchema() {
            updateOutputSchemas();
        }
    };

    // output schema
    public transient PropertyPathConnector FLOW_CONNECTOR = new PropertyPathConnector(Connector.MAIN_NAME, "schemaFlow");

    public SchemaProperties schemaFlow = new SchemaProperties("schemaFlow");

    public SchemaProperties schemaReject = new SchemaProperties("schemaReject");

    public final Property<StorageLevel> storageLevel = newEnum("storageLevel", StorageLevel.class).setRequired();

    public final Property<CompressCodec> compressCodec = newEnum("compressCodec", CompressCodec.class);

    public Property<Boolean> cacheOutput = newBoolean("cacheOutput").setRequired().setValue(true);

    public Property<Boolean> compressRDD = newBoolean("compressRDD");

    public final Property<String> tachyonStoreUrl = PropertyFactory.newString("tachyonStoreUrl").setRequired();

    public final Property<String> tachyonStoreBaseDir = PropertyFactory.newString("tachyonStoreBaseDir").setRequired();

    /**
     * FixedSchemaComponentProperties constructor comment.
     */
    public ReplicateProperties(String name) {
        super(name);
    }

    @Override
    public void setupLayout() {
        super.setupLayout();
        Form mainForm = new Form(this, Form.MAIN);
        mainForm.addRow(storageLevel);
        mainForm.addColumn(compressCodec);
        mainForm.addColumn(cacheOutput);
        mainForm.addColumn(compressRDD);
        mainForm.addColumn(tachyonStoreUrl);
        mainForm.addColumn(tachyonStoreBaseDir);
    }

    @Override
    public void refreshLayout(Form form) {
        super.refreshLayout(form);
        // Main properties
        if (form.getName().equals(Form.MAIN)) {
            form.getWidget(storageLevel.getName()).setVisible(cacheOutput);
            form.getWidget(compressRDD.getName()).setVisible(
                    cacheOutput.getValue() &&
                            (storageLevel.getValue().equals(StorageLevel.MEMORY_ONLY_SER)
                            || storageLevel.getValue().equals(StorageLevel.MEMORY_AND_DISK_SER)
                            || storageLevel.getValue().equals(StorageLevel.MEMORY_ONLY_SER_2)
                            || storageLevel.getValue().equals(StorageLevel.MEMORY_AND_DISK_SER_2))
            );

            form.getWidget(compressCodec.getName()).setVisible(
                    cacheOutput.getValue() && compressRDD.getValue() &&
                            (storageLevel.getValue().equals(StorageLevel.MEMORY_ONLY_SER)
                                    || storageLevel.getValue().equals(StorageLevel.MEMORY_AND_DISK_SER)
                                    || storageLevel.getValue().equals(StorageLevel.MEMORY_ONLY_SER_2)
                                    || storageLevel.getValue().equals(StorageLevel.MEMORY_AND_DISK_SER_2))
            );

            form.getWidget(tachyonStoreUrl.getName()).setVisible(
                    cacheOutput.getValue() && storageLevel.getValue().equals(StorageLevel.OFF_HEAP)
            );

            form.getWidget(tachyonStoreBaseDir.getName()).setVisible(
                    cacheOutput.getValue() && storageLevel.getValue().equals(StorageLevel.OFF_HEAP)
            );
        }
    }

    public void afterCacheOutput() {
        refreshLayout(getForm(Form.MAIN));
    }

    public void afterCompressRDD() {
        refreshLayout(getForm(Form.MAIN));
    }

    public void afterStorageLevel() {
        refreshLayout(getForm(Form.MAIN));
    }

    @Override
    public void setupProperties() {
        super.setupProperties();
        // default value
        cacheOutput.setValue(false);
        compressRDD.setValue(false);
        storageLevel.setValue(StorageLevel.MEMORY_ONLY);
        tachyonStoreUrl.setValue("tachyon://localhost:19998");
        tachyonStoreBaseDir.setValue("/tmp/");
        compressCodec.setValue(CompressCodec.SNAPPY);
    }

    public void updateOutputSchemas() {
        // Copy the "main" schema into the "flow" schema
        Schema inputSchema = main.schema.getValue();
        schemaFlow.schema.setValue(inputSchema);

        // Add error field to create the "reject" schema
        Schema rejectSchema = SchemaUtil.createRejectSchema(inputSchema, "rejectOutput");
        schemaReject.schema.setValue(rejectSchema);
    }
}
