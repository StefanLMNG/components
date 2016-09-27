package ${packageComponent};

import ${package}.api.AbstractComponentFamilyDefinition;
import ${package}.api.ComponentInstaller;
import ${package}.api.Constants;

import aQute.bnd.annotation.component.Component;
import ${packageComponent}.input.${componentClass}InputDefinition;
import ${packageComponent}.output.${componentClass}OutputDefinition;

/**
 * Install all of the definitions provided for the FullExample family of components.
 */
@Component(name = Constants.COMPONENT_INSTALLER_PREFIX
        + ${componentClass}ComponentFamilyDefinition.NAME, provide = ComponentInstaller.class)
public class ${componentClass}ComponentFamilyDefinition extends AbstractComponentFamilyDefinition implements ComponentInstaller {

    public static final String NAME = "${component-name}";

    public ${componentClass}ComponentFamilyDefinition() {
        super(NAME, new ${componentClass}DatastoreDefinition(), new ${componentClass}DatastoreDefinition(), new ${componentClass}InputDefinition(),
                new ${componentClass}OutputDefinition());
    }

    public void install(ComponentFrameworkContext ctx) {
        ctx.registerComponentFamilyDefinition(this);
    }
}
