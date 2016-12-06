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

package org.talend.components.service.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.talend.components.service.rest.dto.PropertiesValidationResponse;
import org.talend.daikon.annotation.Service;
import org.talend.daikon.serialize.jsonschema.PropertyTrigger;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Service(name = "PropertiesController")
@RestController
@RequestMapping("properties")
public interface PropertiesController {

    @RequestMapping(value = "{name}", method = GET)
    String getProperties(@PathVariable("name") String definitionName);

    /** Validate the coherence of a set of properties for a specific component. **/
    @RequestMapping(value = "{definitionName}/validate", method = POST)
    ResponseEntity<PropertiesValidationResponse> validateProperties(@PathVariable("definitionName") String definitionName,
                                                                    @RequestBody FormDataContainer formData);

    /** Validate one field. */
    @RequestMapping(value = "{definition}/{trigger}/{property}", method = POST)
    ResponseEntity<String> triggerOnProperty(@PathVariable("definition") String definition, //
                                             @PathVariable("trigger") PropertyTrigger trigger, //
                                             @PathVariable("property") String property, //
                                             @RequestBody String formData);

    /** Get dataset properties. Should it be GET? **/
    @RequestMapping(value = "{definitionName}/dataset", method = POST)
    String getDatasetProperties(@PathVariable("definitionName") String definitionName, @RequestBody FormDataContainer formData);

}
