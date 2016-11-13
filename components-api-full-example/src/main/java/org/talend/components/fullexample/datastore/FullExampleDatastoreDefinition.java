
package org.talend.components.fullexample.datastore;

import org.talend.components.common.dataset.DatasetProperties;
import org.talend.components.common.datastore.DatastoreDefinition;
import org.talend.components.fullexample.FullExampleInputDefinition;
import org.talend.daikon.SimpleNamedThing;
import org.talend.daikon.runtime.RuntimeInfo;

/**
 * An example of a DatastoreDefinition.
 */
public class FullExampleDatastoreDefinition extends SimpleNamedThing
        implements DatastoreDefinition<FullExampleDatastoreProperties> {

    private static final String NAME = "FullExampleDatastore";

    public FullExampleDatastoreDefinition() {
        super(NAME);
    }

    @Override
    public Class<FullExampleDatastoreProperties> getPropertiesClass() {
        return FullExampleDatastoreProperties.class;
    }

    @Override
    public RuntimeInfo getRuntimeInfo(FullExampleDatastoreProperties properties, Object ctx) {
        return null;
    }

    @Override
    public DatasetProperties createDatasetProperties(FullExampleDatastoreProperties storeProp) {
        return null;
    }

    @Override
    public String getInputCompDefinitionName() {
        return FullExampleInputDefinition.COMPONENT_NAME;
    }

    @Override
    public String getOutputCompDefinitionName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Full example datastore";
    }

    @Override
    public String getTitle() {
        return "Full example datastore";
    }

    @Override
    public String getImagePath() {
        return "/org/talend/components/fullexample/fullExample_icon32.png";
    }
}
