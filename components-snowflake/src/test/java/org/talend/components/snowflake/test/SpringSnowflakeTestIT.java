package org.talend.components.snowflake.test;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.talend.components.api.service.ComponentService;
import org.talend.components.service.spring.SpringTestApp;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringTestApp.class)
public class SpringSnowflakeTestIT extends SnowflakeTestIT {

    @Inject
    ComponentService componentService;

    @Override
    public ComponentService getComponentService() {
        return componentService;
    }

}
