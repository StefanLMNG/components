package org.talend.components.fake;

import org.talend.components.api.AbstractComponentFamilyDefinition;
import org.talend.components.api.ComponentInstaller;
import org.talend.components.api.Constants;
import org.talend.components.api.component.runtime.DependenciesReader;
import org.talend.components.fake.tfakeinput.TFakeInputDefinition;
import org.talend.components.fake.tfakelog.TFakeLogDefinition;

import aQute.bnd.annotation.component.Component;

/**
 * Install all of the definitions provided for the processing family of components.
 */
@Component(name = Constants.COMPONENT_INSTALLER_PREFIX
		+ FakeFamilyDefinition.NAME, provide = ComponentInstaller.class)
public class FakeFamilyDefinition extends AbstractComponentFamilyDefinition implements ComponentInstaller {

	public static final String NAME = "Fake";

	public static final String MAVEN_GROUP_ID = "org.talend.components";

	public static final String MAVEN_ARTIFACT_ID = "components-fake";

	public FakeFamilyDefinition() {
		super(NAME,
				// Components
				new TFakeInputDefinition(), new TFakeLogDefinition()
		// Component wizards
		);
	}

    @Override
	public void install(ComponentFrameworkContext ctx) {
		ctx.registerComponentFamilyDefinition(this);
	}

	public static String computeDependenciesFilepath() {
		return DependenciesReader.computeDependenciesFilePath(FakeFamilyDefinition.MAVEN_GROUP_ID,
				FakeFamilyDefinition.MAVEN_ARTIFACT_ID);
	}
}
