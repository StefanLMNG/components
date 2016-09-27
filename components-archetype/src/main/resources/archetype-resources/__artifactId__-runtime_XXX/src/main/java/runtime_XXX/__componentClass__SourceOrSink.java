package ${package}.runtime_XXX;

import org.apache.avro.Schema;
import ${packageTalend}.api.component.runtime.SourceOrSink;
import ${packageTalend}.api.container.RuntimeContainer;
import ${packageTalend}.api.properties.ComponentProperties;
import ${package}.${componentClass}DatastoreProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.properties.ValidationResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ${componentClass}SourceOrSink implements SourceOrSink{

    protected static final String DATABASE_GLOBALMAP_KEY = "database"; //FIXME only for Studio?

    protected transient ${componentClass}DatastoreProperties properties;

    @Override
    public ValidationResult initialize(RuntimeContainer container, ComponentProperties properties) {
        this.properties = (${componentClass}DatastoreProperties) properties;
        return null;
    }

    @Override
    public ValidationResult validate(RuntimeContainer container) {
        try {
            connect(container);
        } catch (IOException ex) {
            return new ValidationResult().setStatus(ValidationResult.Result.ERROR).setMessage(ex.getMessage());
        }
        return ValidationResult.OK;
    }

    protected MongoClient connect(RuntimeContainer container) throws IOException {
        try {
            return new MongoClient(new MongoClientURI(getUri()));
        } catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
    }

    public ${componentClass}DatastoreProperties getProperties() {
        return properties;
    }

    @Override
    public List<NamedThing> getSchemaNames(RuntimeContainer container) throws IOException {
        return null;
    }

    @Override
    public Schema getEndpointSchema(RuntimeContainer container, String schemaName) throws IOException {
        return null;
    }

    // TODO what kind of check we need to do
    boolean doValidate (String name, ComponentProperties properties){
        try {
            //${componentClass}SourceOrSink mdbsos = new ${componentClass}SourceOrSink();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // TODO check if
    // schema
    public Schema getSchema(RuntimeContainer container, String collection) throws IOException {
        return null;
    }
}
