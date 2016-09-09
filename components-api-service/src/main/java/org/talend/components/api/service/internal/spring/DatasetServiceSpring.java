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
package org.talend.components.api.service.internal.spring;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.talend.components.api.component.DatasetDefinition;
import org.talend.components.api.component.DatasetImageType;
import org.talend.components.api.exception.DatasetException;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.api.service.DatasetService;
import org.talend.components.api.service.internal.DatasetRegistry;
import org.talend.components.api.service.internal.DatasetServiceImpl;
import org.talend.daikon.exception.error.CommonErrorCodes;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.service.Repository;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * This is a spring only class that is instantiated by the spring framework. It delegates all its calls to the
 * DatasetServiceImpl delegate create in it's constructor. This delegate uses a Dataset registry implementation specific
 * to spring.
 */

@RestController
@Api(value = "dataset", basePath = DatasetServiceSpring.BASE_PATH, description = "Dataset services")
@Service
public class DatasetServiceSpring implements DatasetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatasetServiceSpring.class);

    public static final String BASE_PATH = "/dataset"; //$NON-NLS-1$

    private DatasetService datasetServiceDelegate;

    @Autowired
    public DatasetServiceSpring(final ApplicationContext context) {
        this.datasetServiceDelegate = new DatasetServiceImpl(new DatasetRegistry() {

            @Override
            public Map<String, DatasetDefinition> getDatasets() {
                Map<String, DatasetDefinition> compDefs = context.getBeansOfType(DatasetDefinition.class);
                return compDefs;
            }

        });
    }

    @Override
    @RequestMapping(value = BASE_PATH + "/properties/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ComponentProperties getComponentProperties(
            @PathVariable(value = "name") @ApiParam(name = "name", value = "Name of the dataset") String name) {
        return datasetServiceDelegate.getComponentProperties(name);
    }

    @Override
    @RequestMapping(value = BASE_PATH + "/components/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String[] getComponents(
            @PathVariable(value = "name") @ApiParam(name = "name", value = "Name of the dataset") String name) {
        return datasetServiceDelegate.getComponents(name);
    }

    @Override
    @RequestMapping(value = BASE_PATH + "/sample/{name}/{size}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String getSample(
            @PathVariable(value = "name") @ApiParam(name = "name", value = "Name of the datastore") String name,
            @PathVariable(value = "size") @ApiParam(name = "size", value = "Max return rows of sample") Integer size,
            @ApiParam(name = "properties", value = "Setting for the current dataset") @RequestBody Properties properties) {
        return datasetServiceDelegate.getSample(name, size, properties);
    }

    @Override
    @RequestMapping(value = BASE_PATH + "/validate/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getValidateChecks(@PathVariable(value = "name") @ApiParam(name = "name", value = "Name of the dataset") String name) {
        return datasetServiceDelegate.getValidateChecks(name);
    }

    @Override
    @RequestMapping(value = BASE_PATH + "/jsonSchema/{name}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody String getJSONSchema(
            @PathVariable(value = "name") @ApiParam(name = "name", value = "Name of the dataset") String name) {
        return datasetServiceDelegate.getJSONSchema(name);
    }

    @Override
    @RequestMapping(value = BASE_PATH + "/definition/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody DatasetDefinition getDatasetDefinition(
            @PathVariable(value = "name") @ApiParam(name = "name", value = "Name of the dataset") String name) {
        return datasetServiceDelegate.getDatasetDefinition(name);
    }

    @Override
    @RequestMapping(value = BASE_PATH + "/dependencies/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Set<String> getMavenUriDependencies(
            @PathVariable(value = "name") @ApiParam(name = "name", value = "Name of the dataset") String name) {
        return datasetServiceDelegate.getMavenUriDependencies(name);
    }

    @Override
    @RequestMapping(value = BASE_PATH + "/possibleDatasets", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DatasetDefinition> getPossibleDatasets(
            @ApiParam(name = "properties", value = "Dataset properties") @RequestBody ComponentProperties... properties)
            throws Throwable {
        return datasetServiceDelegate.getPossibleDatasets(properties);
    }

    @Override
    @RequestMapping(value = BASE_PATH + "/makeFormCancelable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Properties makeFormCancelable(
            @ApiParam(name = "properties", value = "Properties related to the form") @RequestBody Properties properties,
            @ApiParam(name = "formName", value = "Name of the form") String formName) {
        return datasetServiceDelegate.makeFormCancelable(properties, formName);
    }

    @Override
    @RequestMapping(value = BASE_PATH + "/commitFormValues", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Properties cancelFormValues(
            @ApiParam(name = "properties", value = "Properties related to the form.") @RequestBody Properties properties,
            @ApiParam(name = "formName", value = "Name of the form") String formName) {
        return datasetServiceDelegate.cancelFormValues(properties, formName);
    }

    @Override
    @RequestMapping(value = BASE_PATH + "/properties/{propName}/validate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Properties validateProperty(
            @PathVariable(value = "propName") @ApiParam(name = "propName", value = "Name of property") String propName,
            @ApiParam(name = "properties", value = "Properties holding the property to validate") @RequestBody Properties properties)
            throws Throwable {
        datasetServiceDelegate.validateProperty(propName, properties);
        return properties;
    }

    @Override
    @RequestMapping(value = BASE_PATH + "/properties/{propName}/beforeActivate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Properties beforePropertyActivate(
            @PathVariable(value = "propName") @ApiParam(name = "propName", value = "Name of property") String propName,
            @ApiParam(name = "properties", value = "Properties holding the property to activate") @RequestBody Properties properties)
            throws Throwable {
        datasetServiceDelegate.beforePropertyActivate(propName, properties);
        return properties;
    }

    @Override
    @RequestMapping(value = BASE_PATH + "/properties/{propName}/beforeRender", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Properties beforePropertyPresent(
            @PathVariable(value = "propName") @ApiParam(name = "propName", value = "Name of property") String propName,
            @ApiParam(name = "properties", value = "Properties holding the property that is going to be presented") @RequestBody Properties properties)
            throws Throwable {
        datasetServiceDelegate.beforePropertyPresent(propName, properties);
        return properties;
    }

    @Override
    @RequestMapping(value = BASE_PATH + "/properties/{propName}/after", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Properties afterProperty(
            @PathVariable(value = "propName") @ApiParam(name = "propName", value = "Name of property") String propName,
            @ApiParam(name = "properties", value = "Properties holding the value that just has been set") @RequestBody Properties properties)
            throws Throwable {
        datasetServiceDelegate.afterProperty(propName, properties);
        return properties;
    }

    @Override
    @RequestMapping(value = BASE_PATH + "/properties/beforeFormPresent/{formName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Properties beforeFormPresent(
            @PathVariable(value = "formName") @ApiParam(name = "formName", value = "Name of form") String formName,
            @ApiParam(name = "properties", value = "Properties holding the form to be presented") @RequestBody Properties properties)
            throws Throwable {
        datasetServiceDelegate.beforeFormPresent(formName, properties);
        return properties;
    }

    @Override
    @RequestMapping(value = BASE_PATH + "/properties/afterFormNext/{formName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Properties afterFormNext(
            @PathVariable(value = "formName") @ApiParam(name = "formName", value = "Name of form") String formName,
            @ApiParam(name = "properties", value = "Properties related to the current wizard form") @RequestBody Properties properties)
            throws Throwable {
        datasetServiceDelegate.afterFormNext(formName, properties);
        return properties;
    }

    @Override
    @RequestMapping(value = BASE_PATH + "/properties/afterFormBack/{formName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Properties afterFormBack(
            @PathVariable(value = "formName") @ApiParam(name = "formName", value = "Name of form") String formName,
            @ApiParam(name = "properties", value = "Properties related to the current form") @RequestBody Properties properties)
            throws Throwable {
        datasetServiceDelegate.afterFormBack(formName, properties);
        return properties;
    }

    @Override
    @RequestMapping(value = BASE_PATH + "/properties/afterFormFinish/{formName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Properties afterFormFinish(
            @PathVariable(value = "formName") @ApiParam(name = "formName", value = "Name of form") String formName,
            @ApiParam(name = "properties", value = "Properties holding the current form to be closed.") @RequestBody Properties properties)
            throws Throwable {
        datasetServiceDelegate.afterFormFinish(formName, properties);
        return properties;
    }

    @Override
    @RequestMapping(value = BASE_PATH + "/names", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Set<String> getAllDatasetNames() {
        return datasetServiceDelegate.getAllDatasetNames();
    }

    @Override
    @RequestMapping(value = BASE_PATH + "/definitions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Set<DatasetDefinition> getAllDatasets() {
        return datasetServiceDelegate.getAllDatasets();
    }

    private void sendStreamBack(final HttpServletResponse response, InputStream inputStream) {
        try {
            if (inputStream != null) {
                try {
                    IOUtils.copy(inputStream, response.getOutputStream());
                } catch (IOException e) {
                    throw new DatasetException(CommonErrorCodes.UNEXPECTED_EXCEPTION, e);
                } finally {
                    inputStream.close();
                }
            } else {// could not get icon so respond a resource_not_found : 404
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (IOException e) {// is sendError fails or inputstream fails when closing
            LOGGER.error("sendError failed or inputstream failed when closing.", e); //$NON-NLS-1$
            throw new DatasetException(CommonErrorCodes.UNEXPECTED_EXCEPTION, e);
        }
    }

    @Override
    // this cannot be used as is as a rest api so see getWizardPngIconRest.
    public InputStream getDatasetPngImage(String componentName, DatasetImageType imageType) {
        return datasetServiceDelegate.getDatasetPngImage(componentName, imageType);
    }

    @Override
    public void setRepository(Repository repository) {
        datasetServiceDelegate.setRepository(repository);
    }

    @RequestMapping(value = BASE_PATH + "/icon/{name}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    @ApiOperation(value = "Return the icon related to the Dataset", notes = "return the png image related to the Dataset name parameter.")
    public void getDatasetsImageRest(
            @PathVariable(value = "name") @ApiParam(name = "name", value = "Name of Dataset") String name,
            @PathVariable(value = "type") @ApiParam(name = "type", value = "Type of the icon requested") DatasetImageType type,
            final HttpServletResponse response) {
        InputStream componentPngImageStream = getDatasetPngImage(name, type);
        sendStreamBack(response, componentPngImageStream);
    }

    // FIXME - make this work for web
    @Override
    public String storeProperties(Properties properties, String name, String repositoryLocation, String schemaPropertyName) {
        return datasetServiceDelegate.storeProperties(properties, name, repositoryLocation, schemaPropertyName);
    }

    @Override
    public boolean setNestedPropertiesValues(ComponentProperties targetProperties, Properties nestedValues) {
        return datasetServiceDelegate.setNestedPropertiesValues(targetProperties, nestedValues);
    }

}
