package ${package}.input;

import aQute.bnd.annotation.component.Component;
import ${package}.api.Constants;
import ${package}.api.component.ComponentDefinition;
import ${package}.api.component.InputComponentDefinition;
import ${package}.api.component.runtime.Source;
import ${package}.api.properties.ComponentProperties;
import ${package}.${component-name}.${componentClass}Definition;

@Component(name = Constants.COMPONENT_BEAN_PREFIX
        + T${componentClass}InputDefinition.COMPONENT_NAME, provide = ComponentDefinition.class)
public class T${componentClass}InputDefinition extends ${componentClass}Definition implements InputComponentDefinition {

    public static final String COMPONENT_NAME = "t${componentClass}Input"; //$NON-NLS-1$

    public T${componentClass}InputDefinition() {
        super(COMPONENT_NAME);
    }

    @Override
    public Class<? extends ComponentProperties> getPropertyClass() {
        return T${componentClass}InputProperties.class;
    }

    @Override
    public Source getRuntime() {
        // TODO(rskraba): redirect to the runtime to actually load.
        try {
            return (Source) Class.forName("${package}.${component-name}.runtime_3_0.${componentClass}Source").newInstance();
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
