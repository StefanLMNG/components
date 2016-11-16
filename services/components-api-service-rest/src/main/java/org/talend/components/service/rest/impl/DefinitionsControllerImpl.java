/*
 * Copyright (C) 2006-2015 Talend Inc. - www.talend.com
 *
 * This source code is available under agreement available at
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
 *
 * You should have received a copy of the agreement
 * along with this program; if not, write to Talend SA
 * 9 rue Pages 92150 Suresnes, France
 */

package org.talend.components.service.rest.impl;

import static java.util.stream.StreamSupport.stream;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.talend.components.api.RuntimableDefinition;
import org.talend.components.api.component.ComponentDefinition;
import org.talend.components.common.datastore.DatastoreDefinition;
import org.talend.components.service.rest.DefinitionType;
import org.talend.components.service.rest.DefinitionTypeConverter;
import org.talend.components.service.rest.DefinitionsController;
import org.talend.components.service.rest.dto.DefinitionDTO;
import org.talend.components.service.rest.dto.TopologyDTO;
import org.talend.components.service.rest.dto.TopologyDTOConverter;
import org.talend.daikon.annotation.ServiceImplementation;
import org.talend.daikon.definition.service.DefinitionRegistryService;

/**
 * Definition controller..
 */
@ServiceImplementation
@RequestMapping(produces = APPLICATION_JSON_UTF8_VALUE)
public class DefinitionsControllerImpl implements DefinitionsController {

    /** This class' logger. */
    private static final Logger logger = getLogger(DefinitionsControllerImpl.class);

    @Autowired
    private DefinitionRegistryService definitionServiceDelegate;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(DefinitionType.class, new DefinitionTypeConverter());
        dataBinder.registerCustomEditor(TopologyDTO.class, new TopologyDTOConverter());
    }

    /**
     * Return all known definitions that match the given type.
     *
     * @param type the wanted definition type.
     * @return all known definitions that match the given type.
     * @returnWrapped java.lang.Iterable<org.talend.components.service.rest.dto.DefinitionDTO>
     */
    @Override
    public Iterable<DefinitionDTO> listDefinitions(@PathVariable("type") DefinitionType type) {
        logger.debug("listing definitions for {} ", type);

        Iterable<? extends RuntimableDefinition> definitionsByType = //
                definitionServiceDelegate.getDefinitionsMapByType(type.getTargetClass()).values();

        return stream(definitionsByType.spliterator(), false)
                // this if...else is ugly, one should try to find a better solution
                .map(c -> {
                    if (type == DefinitionType.COMPONENT) {
                        return new DefinitionDTO((ComponentDefinition)c);
                    }
                    else {
                        return new DefinitionDTO((DatastoreDefinition)c);
                    }
                }) //
                .collect(Collectors.toList());
    }

    /**
     * Return components that match the given topology.
     *
     * @param topology the wanted topology.
     * @return the list of all definitions that match the wanted topology.
     * @returnWrapped java.lang.Iterable<org.talend.components.service.rest.dto.DefinitionDTO>
     */
    @Override
    public Iterable<DefinitionDTO> listComponentDefinitions(@RequestParam(value = "topology", required = false) TopologyDTO topology) {
        final Collection<ComponentDefinition> definitions = //
                definitionServiceDelegate.getDefinitionsMapByType(ComponentDefinition.class).values();

        Stream<ComponentDefinition> stream = definitions.stream();

        if (topology != null) {
            stream = stream.filter(c -> c.getSupportedConnectorTopologies().contains(topology.getTopology()));
        }

        final List<DefinitionDTO> result = stream //
                .map(DefinitionDTO::new) //
                .collect(Collectors.toList());

        logger.debug("found {} component definitions for topology {}", result.size(), topology);

        return result;
    }


}