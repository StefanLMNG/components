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

import java.util.EnumSet;
import java.util.Set;

import org.talend.components.api.component.AbstractComponentDefinition;
import org.talend.components.api.component.ComponentImageType;
import org.talend.components.api.component.ConnectorTopology;
import org.talend.components.api.component.runtime.ExecutionEngine;
import org.talend.components.api.component.runtime.SimpleRuntimeInfo;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.processing.definition.ProcessingFamilyDefinition;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.runtime.RuntimeInfo;

public class ReplicateDefinition extends AbstractComponentDefinition {

    public static final String COMPONENT_NAME = "Replicate";

    public ReplicateDefinition() {
        super(COMPONENT_NAME, ExecutionEngine.BEAM);
    }

    @Override
    public String[] getFamilies() {
        return new String[] { ProcessingFamilyDefinition.NAME };
    }

    @Override public Class getPropertyClass() {
        return ReplicateProperties.class;
    }

    public Property[] getReturnProperties() {
        return new Property[] {};
    }

    @Override
    public String getPngImagePath(ComponentImageType imageType) {
        switch (imageType) {
        case PALLETE_ICON_32X32:
            return "Replicate_icon32.png"; //$NON-NLS-1$
        default:
            return "Replicate_icon32.png"; //$NON-NLS-1$
        }
    }

    @Override
    public RuntimeInfo getRuntimeInfo(ExecutionEngine engine, ComponentProperties properties,
            ConnectorTopology connectorTopology) {
        if (ExecutionEngine.BEAM.equals(engine) && ConnectorTopology.INCOMING_AND_OUTGOING.equals(connectorTopology)) {
            return new SimpleRuntimeInfo(this.getClass().getClassLoader(),
                    ProcessingFamilyDefinition.computeDependenciesFilepath(),
                    "org.talend.components.processing.runtime.replicate.ReplicateRuntime");
        } else {
            return null;
        }
    }

    public Set<ConnectorTopology> getSupportedConnectorTopologies() {
        return EnumSet.of(ConnectorTopology.INCOMING_AND_OUTGOING);
    }
}
