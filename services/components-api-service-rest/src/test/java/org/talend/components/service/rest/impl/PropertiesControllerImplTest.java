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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.talend.components.service.rest.Application;
import org.talend.components.service.rest.FormDataContainer;

import static com.jayway.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
public class PropertiesControllerImplTest {

    @LocalServerPort
    private int localServerPort;

    @Test
    public void getProperties() throws Exception {
        String dataStoreName = "FullExampleDatastore";
        given().accept(APPLICATION_JSON_UTF8_VALUE) //
                .expect() //
                .statusCode(200).log().ifError() //
                .with().port(localServerPort) //
                .get("/properties/{name}", dataStoreName);

        // {"jsonSchema":{"title":"","type":"object","properties":{"tag":{"title":"property.tag.displayName","type":"string"},"tagId":{"title":"property.tagId.displayName","type":"integer"}}},"properties":{},"uiSchema":{"ui:order":["tag","tagId"]}}
    }

    @Test
    public void validateProperties() throws Exception {
        String dataStoreName = "FullExampleDatastore";
        FormDataContainer formDataContainer = new FormDataContainer();
        formDataContainer.setFormData("{\"tag\":\"toto\", \"tagId\":\"256\"}");
        given().accept(APPLICATION_JSON_UTF8_VALUE) //
                .expect() //
                .statusCode(204).log().ifError() //
                .with().port(localServerPort) //
                .content(formDataContainer) //
                .contentType(APPLICATION_JSON_UTF8_VALUE) //
                .post("/properties/{name}/validate", dataStoreName);
    }

    @Test
    public void validateProperty() throws Exception {
        String dataStoreName = "FullExampleDatastore";
        String propName = "tagId";
        FormDataContainer formDataContainer = new FormDataContainer();
        formDataContainer.setFormData("{\"tag\":\"toto\", \"tagId\":\"256\"}");
        given().accept(APPLICATION_JSON_UTF8_VALUE) //
                .expect() //
                .statusCode(204).log().ifError() //
                .with().port(localServerPort) //
                .content(formDataContainer) //
                .contentType(APPLICATION_JSON_UTF8_VALUE) //
                .post("/properties/{name}/validate/{propName}", dataStoreName, propName);
    }

    @Test
    public void getDatasetProperties() throws Exception {
        String dataStoreName = "FullExampleDatastore";
        FormDataContainer formDataContainer = new FormDataContainer();
        formDataContainer.setFormData("{\"tag\":\"toto\", \"tagId\":\"256\"}");
        given().accept(APPLICATION_JSON_UTF8_VALUE) //
                .expect() //
                .statusCode(200).log().ifError() //
                .with().port(localServerPort) //
                .content(formDataContainer) //
                .contentType(APPLICATION_JSON_UTF8_VALUE) //
                .post("/properties/{name}/dataset", dataStoreName);
    }

}
