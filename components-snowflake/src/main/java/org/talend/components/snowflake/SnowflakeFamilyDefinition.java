package org.talend.components.snowflake;

import aQute.bnd.annotation.component.Component;
import org.talend.components.api.AbstractComponentFamilyDefinition;
import org.talend.components.api.ComponentInstaller;
import org.talend.components.api.Constants;
import org.talend.components.snowflake.tsnowflakeconnection.TSnowflakeConnectionDefinition;
import org.talend.components.snowflake.tsnowflakeinput.TSnowflakeInputDefinition;
import org.talend.components.snowflake.tsnowflakeoutput.TSnowflakeOutputDefinition;

/**
 * Install all of the definitions provided for the Snowflake family of components.
 */
@Component(name = Constants.COMPONENT_INSTALLER_PREFIX + SnowflakeFamilyDefinition.NAME, provide = ComponentInstaller.class)
public class SnowflakeFamilyDefinition extends AbstractComponentFamilyDefinition implements ComponentInstaller {

    public static final String NAME = "Snowflake";

    public SnowflakeFamilyDefinition() {
        super(NAME,
                // Components
                new TSnowflakeConnectionDefinition(), new TSnowflakeInputDefinition(), new TSnowflakeOutputDefinition(),
                // Component wizards
                new SnowflakeConnectionWizardDefinition(), new SnowflakeConnectionEditWizardDefinition(), new SnowflakeTableWizardDefinition());
    }

    @Override
    public void install(ComponentFrameworkContext ctx) {
        ctx.registerComponentFamilyDefinition(this);
    }

}
