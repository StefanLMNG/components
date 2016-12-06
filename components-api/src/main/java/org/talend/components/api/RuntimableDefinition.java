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
package org.talend.components.api;

import org.talend.daikon.definition.Definition;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.runtime.RuntimeInfo;

/**
 * A base class for definitions of business objects provided by the component framework.
 *
 * An instance of {@link RuntimableDefinition} serves as a factory for {@link Properties}, and subsequently a
 * {@link RuntimeInfo} created from properties compatible with a definition. Any number of definitions can be provided
 * by a component family, and are grouped by sub-interfaces.
 *
 * For example:
 *
 * <ul>
 * <li>The {@link org.talend.components.api.component.ComponentDefinition} is a subinterface used to group all
 * components in the framework.</li>
 * <li>A component family provides instances of implementations of
 * {@link org.talend.components.api.component.ComponentDefinition}, where each instance is a type of component that can
 * be created.</li>
 * <li>Each instance of {@link org.talend.components.api.component.ComponentDefinition} can be used to create a
 * {@link org.talend.components.api.properties.ComponentProperties}, which describes how to configure that
 * component.</li>
 * <li>Each configured instance of {@link org.talend.components.api.properties.ComponentProperties} can be used to
 * create a {@link org.talend.components.api.properties.ComponentProperties} represents a configured component in a
 * job.</li>
 * <li>Finally, {@link org.talend.components.api.component.ComponentDefinition} can turn a configured instance of
 * {@link org.talend.components.api.properties.ComponentProperties} into a {@link RuntimeInfo} that performs the
 * processing tasks for the component.</li>
 * </ul>
 *
 * @param <PropT> The type of properties that the specific definition works with.
 * @param <RuntimeInfoContextT> Creating a runtime object for this class may require additional information than just
 *            the properties.
 */
public interface RuntimableDefinition<P extends Properties, RuntimeInfoContextT> extends Definition<P> {

    /**
     * @param properties an instance of the definition.
     * @param ctx a helper context containing additional information outside of the instance, if any.
     * @return an object that can be used to create a runtime instance of this definition, configured by the properties
     *         of the instance and the context. This can be null if no runtime applies.
     */
    RuntimeInfo getRuntimeInfo(P properties, RuntimeInfoContextT ctx);
}
