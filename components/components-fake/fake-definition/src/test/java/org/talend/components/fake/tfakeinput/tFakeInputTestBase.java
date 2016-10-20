package org.talend.components.fake.tfakeinput;

import javax.inject.Inject;

import org.junit.Test;
import org.talend.components.api.service.ComponentService;
import org.talend.components.fake.common.AbstractComponentTest;

public class tFakeInputTestBase extends AbstractComponentTest {
    @Inject
    private ComponentService componentService;

    public ComponentService getComponentService(){
        return componentService;
    }
    
    @Test
    public void componentHasBeenRegistered(){
        checkComponentIsRegistered("tFakeInput");
    }
}
