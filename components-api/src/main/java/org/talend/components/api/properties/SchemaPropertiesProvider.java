package org.talend.components.api.properties;

public interface SchemaPropertiesProvider<T> {
    void setSchemaProperties(T props);
    T getSchemaProperties();
}
