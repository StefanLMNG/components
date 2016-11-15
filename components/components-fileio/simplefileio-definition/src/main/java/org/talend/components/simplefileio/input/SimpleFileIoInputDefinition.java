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

package org.talend.components.simplefileio.input;

import java.util.EnumSet;
import java.util.Set;

import org.talend.components.api.component.AbstractComponentDefinition;
import org.talend.components.api.component.ConnectorTopology;
import org.talend.components.api.component.runtime.DependenciesReader;
import org.talend.components.api.component.runtime.SimpleRuntimeInfo;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.simplefileio.SimpleFileIoComponentFamilyDefinition;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.runtime.RuntimeInfo;

public class SimpleFileIoInputDefinition extends AbstractComponentDefinition {

    public static final String NAME = SimpleFileIoComponentFamilyDefinition.NAME + "Input";

    public static final String RUNTIME = "org.talend.components.simplefileio.runtime.SimpleFileIoInputRuntime";

    public SimpleFileIoInputDefinition() {
        super(NAME);
    }

    @Override
    public Class<? extends ComponentProperties> getPropertyClass() {
        return SimpleFileIoInputProperties.class;
    }

    @Override
    public RuntimeInfo getRuntimeInfo(ComponentProperties properties, ConnectorTopology connectorTopology) {
        return new SimpleRuntimeInfo(this.getClass().getClassLoader(),
                DependenciesReader.computeDependenciesFilePath("org.talend.components", "components-simplefileio/simplefileio"),
                RUNTIME);
    }

    @Override
    public Property[] getReturnProperties() {
        return new Property[] {};
    }

    @Override
    public Set<ConnectorTopology> getSupportedConnectorTopologies() {
        return EnumSet.of(ConnectorTopology.OUTGOING);
    }
}
