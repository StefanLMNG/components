package ${package};

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ${package}.api.Constants;
import ${package}.api.component.AbstractDatastoreDefinition;
import ${package}.api.component.DatastoreDefinition;
import ${package}.api.component.runtime.SourceOrSink;
import ${package}.api.properties.ComponentProperties;

import aQute.bnd.annotation.component.Component;
import ${package}.connection.T${componentClass}ConnectionDefinition;
import ${package}.runtime.${componentClass}SourceOrSink;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.ValidationResult;
import org.talend.json.schema.JsonUtil;

@Component(name = Constants.DATASTORE_BEAN_PREFIX + ${componentClass}Datastore.DATASTORE_NAME, provide = DatastoreDefinition.class)
public class ${componentClass}Datastore extends AbstractDatastoreDefinition {

    private static final Logger LOG = LoggerFactory.getLogger(${componentClass}Datastore.class);

    public static final String DATASTORE_NAME = "${componentClass}Datastore"; //$NON-NLS-1$

    public ${componentClass}Datastore() {
        super(DATASTORE_NAME);
    }

    public ${componentClass}Datastore(String datastoreName) {
        super(datastoreName);
    }

    @Override
    public Class<? extends ComponentProperties> getPropertyClass() {
        return ${componentClass}ConnectionProperties.class;
    }

    @Override
    public String[] getFamilies() {
        return new String[] { "Databases/${componentClass}", "Big Data/${componentClass}" }; //$NON-NLS-1$  //$NON-NLS-2$
    }

    @Override
    public List<String> getValidateChecks() {
        return Arrays.asList("ping_${component-name}");
    }

    @Override
    public Boolean doValidate(String name, Properties properties) {
        if ("ping_${component-name}".equals(name)) {
            try {
                T${componentClass}ConnectionDefinition connectionDefinition = new
                        T${componentClass}ConnectionDefinition();
                SourceOrSink runtime = connectionDefinition.getRuntime();
                runtime.initialize(null, (ComponentProperties)properties);
                ValidationResult validate = runtime.validate(null);
                return true;
                //return "{\"status\":\"ok\"}";//TODO(bchen) change it!
            } catch (Exception e) {
                //return "{\"status\":\"error\", \"message\":"+e.getMessage()+"}";
                return false;
            }
        }
        // link to the check in ${component-name}_runtime_3_0, using the classloader defined in MavenBooter.
        return true;
    }

    @Override
    public String[] getDatasets() {
        return new String[] { new ${componentClass}Dataset().getName() };
    }

    @Override
    public String getJSONSchema() {
        return JsonUtil.toJson(createProperties(), true); //true means contains json-ui-schema
    }

    @Override
    public String getMavenGroupId() {
        return "${package}"; //$NON-NLS-1$
    }

    @Override
    public String getMavenArtifactId() {
        return "${artifactId}"; //$NON-NLS-1$
    }

}
