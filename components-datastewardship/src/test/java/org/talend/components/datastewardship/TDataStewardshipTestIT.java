// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.components.datastewardship;

import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.talend.components.api.service.ComponentService;
import org.talend.components.api.test.ComponentTestUtils;
import org.talend.components.api.test.SpringTestApp;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringTestApp.class)
public class TDataStewardshipTestIT {

    @Inject
    private ComponentService componentService;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Test
    // this is an integration test to check that the dependencies file is properly generated.
    public void testDependencies() {
        ComponentTestUtils.testAllDesignDependenciesPresent(componentService, errorCollector);
    }

    @Test
    public void testAlli18n() {
        ComponentTestUtils.testAlli18n(componentService, errorCollector);
    }

    @Test
    public void testAllImagePath() {
        ComponentTestUtils.testAllImages(componentService);
    }

    @Test
    public void testAllRuntimes() {
        ComponentTestUtils.testAllRuntimeAvaialble(componentService);
    }

}
