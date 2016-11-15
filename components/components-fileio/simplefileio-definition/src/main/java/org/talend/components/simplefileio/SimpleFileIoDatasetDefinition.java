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

package org.talend.components.simplefileio;

import org.talend.components.api.component.runtime.DependenciesReader;
import org.talend.components.api.component.runtime.SimpleRuntimeInfo;
import org.talend.components.common.dataset.DatasetDefinition;
import org.talend.daikon.SimpleNamedThing;
import org.talend.daikon.runtime.RuntimeInfo;

public class SimpleFileIoDatasetDefinition extends SimpleNamedThing implements DatasetDefinition<SimpleFileIoDatasetProperties> {

    public static final String RUNTIME = "org.talend.components.simplefileio.runtime.SimpleFileIoDatasetRuntime";

    public static final String NAME = SimpleFileIoComponentFamilyDefinition.NAME + "Dataset";

    public SimpleFileIoDatasetDefinition() {
        super(NAME);
    }

    @Override
    public Class<SimpleFileIoDatasetProperties> getPropertiesClass() {
        return SimpleFileIoDatasetProperties.class;
    }

    @Override
    public RuntimeInfo getRuntimeInfo(SimpleFileIoDatasetProperties properties, Object ctx) {
        return new SimpleRuntimeInfo(this.getClass().getClassLoader(), DependenciesReader.computeDependenciesFilePath(
                "org.talend.components", "components-simplefileio/simplefileio-runtime_1_1"), RUNTIME);
    }

    @Override
    public String getImagePath() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return getI18nMessage("dataset." + getName() + I18N_DISPLAY_NAME_SUFFIX);
    }

}
