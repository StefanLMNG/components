package org.talend.components.api.properties;

public interface ConnectionPropertiesProvider<T> {
    void setConnectionProperties(T props);
    T getConnectionProperties();
}
