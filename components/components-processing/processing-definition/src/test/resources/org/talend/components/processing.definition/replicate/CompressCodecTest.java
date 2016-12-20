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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;

import org.junit.Test;
import org.mockito.Mockito;
import org.talend.components.api.ComponentFamilyDefinition;
import org.talend.components.api.ComponentInstaller;
import org.talend.components.processing.definition.replicate.CompressCodec;

public class CompressCodecTest {

    private final String ValueSnappy = CompressCodec.SNAPPY.getValue();

    private final String ValueLZ4 = CompressCodec.LZ4.getValue();

    private final String ValueLZF = CompressCodec.LZF.getValue();

    @Test
    public void test(){
        assertTrue(ValueLZ4.equals("org.apache.spark.io.LZ4CompressionCodec"));
        assertTrue(ValueLZF.equals("org.apache.spark.io.LZFCompressionCodec"));
        assertTrue(ValueSnappy.equals("org.apache.spark.io.SnappyCompressionCodec"));
    }




}
