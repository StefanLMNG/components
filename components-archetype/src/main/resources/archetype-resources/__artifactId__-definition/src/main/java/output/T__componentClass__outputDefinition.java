package ${package}.output;

import aQute.bnd.annotation.component.Component;
import ${package}.api.Constants;
import ${package}.api.component.ComponentDefinition;
import ${package}.api.component.OutputComponentDefinition;
import ${package}.api.component.runtime.Sink;
import ${package}.api.properties.ComponentProperties;
import ${package}.${component-name}.${componentClass}Definition;

@Component(name = Constants.COMPONENT_BEAN_PREFIX
        + T${componentClass}OutputDefinition.COMPONENT_NAME, provide = ComponentDefinition.class)
public class T${componentClass}OutputDefinition extends ${componentClass}Definition implements OutputComponentDefinition {
    public static final String COMPONENT_NAME = "t${componentClass}Output"; //$NON-NLS-1$

    public T${componentClass}OutputDefinition() {
        super(COMPONENT_NAME);
    }

    @Override
    public Class<? extends ComponentProperties> getPropertyClass() {
        return T${componentClass}OutputProperties.class;
    }

    @Override
    public Sink getRuntime() {
        // TODO(rskraba): redirect to the runtime to actually load.
        try {
            return (Sink) Class.forName("${package}.${component-name}.runtime_3_0.${componentClass}Sink").newInstance();
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
