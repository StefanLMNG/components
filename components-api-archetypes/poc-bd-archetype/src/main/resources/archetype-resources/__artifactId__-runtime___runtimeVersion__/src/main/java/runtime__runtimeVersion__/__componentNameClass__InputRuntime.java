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
package ${package}.runtime_${runtimeVersion};

import org.apache.avro.generic.IndexedRecord;
import org.apache.avro.mapred.AvroKey;
import org.apache.beam.sdk.coders.KvCoder;
import org.apache.beam.sdk.coders.VoidCoder;
import org.apache.beam.sdk.io.Read;
import org.apache.beam.sdk.io.hdfs.AvroHdfsFileSource;
import org.apache.beam.sdk.io.hdfs.CsvHdfsFileSource;
import org.apache.beam.sdk.io.hdfs.ParquetHdfsFileSource;
import org.apache.beam.sdk.io.hdfs.WritableCoder;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import ${packageTalend}.adapter.beam.coders.LazyAvroCoder;
import ${packageTalend}.adapter.beam.transform.ConvertToIndexedRecord;
import ${packageTalend}.api.component.runtime.RuntimableRuntime;
import ${packageTalend}.api.container.RuntimeContainer;
import ${packageTalend}.${componentNameLowerCase}.input.${componentNameClass}InputProperties;
import ${packageTalend}.${componentNameLowerCase}.runtime.coders.LazyAvroKeyWrapper;
import ${packageDaikon}.properties.ValidationResult;

public class ${componentNameClass}InputRuntime extends PTransform<PBegin, PCollection<IndexedRecord>> implements
        RuntimableRuntime<${componentNameClass}InputProperties> {

    /**
     * The component instance that this runtime is configured for.
     */
    private ${componentNameClass}InputProperties properties = null;

    @Override
    public ValidationResult initialize(RuntimeContainer container, ${componentNameClass}InputProperties properties) {
        this.properties = properties;
        return ValidationResult.OK;
    }

    @Override
    public PCollection<IndexedRecord> apply(PBegin in) {
        return null;
    }
}
