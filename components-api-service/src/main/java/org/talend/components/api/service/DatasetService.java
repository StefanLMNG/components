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

import org.talend.components.api.component.ComponentDefinition;
import org.talend.components.api.component.DatasetDefinition;
import org.talend.components.api.component.DatasetImageType;
import org.talend.components.api.exception.DatasetException;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.service.PropertiesService;
import org.talend.daikon.properties.service.Repository;

/**
 * The Main service provided by this project to get access to all registered datasets and their properties.
 */
public interface DatasetService extends PropertiesService<Properties> {

    /**
     * Get the list of all the dataset names that are registered
     *
     * @return return the set of dataset names, never null
     */
    Set<String> getAllDatasetNames();

    /**
     * Get the list of all the datasets {@link DatasetDefinition} that are registered
     *
     * @return return the set of dataset definitions, never null.
     */
    Set<DatasetDefinition> getAllDatasets();

    /**
     * Used to get a new {@link ComponentProperties} object for the specified dataset.
     * 
     * The {@code ComponentProperties} has everything required to render a UI and as well capture and validate the
     * values of the properties associated with the dataset, based on interactions with this service.
     *
     * @param name the name of the dataset
     * @return a {@code ComponentProperties} object.
     * @exception DatasetException thrown if the dataset is not registered in the service
     */
    ComponentProperties getComponentProperties(String name);

    /**
     * Return the list of components associated to a dataset
     */
    ComponentDefinition[] getComponents(String name);

    /**
     * Return a sample of the data contained inside a specified dataset.
     */
    // TODO Change return type
    // Object[] getSample(String name, Integer size);

    /**
     * Return the schema associated to a specified dataset
     */
    // TODO Change return type
    // String getSchema(String name);

    /**
     * Check the integrity of a specified dataset. The nature of the checks are dependant of the dataset. It must allow
     * the user to know when the registered parameters are valid before creating a Dataset.
     */
    List<Object> validate(String name);

    /**
     * Used to get the JSON Schema for the specified dataset.
     * 
     * @param name the name of the dataset
     * @return a JSON Schema as a String.
     * @exception DatasetException thrown if the dataset is not registered in the service
     */
    String getJSONSchema(String name);

    /**
     * Used to get a the {@link DatasetDefinition} object for the specified dataset.
     *
     *
     * @param name the name of the dataset
     * @return the {@code DatasetDefinition} object.
     * @exception DatasetException thrown if the dataset is not registered in the service
     */
    DatasetDefinition getDatasetDefinition(String name);

    /**
     * Return the {@link DatasetDefinition} objects for any dataset(s) that can be constructed from the given
     * {@link ComponentProperties} object.
     * 
     * @param properties the {@link ComponentProperties} object to look for.
     * @return the list of compatbible {@link DatasetDefinition} objects.
     */
    List<DatasetDefinition> getPossibleDatasets(ComponentProperties... properties) throws Throwable;

    /**
     * Copy the nestedValues properties into the targetProperties nested properties if the targetProperties accepts it.
     * It is guarantied to be accepted if the targetProperties is associated with the Dataset definition that was return
     * by {@link #getPossibleDatasets(ComponentProperties...)} using the nestedValue as a parameter.
     * 
     * @param targetProperties the ComponentProperties to be updated with the nestedValues properties.
     * @param nestedValues the ComponentProperties which properties will be copied inot the targetProperties.
     * @return true if the copy was done and false if the targetProperties does not accept the nestedValues type.
     */
    boolean setNestedPropertiesValues(ComponentProperties targetProperties, Properties nestedValues);

    /**
     * Return the png image related to the given dataset
     * 
     * @param datasetName, name of the comonent to get the image for
     * @param imageType, the type of image requested
     * @return the png image stream or null if none was provided or an error occurred
     * @exception DatasetException thrown if the datasetName is not registered in the service
     */
    InputStream getDatasetPngImage(String datasetName, DatasetImageType imageType);

    /**
     * Allows for a local implementation to setup a repository store used to store {@link ComponentProperties}.
     * 
     * @param repository
     */
    @Override
    void setRepository(Repository repository);

    /**
     * list all the depencencies required for this dataset to be executed at runtime
     * 
     * @param datasetName name of the dataset to get the dependencies of.
     * @return a set of maven uri following the pax-maven uri scheme @see <a
     * href="https://ops4j1.jira.com/wiki/display/paxurl/Mvn+Protocol">https://ops4j1.jira.com/wiki/display/paxurl/
     * Mvn+Protocol</a>
     */
    Set<String> getMavenUriDependencies(String datasetName);

}