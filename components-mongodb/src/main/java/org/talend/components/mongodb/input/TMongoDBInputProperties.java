package org.talend.components.mongodb.input;

import org.talend.components.mongodb.MongoDBIOBasedProperties;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.property.Property;

import static org.talend.daikon.properties.property.PropertyFactory.newString;

/**
 * Created by slemoing on 8/2/2016.
 */
public class TMongoDBInputProperties extends MongoDBIOBasedProperties{
    public TMongoDBInputProperties(String name) {
        super(name);
    }

    public Property<String> query = newString("query");

    @Override
    public void setupLayout(){
        super.setupLayout();
        Form form = getForm(Form.MAIN);
        form.addRow(query);
    }
}
