package ${package}.input;

import ${package}.${component-name}.${componentClass}IOBasedProperties;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.property.Property;

import static org.talend.daikon.properties.property.PropertyFactory.newString;


public class T${componentClass}InputProperties extends ${componentClass}IOBasedProperties {
    /**
     * named constructor to be used is these properties are nested in other properties. Do not subclass this method for
     * initialization, use {@link #init()} instead.
     *
     * @param name
     */
    public T${componentClass}InputProperties(String name) {
        super(name);
    }
    
    // property example
    //public Property<String> query = newString("query");

    @Override
    public void setupLayout(){
        super.setupLayout();
        Form form = getForm(Form.MAIN);
        // add property in layout
        //form.addRow(query);
    }

}
