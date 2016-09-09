package org.talend.components.mongodb;

import org.talend.components.api.component.AbstractComponentDefinition;
import org.talend.daikon.properties.property.Property;

/**
 * Created by slemoing on 8/2/2016.
 */
public abstract class MongoDBDefinition extends AbstractComponentDefinition {

    public MongoDBDefinition(String componentName) {
        super(componentName);
    }

    @Override
    public String[] getFamilies() {
        return new String[]{"Databases/MongoDB", "Big Data/MongoDB"};
    }

    @Override
    public String getMavenGroupId() {
        return "org.talend.components";
    }

    @Override
    public String getMavenArtifactId() {
        return "components-mongoDB";
    }

    @Override
    public Property[] getReturnProperties() {
        return new Property[0];
    }
}
