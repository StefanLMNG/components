package ${package}.output;

import org.apache.avro.Schema;
import org.talend.components.${component-name}.${componentClass}IOBasedProperties;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;

import java.util.ArrayList;
import java.util.List;

import static org.talend.daikon.properties.presentation.Widget.widget;
import static org.talend.daikon.properties.property.PropertyFactory.*;

public class T${componentClass}OutputProperties extends ${componentClass}IOBasedProperties {

    /**
     * named constructor to be used is these properties are nested in other properties. Do not subclass this method for
     * initialization, use {@link #init()} instead.
     *
     * @param name
     */
    public T${componentClass}OutputProperties(String name) {
        super(name);
    }

    // TODO implement setup Properties
    @Override
    public void setupProperties() {
        super.setupProperties();
    }

    // TODO implement setupLayout
    @Override
    public void setupLayout() {
        super.setupLayout();
        Form mainForm = getForm(Form.MAIN); 
        Form advancedForm = new Form(this, Form.ADVANCED);
    }
    
    // TODO implement refreshLayout
    @Override
    public void refreshLayout(Form form) {
        super.refreshLayout(form);
        if (form.getName().equals(Form.MAIN)) {
            
        } else if (form.getName().equals(Form.ADVANCED)) {
    
        }
    }
}
