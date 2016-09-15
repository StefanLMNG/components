package org.talend.components.mongodb;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;
import org.talend.components.api.properties.ComponentProperties;

/**
 * Unit-tests for {@link MongoDBDefinition} class
 */
public class MongoDBDefinitionTest {

    @Test
    public void testGetMavenArtifactId(){
        MongoDBDefinition mongoDBDefinition = Mockito.mock(MongoDBDefinition.class, Mockito.CALLS_REAL_METHODS);
        System.out.println("before");
        assertEquals("components-mongoDB",mongoDBDefinition.getMavenArtifactId());
        System.out.println("after");
    }
}
