// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.components.base.properties.presentation;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.talend.components.base.ComponentProperties;
import org.talend.components.base.properties.NamedThing;
import org.talend.components.base.properties.Property;

import javax.validation.constraints.Null;

/**
 * Represents a collection of components {@link Property} objects that are grouped into
 * a form for display. This form can be manifested for example as a tab in a view, a dialog, or a page in a wizard.
 */
public class Form extends NamedThing {

    protected List<NamedThing> children;

    protected Map<NamedThing, Layout> layoutMap;

    public Form(ComponentProperties props, String name, String displayName) {
        super(name, displayName);
        children = new ArrayList();
        layoutMap = new HashMap<NamedThing, Layout>();
        props.addForm(this);
    }

    public static Form create(ComponentProperties props, String name, String displayName) {
        return new Form(props, name, displayName);
    }

    public Map<NamedThing, Layout> getLayoutMap() {
        return layoutMap;
    }

    public void setLayoutMap(Map<NamedThing, Layout> layoutMap) {
        this.layoutMap = layoutMap;
    }

    public Form addChild(NamedThing child, Layout layout) {
        if (child == null)
            throw new NullPointerException();
        layoutMap.put(child, layout);
        children.add(child);
        return this;
    }

}
