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
package org.talend.components.processing.definition.replicate;

public enum StorageLevel {
    MEMORY_ONLY,
    MEMORY_AND_DISK,
    DISK_ONLY,
    MEMORY_ONLY_SER,
    MEMORY_AND_DISK_SER,
    MEMORY_ONLY_2,
    MEMORY_AND_DISK_2,
    DISK_ONLY_2,
    MEMORY_ONLY_SER_2,
    MEMORY_AND_DISK_SER_2,
    OFF_HEAP
}
