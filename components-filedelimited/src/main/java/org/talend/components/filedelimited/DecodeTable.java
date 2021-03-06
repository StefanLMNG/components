package org.talend.components.filedelimited;

import java.util.List;

import org.apache.commons.lang3.reflect.TypeLiteral;
import org.talend.components.common.BasedOnSchemaTable;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.property.Property;

import static org.talend.daikon.properties.property.PropertyFactory.newProperty;

public class DecodeTable extends BasedOnSchemaTable {

    private static final TypeLiteral<List<Boolean>> LIST_BOOLEAN_TYPE = new TypeLiteral<List<Boolean>>() {// empty
    };

    public Property<List<Boolean>> decode = newProperty(LIST_BOOLEAN_TYPE, "decode");

    public DecodeTable(String name) {
        super(name);
    }

    @Override
    public void setupLayout() {
        super.setupLayout();
        Form mainForm = getForm(Form.MAIN);
        mainForm.addColumn(decode);
    }

}
