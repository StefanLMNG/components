package ${package}.connection;

import aQute.bnd.annotation.component.Component;
import ${package}.api.Constants;
import ${package}.api.component.ComponentDefinition;
import ${package}.api.component.EndpointComponentDefinition;
import ${package}.api.component.runtime.SourceOrSink;
import ${package}.api.properties.ComponentProperties;
import ${package}.${component-name}.${componentClass}ConnectionProperties;
import ${package}.${component-name}.${componentClass}Definition;

@Component(name = Constants.COMPONENT_BEAN_PREFIX
        + T${componentClass}ConnectionDefinition.COMPONENT_NAME, provide = ComponentDefinition.class)
public class T${componentClass}ConnectionDefinition extends ${componentClass}Definition implements EndpointComponentDefinition {

    public static final String COMPONENT_NAME = "t${componentClass}Connection"; //$NON-NLS-1$

    public T${componentClass}ConnectionDefinition() {
        super(COMPONENT_NAME);
    }

    @Override
    public Class<? extends ComponentProperties> getPropertyClass() {
        return ${componentClass}ConnectionProperties.class;
    }

    @Override
    public SourceOrSink getRuntime() {
        // TODO(rskraba): redirect to the runtime to actually load.
        try { 
            return (SourceOrSink) Class.forName("org.talend.components.${component-name}.runtime_3_0.${componentClass}SourceOrSink").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}