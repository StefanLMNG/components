package org.talend.components.mongodb.input;

import aQute.bnd.annotation.component.Component;
import org.talend.components.api.Constants;
import org.talend.components.api.component.ComponentDefinition;
import org.talend.components.api.component.InputComponentDefinition;
import org.talend.components.api.component.runtime.Source;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.mongodb.MongoDBDefinition;

@Component(name = Constants.COMPONENT_BEAN_PREFIX
        + TMongoDBInputDefinition.COMPONENT_NAME, provide = ComponentDefinition.class)
public class TMongoDBInputDefinition extends MongoDBDefinition implements InputComponentDefinition{

    public static final String COMPONENT_NAME = "tMongoDBInput"; //$NON-NLS-1$

    public TMongoDBInputDefinition() {
        super(COMPONENT_NAME);
    }

    @Override
    public Class<? extends ComponentProperties> getPropertyClass() {
        return TMongoDBInputProperties.class;
    }

    @Override
    public Source getRuntime() {
        // TODO return MongoDBSource
        return null;
    }
}
