package org.talend.components.mongodb;

import org.apache.commons.lang3.reflect.TypeLiteral;
import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;

import java.util.List;

import static org.talend.daikon.properties.property.PropertyFactory.newProperty;

/**
 * Created by slemoing on 8/30/2016.
 */
public class ReplicaTable extends ComponentPropertiesImpl {
    private static final TypeLiteral<List<String>> LIST_STRING_TYPE = new TypeLiteral<List<String>>() {// empty
    };

    private static final TypeLiteral<List<Boolean>> LIST_BOOLEAN_TYPE = new TypeLiteral<List<Boolean>>() {// empty
    };

    /**
     * named constructor to be used is these properties are nested in other properties. Do not
     * subclass this method for initialization, use {@link #init()} instead.
     */
    public ReplicaTable(String name) {
        super(name);
    }

    public Property<List<String>> replicaHost = newProperty(LIST_STRING_TYPE, "replicaHost");
    public Property<List<String>> replicaPort = newProperty(LIST_STRING_TYPE, "replicaPort");

    @Override
    public void setupLayout() {
        super.setupLayout();
        Form mainForm = new Form(this, Form.MAIN);
        mainForm.addColumn(replicaHost);
        mainForm.addColumn(replicaPort);
    }

    public String getReplica(){

        String replica = "";
        return replica;
    }

}
