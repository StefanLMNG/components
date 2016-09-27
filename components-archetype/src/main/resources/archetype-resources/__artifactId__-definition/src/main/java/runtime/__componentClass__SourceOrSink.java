package ${package}.${component-name}.runtime;

import org.apache.avro.Schema;
import ${package}.api.component.runtime.SourceOrSink;
import ${package}.api.container.RuntimeContainer;
import ${package}.api.properties.ComponentProperties;
import org.talend.daikon.NamedThing;

import java.io.IOException;
import java.util.List;

/**
 * This shouldn't be here, but is required for Forms using component properties.
 */
public interface ${componentClass}SourceOrSink extends SourceOrSink {
    List<NamedThing> getKeyspaceNames(RuntimeContainer container) throws IOException;

    List<NamedThing> getTableNames(RuntimeContainer container, String stringValue) throws IOException;

    Schema getSchema(RuntimeContainer container, String stringValue, String stringValue1) throws IOException;

    boolean doValidate(String name, ComponentProperties properties);
}
