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

package ${package};

import org.junit.Test;

import ${packageTalend}.api.component.runtime.RuntimeInfo;

import static org.junit.Assert.assertEquals;

public class ${componentNameClass}DatasetDefinitionTest {

    private final ${componentNameClass}DatasetDefinition datasetDefinition = new ${componentNameClass}DatasetDefinition();

    /**
     * Check {@link ${componentNameClass}DatasetDefinition#getRuntimeInfo(JmsDatasetProperties properties, Object ctx)} returns RuntimeInfo,
     * which runtime class name is "${package}.runtime_${runtimeVersion}.DatasetRuntime"
     */
    @Test
    public void testGetRuntimeInfo(){
        RuntimeInfo runtimeInfo = datasetDefinition.getRuntimeInfo(null, null);
        assertEquals("${package}.runtime_${runtimeVersion}.DatasetRuntime", runtimeInfo.getRuntimeClassName());
    }

    /**
     * Check {@link ${componentNameClass}DatasetDefinition#createProperties()} returns ${componentNameClass}DatasetProperties, which canonical name is
     * "${componentName}"
     */
    @Test
    public void testCreateProperties(){
        ${componentNameClass}DatasetProperties props = datasetDefinition.createProperties();
        assertEquals("${componentName}", props.getName());
    }
}
