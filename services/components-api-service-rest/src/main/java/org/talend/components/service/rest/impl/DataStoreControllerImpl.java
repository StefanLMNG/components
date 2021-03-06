package org.talend.components.service.rest.impl;

import static java.util.stream.StreamSupport.*;
import static org.slf4j.LoggerFactory.*;

import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.talend.components.api.service.ComponentService;
import org.talend.components.common.datastore.DatastoreDefinition;
import org.talend.components.common.datastore.DatastoreProperties;
import org.talend.components.service.rest.DataStoreController;
import org.talend.components.service.rest.DataStoreDefinitionDTO;
import org.talend.components.service.rest.serialization.JsonSerializationHelper;
import org.talend.daikon.annotation.ServiceImplementation;
import org.talend.daikon.definition.service.DefinitionRegistryService;
import org.talend.daikon.exception.TalendRuntimeException;
import org.talend.daikon.exception.error.CommonErrorCodes;

/**
 * Rest controller in charge of data stores.
 */
@ServiceImplementation
public class DataStoreControllerImpl implements DataStoreController {

    /** This class' logger. */
    private static final Logger log = getLogger(DataStoreControllerImpl.class);

    @Autowired
    private ComponentService componentServiceDelegate;

    @Autowired
    private DefinitionRegistryService definitionServiceDelegate;

    @Autowired
    private JsonSerializationHelper jsonSerializationHelper;

    @Override
    public Iterable<DataStoreDefinitionDTO> listDataStoreDefinitions() {
        log.debug("listing datastore definitions");
        Iterable<DatastoreDefinition> definitionsByType = //
        definitionServiceDelegate.getDefinitionsMapByType(DatastoreDefinition.class).values();

        return stream(definitionsByType.spliterator(), false).map(DataStoreDefinitionDTO::from).collect(Collectors.toList());
    }

    @Override
    public String getDatastoreProperties(@PathVariable String dataStoreName) {
        Validate.notNull(dataStoreName, "Data store name cannot be null.");
        DatastoreDefinition def = definitionServiceDelegate.getDefinitionsMapByType(DatastoreDefinition.class).get(dataStoreName);

        if (def != null) {
            return jsonSerializationHelper.toJson(definitionServiceDelegate.createProperties(def, ""), dataStoreName);
        } else {
            throw TalendRuntimeException.build(CommonErrorCodes.UNREGISTERED_DEFINITION).set(dataStoreName);
        }
    }

    @Override
    public void validateDatastoreDefinition(String dataStoreName, DatastoreProperties properties) {
        log.debug("validate {}", properties);

    }

    @Override
    public boolean checkDatastoreConnection(String dataStoreName, DatastoreProperties datastoreProperties) {
        log.debug("checkDataStoreConnection on {}", dataStoreName); // Shouldn't it be in an aspect?
        Validate.notNull(datastoreProperties, "Data stores properties cannot be null.");
        return false;
    }

    @Override
    public boolean checkDatastoreProperty(String dataStoreName, String propertyName, Object value) {
        return false;
    }

}
