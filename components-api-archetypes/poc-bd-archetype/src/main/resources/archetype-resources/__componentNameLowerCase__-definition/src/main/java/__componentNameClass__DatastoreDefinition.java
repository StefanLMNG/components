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

package ${package};

import java.net.MalformedURLException;
import java.net.URL;

import ${packageTalend}.api.component.runtime.DependenciesReader;
import ${packageTalend}.api.component.runtime.JarRuntimeInfo;
import ${packageTalend}.api.exception.ComponentException;
import ${packageTalend}.common.dataset.DatasetProperties;
import ${packageTalend}.common.datastore.DatastoreDefinition;
import ${packageTalend}.${componentNameLowerCase}.input.${componentName}InputDefinition;
import ${packageTalend}.${componentNameLowerCase}.output.${componentName}OutputDefinition;
import ${packageDaikon}.SimpleNamedThing;
import ${packageDaikon}.runtime.RuntimeInfo;


public class ${componentNameClass}DatastoreDefinition extends SimpleNamedThing implements DatastoreDefinition<${componentNameClass}DatastoreProperties> {

    public static final String RUNTIME_${runtimeVersion} = "org.talend.components.${componentNameLowerCase}.runtime_${runtimeVersion}.DatastoreRuntime";

    public static final String NAME = ${componentNameClass}ComponentFamilyDefinition.NAME + "Datastore";

    public ${componentNameClass}DatastoreDefinition() {
        super(NAME);
    }

    @Override
    public Class<${componentNameClass}DatastoreProperties> getPropertiesClass() {
        return ${componentNameClass}DatastoreProperties.class;
    }

    @Override
    public RuntimeInfo getRuntimeInfo(${componentNameClass}DatastoreProperties properties, Object ctx) {
        try {
            return new JarRuntimeInfo(new URL("mvn:org.talend.components/${componentNameLowerCase}-runtime"),
                    DependenciesReader.computeDependenciesFilePath("org.talend.components", "${componentNameLowerCase}-runtime"), RUNTIME_${runtimeVersion});
        } catch (MalformedURLException e) {
            throw new ComponentException(e);
        }
    }

    @Override
    public String getImagePath(){
        return null;
    }

    @Override
    public DatasetProperties createDatasetProperties(${componentNameClass}DatastoreProperties storeProp) {
        ${componentName}DatasetProperties setProp = new ${componentNameClass}DatasetProperties(${componentNameClass}DatasetDefinition.NAME);
        setProp.init();
        setProp.setDatastoreProperties(storeProp);
        return setProp;
    }

    @Override
    public String getInputCompDefinitionName() {
        return ${componentNameClass}InputDefinition.NAME;
    }

    @Override
    public String getOutputCompDefinitionName() {
        return ${componentNameClass}OutputDefinition.NAME;
    }

    @Override
    public String getDisplayName() {
        return getI18nMessage("datastore." + getName() + I18N_DISPLAY_NAME_SUFFIX);
    }
}
