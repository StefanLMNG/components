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
package org.talend.components.api.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Set;

import org.junit.rules.ErrorCollector;
import org.talend.components.api.component.DatastoreDefinition;
import org.talend.components.api.component.DatastoreImageType;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.api.service.DatastoreService;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.test.PropertiesTestUtils;

public class DatastoreTestUtils {

    public static Properties checkSerialize(Properties props, ErrorCollector errorCollector) {
        return PropertiesTestUtils.checkSerialize(props, errorCollector);
    }

    /**
     * check all properties of a datastore for i18n, check form i18n, check ComponentProperties title is i18n
     * 
     * @param datastoreService where to get all the datastores
     * @param errorCollector used to collect all errors at once. @see <a
     * href="http://junit.org/apidocs/org/junit/rules/ErrorCollector.html">ErrorCollector</a>
     */
    static public void testAlli18n(DatastoreService datastoreService, ErrorCollector errorCollector) {
        Set<DatastoreDefinition> allDatastores = datastoreService.getAllDatastores();
        for (DatastoreDefinition cd : allDatastores) {
            ComponentProperties props = cd.createProperties();
            // check all properties
            if (props != null) {
                checkAllI18N(props, errorCollector);
            } else {
                System.out.println("No properties to check fo I18n for :" + cd.getName());
            }
            // check datastore definition title
            errorCollector.checkThat("missing I18n property :" + cd.getTitle(), cd.getTitle().contains("datastore."), is(false));
        }
    }

    public static void checkAllPropertyI18n(Property<?>[] propertyArray, Object parent, ErrorCollector errorCollector) {
        if (propertyArray != null) {
            for (Property<?> prop : propertyArray) {
                PropertiesTestUtils.chekProperty(errorCollector, prop, parent);
            }
        } // else no property to check so ignore.
    }

    static public void checkAllI18N(Properties checkedProps, ErrorCollector errorCollector) {
        PropertiesTestUtils.checkAllI18N(checkedProps, errorCollector);
    }

    /**
     * check that all Datastores have theirs images properly set.
     * 
     * @param datastoreService service to get the datastores to be checked.
     */
    public static void testAllImages(DatastoreService datastoreService) {
        // check datastores
        Set<DatastoreDefinition> allDatastores = datastoreService.getAllDatastores();
        for (DatastoreDefinition compDef : allDatastores) {
            for (DatastoreImageType compIT : DatastoreImageType.values()) {
                String pngImagePath = compDef.getPngImagePath(compIT);
                assertNotNull("the datastore [" + compDef.getName() + "] must return an image path for type [" + compIT + "]",
                        pngImagePath);
                InputStream resourceAsStream = compDef.getClass().getResourceAsStream(pngImagePath);
                assertNotNull(
                        "Failed to find the image for path [" + pngImagePath + "] for the datastore:type [" + compDef.getName()
                                + ":" + compIT + "].\nIt should be located at ["
                                + compDef.getClass().getPackage().getName().replace('.', '/') + "/" + pngImagePath + "]",
                        resourceAsStream);
            }
        }
    }

    /**
     * check that the depenencies file is present during integration test.
     * 
     * @param datastoreService service to get the datastores to be checked.
     */
    public static void testAllDesignDependenciesPresent(DatastoreService datastoreService, ErrorCollector errorCollector) {
        Set<DatastoreDefinition> allDatastores = datastoreService.getAllDatastores();
        for (DatastoreDefinition compDef : allDatastores) {
            errorCollector.checkThat(compDef.getMavenGroupId(), is(not(nullValue())));
            errorCollector.checkThat(compDef.getMavenArtifactId(), is(not(nullValue())));
            Set<String> mavenUriDependencies = datastoreService.getMavenUriDependencies(compDef.getName());
            errorCollector.checkThat(mavenUriDependencies, is(not(nullValue())));
            errorCollector.checkThat(mavenUriDependencies.isEmpty(), is(false));
        }
    }

}
