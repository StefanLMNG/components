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
package org.talend.components.api.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.talend.components.api.component.DatasetDefinition;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.components.api.service.ComponentServiceTest.NotExistingComponentProperties;
import org.talend.components.api.service.internal.DatasetServiceImpl;
import org.talend.components.api.service.testcomponent.ComponentPropertiesWithDefinedI18N;
import org.talend.components.api.service.testcomponent.nestedprop.NestedComponentProperties;
import org.talend.components.api.service.testdataset.TestDatasetDefinition;
import org.talend.components.api.test.AbstractDatasetTest;
import org.talend.components.api.test.DatasetTestUtils;
import org.talend.components.api.test.SimpleDatasetRegistry;

public class DatasetServiceTest extends AbstractDatasetTest {

    static class NotExistingDatasetProperties extends ComponentPropertiesImpl {

        public NotExistingDatasetProperties() {
            super("foo");
        }
    }

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    private DatasetServiceImpl datasetService;

    @Before
    public void initializeDatasetRegistryAnsService() {
        // reset the dataset service
        datasetService = null;
    }

    // default implementation for pure java test. Shall be overriden of Spring or OSGI tests
    @Override
    public DatasetService getDatasetService() {
        if (datasetService == null) {
            SimpleDatasetRegistry testDatasetRegistry = new SimpleDatasetRegistry();
            testDatasetRegistry.addDataset(TestDatasetDefinition.DATASET_NAME, new TestDatasetDefinition());
            datasetService = new DatasetServiceImpl(testDatasetRegistry);
        }
        return datasetService;
    }

    @Test
    public void testSupportsProps() throws Throwable {
        ComponentProperties props = getDatasetService().getComponentProperties(TestDatasetDefinition.DATASET_NAME);
        ComponentPropertiesWithDefinedI18N anotherProp = (ComponentPropertiesWithDefinedI18N) new ComponentPropertiesWithDefinedI18N(
                "foo").init();
        List<DatasetDefinition> datasets = getDatasetService().getPossibleDatasets(props, anotherProp);
        assertEquals("TestDataset", datasets.get(0).getName());

        datasets = getDatasetService().getPossibleDatasets(new NestedComponentProperties("props"),
                new NotExistingComponentProperties());
        assertEquals(0, datasets.size());
    }

    @Test
    public void testFamilies() {
        TestDatasetDefinition testDatasetDefinition = new TestDatasetDefinition();
        assertEquals(2, testDatasetDefinition.getFamilies().length);
    }

    @Override
    @Test
    public void testAlli18n() {
        DatasetTestUtils.testAlli18n(getDatasetService(), errorCollector);
    }

    @Override
    @Test
    public void testAllImages() {
        DatasetTestUtils.testAllImages(getDatasetService());
    }

    @Test
    public void testGetDependencies() {
        // check the dataset def return the proper stream for the pom
        Set<String> mavenUriDependencies = getDatasetService().getMavenUriDependencies(TestDatasetDefinition.DATASET_NAME);
        assertEquals(5, mavenUriDependencies.size());
        assertThat(mavenUriDependencies, containsInAnyOrder("mvn:org.apache.maven/maven-core/3.3.3/jar", //
                "mvn:org.eclipse.sisu/org.eclipse.sisu.plexus/0.0.0.M2a/jar", //
                "mvn:org.apache.maven/maven-artifact/3.3.3/jar", //
                "mvn:org.eclipse.aether/aether-transport-file/1.0.0.v20140518/jar", //
                "mvn:org.talend.components/file-input/0.1.0.SNAPSHOT/jar"//
        ));
    }

    @Test
    public void testGetAllDepenendencies() {
        DatasetTestUtils.testAllDesignDependenciesPresent(getDatasetService(), errorCollector);
    }

}
