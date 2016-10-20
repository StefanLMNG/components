// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.components.fake.tfakelog;

import java.util.EnumSet;
import java.util.Set;

import org.talend.components.api.component.AbstractComponentDefinition;
import org.talend.components.api.component.ComponentImageType;
import org.talend.components.api.component.ConnectorTopology;
import org.talend.components.api.component.runtime.RuntimeInfo;
import org.talend.components.api.component.runtime.SimpleRuntimeInfo;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.fake.FakeFamilyDefinition;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.property.Property;

public class TFakeLogDefinition extends AbstractComponentDefinition {

    public static final String COMPONENT_NAME = "tFakeLog";

    public TFakeLogDefinition() {
        super(COMPONENT_NAME);
    }

    @Override
    public Class<? extends ComponentProperties> getPropertyClass() {
        return TFakeLogProperties.class;
    }

    @Override
    public String[] getFamilies() {
        return new String[] { FakeFamilyDefinition.NAME };
    }

    public Property[] getReturnProperties() {
        return new Property[] {};
    }

    @Override
    public String getPngImagePath(ComponentImageType imageType) {
        switch (imageType) {
        case PALLETE_ICON_32X32:
            return "TFakeLog_icon32.png"; //$NON-NLS-1$
        default:
            return "TFakeLog_icon32.png"; //$NON-NLS-1$
        }
    }

    public RuntimeInfo getRuntimeInfo(Properties properties, ConnectorTopology connectorTopology) {
        if (ConnectorTopology.INCOMING.equals(connectorTopology)) {
        return new SimpleRuntimeInfo(this.getClass().getClassLoader(), FakeFamilyDefinition.computeDependenciesFilepath(),
                "org.talend.components.fake.runtime.tfakelog.tFakeLogRuntime");
        } else {
            return null;
        }
    }

    public Set<ConnectorTopology> getSupportedConnectorTopologies() {
        return EnumSet.of(ConnectorTopology.INCOMING);
    }

}
