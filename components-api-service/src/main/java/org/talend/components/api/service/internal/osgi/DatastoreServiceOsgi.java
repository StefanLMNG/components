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
import org.talend.components.api.component.DatastoreDefinition;
import org.talend.components.api.component.DatastoreImageType;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.api.service.DatastoreService;
import org.talend.components.api.service.internal.DatastoreRegistry;
import org.talend.components.api.service.internal.DatastoreServiceImpl;
import org.talend.daikon.NamedThing;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.service.Repository;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

/**
 * This is the OSGI specific service implementation that completely delegates the implementation to the Framework
 * agnostic {@link DatastoreServiceImpl}
 */
@Component
public class DatastoreServiceOsgi implements DatastoreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatastoreServiceOsgi.class);

    GlobalI18N gctx;

    @Reference
    public void osgiInjectGlobalContext(GlobalI18N aGctx) {
        this.gctx = aGctx;
    }

    private final class DatastoreRegistryOsgi implements DatastoreRegistry {

        private BundleContext bc;

        public DatastoreRegistryOsgi(BundleContext bc) {
            this.bc = bc;

        }

        private Map<String, DatastoreDefinition> datastores;

        protected <T extends NamedThing> Map<String, T> populateMap(Class<T> cls) {
            Map<String, T> map = new HashMap<>();
            try {
                String typeCanonicalName = cls.getCanonicalName();
                Collection<ServiceReference<T>> serviceReferences = bc.getServiceReferences(cls, null);
                for (ServiceReference<T> sr : serviceReferences) {
                    T service = bc.getService(sr);
                    Object nameProp = sr.getProperty("datastore.name"); //$NON-NLS-1$
                    if (nameProp instanceof String) {
                        map.put((String) nameProp, service);
                        LOGGER.info("Registered the datastore: " + nameProp + "(" + service.getClass().getCanonicalName() + ")"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
                    } else {// no name set so issue a warning
                        LOGGER.warn("Failed to register the following datastore because it is unnamed: " //$NON-NLS-1$
                                + service.getClass().getCanonicalName());
                    }
                }
                if (map.isEmpty()) {// warn if not comonents where registered
                    LOGGER.warn("Could not find any registered datastores for type :" + typeCanonicalName); //$NON-NLS-1$
                } // else everything is fine
            } catch (InvalidSyntaxException e) {
                LOGGER.error("Failed to get DatastoreDefinition services", e); //$NON-NLS-1$
            }
            return map;
        }

        @Override
        public Map<String, DatastoreDefinition> getDatastores() {
            if (datastores == null) {
                datastores = populateMap(DatastoreDefinition.class);
            }
            return datastores;
        }

    }

    private DatastoreService datastoreServiceDelegate;

    @Activate
    void activate(BundleContext bundleContext) throws InvalidSyntaxException {
        this.datastoreServiceDelegate = new DatastoreServiceImpl(new DatastoreRegistryOsgi(bundleContext));
    }

    @Override
    public ComponentProperties getComponentProperties(String name) {
        return datastoreServiceDelegate.getComponentProperties(name);
    }

    @Override
    public String[] getDatasets(String name) {
        return datastoreServiceDelegate.getDatasets(name);
    }

    @Override
    public List<String> getValidateChecks(String name) {
        return datastoreServiceDelegate.getValidateChecks(name);
    }

    @Override
    public String getJSONSchema(String name) {
        return datastoreServiceDelegate.getJSONSchema(name);
    }

    @Override
    public DatastoreDefinition getDatastoreDefinition(String name) {
        return datastoreServiceDelegate.getDatastoreDefinition(name);
    }

    @Override
    public List<DatastoreDefinition> getPossibleDatastores(ComponentProperties... properties) throws Throwable {
        return datastoreServiceDelegate.getPossibleDatastores(properties);
    }

    @Override
    public Properties makeFormCancelable(Properties properties, String formName) {
        return datastoreServiceDelegate.makeFormCancelable(properties, formName);
    }

    @Override
    public Properties cancelFormValues(Properties properties, String formName) {
        return datastoreServiceDelegate.cancelFormValues(properties, formName);
    }

    public ComponentProperties commitFormValues(ComponentProperties properties, String formName) {
        // FIXME - remove this
        return properties;
    }

    @Override
    public Properties validateProperty(String propName, Properties properties) throws Throwable {
        return datastoreServiceDelegate.validateProperty(propName, properties);
    }

    @Override
    public Properties beforePropertyActivate(String propName, Properties properties) throws Throwable {
        return datastoreServiceDelegate.beforePropertyActivate(propName, properties);
    }

    @Override
    public Properties beforePropertyPresent(String propName, Properties properties) throws Throwable {
        return datastoreServiceDelegate.beforePropertyPresent(propName, properties);
    }

    @Override
    public Properties afterProperty(String propName, Properties properties) throws Throwable {
        return datastoreServiceDelegate.afterProperty(propName, properties);
    }

    @Override
    public Properties beforeFormPresent(String formName, Properties properties) throws Throwable {
        return datastoreServiceDelegate.beforeFormPresent(formName, properties);
    }

    @Override
    public Properties afterFormNext(String formName, Properties properties) throws Throwable {
        return datastoreServiceDelegate.afterFormNext(formName, properties);
    }

    @Override
    public Properties afterFormBack(String formName, Properties properties) throws Throwable {
        return datastoreServiceDelegate.afterFormBack(formName, properties);
    }

    @Override
    public Properties afterFormFinish(String formName, Properties properties) throws Throwable {
        return datastoreServiceDelegate.afterFormFinish(formName, properties);
    }

    @Override
    public Set<String> getAllDatastoreNames() {
        return datastoreServiceDelegate.getAllDatastoreNames();
    }

    @Override
    public Set<DatastoreDefinition> getAllDatastores() {
        return datastoreServiceDelegate.getAllDatastores();
    }

    @Override
    public InputStream getDatastorePngImage(String datastoreName, DatastoreImageType imageType) {
        return datastoreServiceDelegate.getDatastorePngImage(datastoreName, imageType);
    }

    @Override
    public String storeProperties(Properties properties, String name, String repositoryLocation, String schemaPropertyName) {
        return datastoreServiceDelegate.storeProperties(properties, name, repositoryLocation, schemaPropertyName);
    }

    @Override
    public void setRepository(Repository repository) {
        datastoreServiceDelegate.setRepository(repository);
    }

    @Override
    public Set<String> getMavenUriDependencies(String datastoreName) {
        return datastoreServiceDelegate.getMavenUriDependencies(datastoreName);
    }

    @Override
    public boolean setNestedPropertiesValues(ComponentProperties targetProperties, Properties nestedValues) {
        return datastoreServiceDelegate.setNestedPropertiesValues(targetProperties, nestedValues);
    }

}
