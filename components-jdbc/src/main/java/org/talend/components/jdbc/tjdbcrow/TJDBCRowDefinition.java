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
package org.talend.components.jdbc.tjdbcrow;

import java.util.EnumSet;
import java.util.Set;

import org.talend.components.api.component.AbstractComponentDefinition;
import org.talend.components.api.component.ConnectorTopology;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.jdbc.runtime.JDBCRowSink;
import org.talend.components.jdbc.runtime.JDBCRowSource;
import org.talend.components.jdbc.runtime.JDBCRowSourceOrSink;
import org.talend.components.jdbc.runtime.JDBCTemplate;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.runtime.RuntimeInfo;

/**
 * JDBC row component
 *
 */
public class TJDBCRowDefinition extends AbstractComponentDefinition {

    public static final String COMPONENT_NAME = "tJDBCRowNew";

    public TJDBCRowDefinition() {
        super(COMPONENT_NAME);
    }

    @Override
    public Class<? extends ComponentProperties> getPropertyClass() {
        return TJDBCRowProperties.class;
    }

    @Override
    public String[] getFamilies() {
        return new String[] { "Databases/DB_JDBC" };
    }

    // TODO add more return properties
    @SuppressWarnings("rawtypes")
    @Override
    public Property[] getReturnProperties() {
        return new Property[] { RETURN_ERROR_MESSAGE_PROP, RETURN_TOTAL_RECORD_COUNT_PROP };
    }

    @Override
    public RuntimeInfo getRuntimeInfo(ComponentProperties properties, ConnectorTopology connectorTopology) {
        switch (connectorTopology) {
        case OUTGOING:
            return JDBCTemplate.createCommonRuntime(this.getClass().getClassLoader(), properties,
                    JDBCRowSource.class.getCanonicalName());
        case INCOMING:
        case INCOMING_AND_OUTGOING:
            return JDBCTemplate.createCommonRuntime(this.getClass().getClassLoader(), properties,
                    JDBCRowSink.class.getCanonicalName());
        case NONE:
            return JDBCTemplate.createCommonRuntime(this.getClass().getClassLoader(), properties,
                    JDBCRowSourceOrSink.class.getCanonicalName());
        default:
            return null;
        }
    }

    @Override
    public Set<ConnectorTopology> getSupportedConnectorTopologies() {
        return EnumSet.of(ConnectorTopology.OUTGOING, ConnectorTopology.INCOMING, ConnectorTopology.INCOMING_AND_OUTGOING,
                ConnectorTopology.NONE);
    }

    @Override
    public boolean isConditionalInputs() {
        return true;
    }

}
