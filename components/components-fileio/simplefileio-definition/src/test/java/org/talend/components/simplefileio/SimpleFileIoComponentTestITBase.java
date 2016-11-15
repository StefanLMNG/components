package org.talend.components.simplefileio;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.talend.components.api.service.ComponentService;
import org.talend.components.api.test.AbstractComponentTest;
import org.talend.components.simplefileio.input.SimpleFileIoInputDefinition;
import org.talend.components.simplefileio.output.SimpleFileIoOutputDefinition;

public abstract class SimpleFileIoComponentTestITBase extends AbstractComponentTest {

    @Inject
    ComponentService componentService;

    @Override
    public ComponentService getComponentService() {
        return componentService;
    }

    @Test
    public void assertComponentsAreRegistered() {
        assertNotNull(getComponentService().getComponentDefinition(SimpleFileIoInputDefinition.NAME));
        assertNotNull(getComponentService().getComponentDefinition(SimpleFileIoOutputDefinition.NAME));
    }
}
