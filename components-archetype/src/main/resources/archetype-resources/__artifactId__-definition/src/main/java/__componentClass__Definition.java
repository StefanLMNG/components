package ${package};

import ${package}.api.component.AbstractComponentDefinition;
import org.talend.daikon.properties.property.Property;

public abstract class ${componentClass}Definition extends AbstractComponentDefinition {

    public ${componentClass}Definition(String componentName) {
        super(componentName);
    }

    @Override
    public String[] getFamilies() {
        return new String[]{"Databases/${componentClass}", "Big Data/${componentClass}"};
    }


    @Override
    public String getMavenGroupId() {
        return "${package}";
    }

    @Override
    public String getMavenArtifactId() {
        return "${artifactId}";
    }

    @Override
    public Property[] getReturnProperties() {
        return new Property[0];
    }
}
