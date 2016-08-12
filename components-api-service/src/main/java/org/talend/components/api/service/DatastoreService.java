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

import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.talend.components.api.component.DatastoreDefinition;
import org.talend.components.api.component.DatastoreImageType;
import org.talend.components.api.exception.DatastoreException;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.service.PropertiesService;
import org.talend.daikon.properties.service.Repository;

/**
 * The Main service provided by this project to get access to all registered datastores and their properties.
 */
public interface DatastoreService extends PropertiesService<Properties> {

    /**
     * Get the list of all the datastore names that are registered
     *
     * @return return the set of datastore names, never null
     */
    Set<String> getAllDatastoreNames();

    /**
     * Get the list of all the datastores {@link DatastoreDefinition} that are registered
     *
     * @return return the set of datastore definitions, never null.
     */
    Set<DatastoreDefinition> getAllDatastores();

    /**
     * Used to get a new {@link ComponentProperties} object for the specified datastore.
     * 
     * The {@code ComponentProperties} has everything required to render a UI and as well capture and validate the
     * values of the properties associated with the datastore, based on interactions with this service.
     *
     * @param name the name of the datastore
     * @return a {@code ComponentProperties} object.
     * @exception DatastoreException thrown if the datastore is not registered in the service
     */
    ComponentProperties getComponentProperties(String name);

    /**
     * Return the dataset associated with a datastore
     */
    String[] getDatasets(String name);

    /**
     * Check the integrity of a specified datastore. The nature of the checks are dependant of the datastore. It must
     * allow the user to know when the registered parameters are valid before creating a Dataset.
     */
    List<String> getValidateChecks(String name);

    /**
     * Used to get the JSON Schema for the specified datastore.
     * 
     * @param name the name of the datastore
     * @return a JSON Schema as a String.
     * @exception DatastoreException thrown if the datastore is not registered in the service
     */
    String getJSONSchema(String name);

    /**
     * Used to get a the {@link DatastoreDefinition} object for the specified datastore.
     *
     *
     * @param name the name of the datastore
     * @return the {@code DatastoreDefinition} object.
     * @exception DatastoreException thrown if the datastore is not registered in the service
     */
    DatastoreDefinition getDatastoreDefinition(String name);

    /**
     * Return the {@link DatastoreDefinition} objects for any datastore(s) that can be constructed from the given
     * {@link ComponentProperties} object.
     * 
     * @param properties the {@link ComponentProperties} object to look for.
     * @return the list of compatbible {@link DatastoreDefinition} objects.
     */
    List<DatastoreDefinition> getPossibleDatastores(ComponentProperties... properties) throws Throwable;

    /**
     * Copy the nestedValues properties into the targetProperties nested properties if the targetProperties accepts it.
     * It is guarantied to be accepted if the targetProperties is associated with the Datastore definition that was
     * return by {@link #getPossibleDatastores(ComponentProperties...)} using the nestedValue as a parameter.
     * 
     * @param targetProperties the ComponentProperties to be updated with the nestedValues properties.
     * @param nestedValues the ComponentProperties which properties will be copied inot the targetProperties.
     * @return true if the copy was done and false if the targetProperties does not accept the nestedValues type.
     */
    boolean setNestedPropertiesValues(ComponentProperties targetProperties, Properties nestedValues);

    /**
     * Return the png image related to the given datastore
     * 
     * @param datastoreName, name of the comonent to get the image for
     * @param imageType, the type of image requested
     * @return the png image stream or null if none was provided or an error occurred
     * @exception DatastoreException thrown if the datastoreName is not registered in the service
     */
    InputStream getDatastorePngImage(String datastoreName, DatastoreImageType imageType);

    /**
     * Allows for a local implementation to setup a repository store used to store {@link ComponentProperties}.
     * 
     * @param repository
     */
    @Override
    void setRepository(Repository repository);

    /**
     * list all the depencencies required for this datastore to be executed at runtime
     * 
     * @param datastoreName name of the datastore to get the dependencies of.
     * @return a set of maven uri following the pax-maven uri scheme @see <a
     * href="https://ops4j1.jira.com/wiki/display/paxurl/Mvn+Protocol">https://ops4j1.jira.com/wiki/display/paxurl/
     * Mvn+Protocol</a>
     */
    Set<String> getMavenUriDependencies(String datastoreName);

}