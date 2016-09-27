package ${package};

import ${package}.api.properties.*;
import ${package}.${component-name}.connection.T${componentClass}ConnectionDefinition;
import ${package}.common.UserPasswordProperties;
import org.talend.daikon.properties.PresentationItem;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;

import static org.talend.daikon.properties.presentation.Widget.widget;
import static org.talend.daikon.properties.property.PropertyFactory.newBoolean;
import static org.talend.daikon.properties.property.PropertyFactory.newEnum;
import static org.talend.daikon.properties.property.PropertyFactory.newString;

public class ${componentClass}ConnectionProperties extends ComponentPropertiesImpl implements ComponentReferencePropertiesEnclosing, ConnectionPropertiesProvider<${componentClass}ConnectionProperties> {
    /**
     * named constructor to be used is these properties are nested in other properties. Do not subclass this method for
     * initialization, use {@link #init()} instead.
     *
     * @param name
     */
    public ${componentClass}ConnectionProperties(String name) {
        super(name);
    }

    // Example
    /*
    public enum ${componentClass}Version {
        V_3_0,
        V_2_0
    }

    public Property<${componentClass}Version> version = newEnum("version", ${componentClass}Version.class).setRequired();

    public Property<String> host = newString("host", "localhost").setRequired();

    public Property<String> port = newString("port", "9042").setRequired();

    public Property<Boolean> needAuth = newBoolean("needAuth", false);

    public UserPasswordProperties userPassword = new UserPasswordProperties("userPassword");

    public PresentationItem testConnection = new PresentationItem("testConnection", "Test connection");

    public ComponentReferenceProperties referencedComponent = new ComponentReferenceProperties("referencedComponent", this);
    */
    @Override
    public void setupLayout() {
        super.setupLayout();
        /* Layout Example
        Form wizardForm = new Form(this, "Wizard");
        wizardForm.addRow((Property) newString("name").setRequired());
        wizardForm.addRow(widget(version).setDeemphasize(true));
        wizardForm.addRow(host);
        wizardForm.addColumn(port);
        wizardForm.addRow(needAuth);
        wizardForm.addRow(userPassword.getForm(Form.MAIN));
        wizardForm.addColumn(widget(testConnection).setLongRunning(true).setWidgetType(Widget.BUTTON_WIDGET_TYPE));

        Form mainForm = new Form(this, Form.MAIN);
        mainForm.addRow(version);
        mainForm.addRow(host);
        mainForm.addColumn(port);
        mainForm.addRow(needAuth);
        mainForm.addRow(userPassword.getForm(Form.MAIN));

        Form mainAndRefForm = new Form(this, Form.REFERENCE);
        Widget compListWidget = widget(referencedComponent).setWidgetType(Widget.COMPONENT_REFERENCE_WIDGET_TYPE);
        referencedComponent.componentType.setValue(T${componentClass}ConnectionDefinition.COMPONENT_NAME);
        mainAndRefForm.addRow(compListWidget);
        mainAndRefForm.addRow(mainForm); 
        */
    }

    @Override
    public void afterReferencedComponent() {
        refreshLayout(getForm(Form.MAIN));
        refreshLayout(getForm(Form.REFERENCE));
    }

    public String getReferencedComponentId() {
        return referencedComponent.componentInstanceId.getStringValue();
    }

    public ${componentClass}ConnectionProperties getReferencedConnectionProperties() {
        ${componentClass}ConnectionProperties refProps = (${componentClass}ConnectionProperties) referencedComponent.componentProperties;
        if (refProps != null)
            return refProps;
        return null;
    }

    public void afterNeedAuth() {
        refreshLayout(getForm(Form.MAIN));
        refreshLayout(getForm("Wizard"));
    }

    //TODO implement it after validateConnection method
    //    public ValidationResult validateTestConnection() throws Exception{
    //    }

    @Override
    public void refreshLayout(Form form) {
        super.refreshLayout(form);
        /* Refresh Layout example
        String refComponentIdValue = getReferencedComponentId();
        boolean useOtherConnection = refComponentIdValue != null && refComponentIdValue.startsWith(T${componentClass}ConnectionDefinition.COMPONENT_NAME);
        if (form.getName().equals(Form.MAIN) || form.getName().equals("Wizard")) {
            if (useOtherConnection) {
                form.getWidget(version.getName()).setHidden(true);
                form.getWidget(host.getName()).setHidden(true);
                form.getWidget(port.getName()).setHidden(true);
                form.getWidget(needAuth.getName()).setHidden(true);
                form.getWidget(userPassword.getName()).setHidden(true);
            } else {
                form.getWidget(version.getName()).setHidden(false);
                form.getWidget(host.getName()).setHidden(false);
                form.getWidget(port.getName()).setHidden(false);
                if (needAuth.getValue()) {
                    form.getWidget(userPassword.getName()).setHidden(false);
                } else {
                    form.getWidget(userPassword.getName()).setHidden(true);
                }
            }
        }
        */
    }

    @Override
    public void setConnectionProperties(${componentClass}ConnectionProperties props) {
        // TODO implement setConnectionProperties
    }

    @Override
    public ${componentClass}ConnectionProperties getConnectionProperties() {
        return this;
    }
}
