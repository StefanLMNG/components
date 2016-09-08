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
package org.talend.components.api.service.internal.osgi;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.talend.components.api.component.DatasetDefinition;
import org.talend.components.api.component.DatasetImageType;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.api.service.DatasetService;
import org.talend.components.api.service.internal.DatasetRegistry;
import org.talend.components.api.service.internal.DatasetServiceImpl;
import org.talend.daikon.NamedThing;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.service.Repository;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

/**
 * This is the OSGI specific service implementation that completely delegates the implementation to the Framework
 * agnostic {@link DatasetServiceImpl}
 */
@Component
//TODO(bchen) nouse?
public class DatasetServiceOsgi implements DatasetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatasetServiceOsgi.class);

    GlobalI18N gctx;

    @Reference
    public void osgiInjectGlobalContext(GlobalI18N aGctx) {
        this.gctx = aGctx;
    }

    private final class DatasetRegistryOsgi implements DatasetRegistry {

        private BundleContext bc;

        public DatasetRegistryOsgi(BundleContext bc) {
            this.bc = bc;

        }

        private Map<String, DatasetDefinition> datasets;

        protected <T extends NamedThing> Map<String, T> populateMap(Class<T> cls) {
            Map<String, T> map = new HashMap<>();
            try {
                String typeCanonicalName = cls.getCanonicalName();
                Collection<ServiceReference<T>> serviceReferences = bc.getServiceReferences(cls, null);
                for (ServiceReference<T> sr : serviceReferences) {
                    T service = bc.getService(sr);
                    Object nameProp = sr.getProperty("dataset.name"); //$NON-NLS-1$
                    if (nameProp instanceof String) {
                        map.put((String) nameProp, service);
                        LOGGER.info("Registered the dataset: " + nameProp + "(" + service.getClass().getCanonicalName() + ")"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
                    } else {// no name set so issue a warning
                        LOGGER.warn("Failed to register the following dataset because it is unnamed: " //$NON-NLS-1$
                                + service.getClass().getCanonicalName());
                    }
                }
                if (map.isEmpty()) {// warn if not comonents where registered
                    LOGGER.warn("Could not find any registered datasets for type :" + typeCanonicalName); //$NON-NLS-1$
                } // else everything is fine
            } catch (InvalidSyntaxException e) {
                LOGGER.error("Failed to get DatasetDefinition services", e); //$NON-NLS-1$
            }
            return map;
        }

        @Override
        public Map<String, DatasetDefinition> getDatasets() {
            if (datasets == null) {
                datasets = populateMap(DatasetDefinition.class);
            }
            return datasets;
        }

    }

    private DatasetService datasetServiceDelegate;

    @Activate
    void activate(BundleContext bundleContext) throws InvalidSyntaxException {
        this.datasetServiceDelegate = new DatasetServiceImpl(new DatasetRegistryOsgi(bundleContext));
    }

    @Override
    public ComponentProperties getComponentProperties(String name) {
        return datasetServiceDelegate.getComponentProperties(name);
    }

    @Override
    public String[] getComponents(String name) {
        return datasetServiceDelegate.getComponents(name);
    }

    @Override
    public String getSample(String name, Integer size, Properties properties) {
        return datasetServiceDelegate.getSample(name, size, properties);
    }

    @Override
    public List<String> getValidateChecks(String name) {
        return datasetServiceDelegate.getValidateChecks(name);
    }

    @Override
    public String getJSONSchema(String name) {
        return datasetServiceDelegate.getJSONSchema(name);
    }

    @Override
    public DatasetDefinition getDatasetDefinition(String name) {
        return datasetServiceDelegate.getDatasetDefinition(name);
    }

    @Override
    public List<DatasetDefinition> getPossibleDatasets(ComponentProperties... properties) throws Throwable {
        return datasetServiceDelegate.getPossibleDatasets(properties);
    }

    @Override
    public Properties makeFormCancelable(Properties properties, String formName) {
        return datasetServiceDelegate.makeFormCancelable(properties, formName);
    }

    @Override
    public Properties cancelFormValues(Properties properties, String formName) {
        return datasetServiceDelegate.cancelFormValues(properties, formName);
    }

    public ComponentProperties commitFormValues(ComponentProperties properties, String formName) {
        // FIXME - remove this
        return properties;
    }

    @Override
    public Properties validateProperty(String propName, Properties properties) throws Throwable {
        return datasetServiceDelegate.validateProperty(propName, properties);
    }

    @Override
    public Properties beforePropertyActivate(String propName, Properties properties) throws Throwable {
        return datasetServiceDelegate.beforePropertyActivate(propName, properties);
    }

    @Override
    public Properties beforePropertyPresent(String propName, Properties properties) throws Throwable {
        return datasetServiceDelegate.beforePropertyPresent(propName, properties);
    }

    @Override
    public Properties afterProperty(String propName, Properties properties) throws Throwable {
        return datasetServiceDelegate.afterProperty(propName, properties);
    }

    @Override
    public Properties beforeFormPresent(String formName, Properties properties) throws Throwable {
        return datasetServiceDelegate.beforeFormPresent(formName, properties);
    }

    @Override
    public Properties afterFormNext(String formName, Properties properties) throws Throwable {
        return datasetServiceDelegate.afterFormNext(formName, properties);
    }

    @Override
    public Properties afterFormBack(String formName, Properties properties) throws Throwable {
        return datasetServiceDelegate.afterFormBack(formName, properties);
    }

    @Override
    public Properties afterFormFinish(String formName, Properties properties) throws Throwable {
        return datasetServiceDelegate.afterFormFinish(formName, properties);
    }

    @Override
    public Set<String> getAllDatasetNames() {
        return datasetServiceDelegate.getAllDatasetNames();
    }

    @Override
    public Set<DatasetDefinition> getAllDatasets() {
        return datasetServiceDelegate.getAllDatasets();
    }

    @Override
    public InputStream getDatasetPngImage(String datasetName, DatasetImageType imageType) {
        return datasetServiceDelegate.getDatasetPngImage(datasetName, imageType);
    }

    @Override
    public String storeProperties(Properties properties, String name, String repositoryLocation, String schemaPropertyName) {
        return datasetServiceDelegate.storeProperties(properties, name, repositoryLocation, schemaPropertyName);
    }

    @Override
    public void setRepository(Repository repository) {
        datasetServiceDelegate.setRepository(repository);
    }

    @Override
    public Set<String> getMavenUriDependencies(String datasetName) {
        return datasetServiceDelegate.getMavenUriDependencies(datasetName);
    }

    @Override
    public boolean setNestedPropertiesValues(ComponentProperties targetProperties, Properties nestedValues) {
        return datasetServiceDelegate.setNestedPropertiesValues(targetProperties, nestedValues);
    }

}
