package ${package};

import ${package}.api.component.runtime.Source;
import ${package}.api.properties.ConnectionPropertiesProvider;
import ${package}.runtime.${componentClass}SourceOrSink;
import ${package}.api.properties.ComponentPropertiesImpl;
import ${package}.common.SchemaProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;

import java.io.IOException;
import java.util.List;

import static org.talend.daikon.properties.property.PropertyFactory.newString;


public class ${componentClass}SchemaProperties extends ComponentPropertiesImpl implements
        ConnectionPropertiesProvider<${componentClass}ConnectionProperties> {

    /**
     * named constructor to be used is these properties are nested in other properties. Do not subclass this method for
     * initialization, use {@link #init()} instead.
     *
     * @param name
     */
    public ${componentClass}SchemaProperties(String name) {
        super(name);
    }

    // Properties EXAMPLE
    /*
    public ${componentClass}ConnectionProperties connectionProperties = new ${componentClass}ConnectionProperties("connectionProperties");

    public Property<String> keyspace = newString("keyspace");
    public Property<String> columnFamily = newString("columnFamily");
    public SchemaProperties main = new SchemaProperties("main");
    */
    
    @Override
    public void setupLayout() {
        super.setupLayout();
        // EXAMPLE
        /*
        Form schemaForm = new Form(this, Form.MAIN);
        schemaForm.addRow(Widget.widget(keyspace).setWidgetType(Widget.NAME_SELECTION_AREA_WIDGET_TYPE));
        schemaForm.addRow(Widget.widget(columnFamily).setWidgetType(Widget.NAME_SELECTION_AREA_WIDGET_TYPE));
        schemaForm.addRow(main.getForm(Form.MAIN));//add this line for dataset json
        refreshLayout(schemaForm);//FIXME why need to invoke refreshLayout here? refer to SalesforceModuleProperties

        Form schemaRefForm = new Form(this, Form.REFERENCE);
        schemaRefForm.addRow(Widget.widget(keyspace).setWidgetType(Widget.NAME_SELECTION_REFERENCE_WIDGET_TYPE));
        schemaRefForm.addRow(Widget.widget(columnFamily).setWidgetType(Widget.NAME_SELECTION_REFERENCE_WIDGET_TYPE));
        schemaRefForm.addRow(main.getForm(Form.REFERENCE));//FIXME why need schema ref form here but don't need schema main form above
        refreshLayout(schemaRefForm);
        */
    }

    private ${componentClass}SourceOrSink get${componentClass}SourceOrSink() {
        // TODO(rskraba): redirect to the runtime to actually load.
        try {
            // TODO Check the good version of the runtime
            //return (${componentClass}SourceOrSink) Class.forName("${package}.runtime_3_0.${componentClass}SourceOrSink").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // EXAMPLE OF VALIDATION
    /* 
    public ValidationResult beforeKeyspace() {
        ${componentClass}SourceOrSink ${component-name}SourceOrSink = get${componentClass}SourceOrSink();
        ${component-name}SourceOrSink.initialize(null, connectionProperties);
        try {
            List<NamedThing> keyspaceNames = ${component-name}SourceOrSink.getKeyspaceNames(null);
            keyspace.setPossibleValues(keyspaceNames);
        } catch (IOException e) {
            return new ValidationResult().setStatus(ValidationResult.Result.ERROR).setMessage(e.getMessage());
        }
        return ValidationResult.OK;
    }
    */ 

    @Override
    public void setConnectionProperties(${componentClass}ConnectionProperties props) {
        connectionProperties = props;
    }

    @Override
    public ${componentClass}ConnectionProperties getConnectionProperties() {
        return connectionProperties;
    }
}