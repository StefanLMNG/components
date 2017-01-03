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
package org.talend.components.simplefileio.runtime;

import org.apache.avro.generic.IndexedRecord;
import org.apache.avro.mapred.AvroKey;
import org.apache.beam.sdk.coders.KvCoder;
import org.apache.beam.sdk.coders.VoidCoder;
import org.apache.beam.sdk.io.Read;
import org.apache.beam.sdk.io.hdfs.TalendAvroHdfsFileSource;
import org.apache.beam.sdk.io.hdfs.CsvHdfsFileSource;
import org.apache.beam.sdk.io.hdfs.ParquetHdfsFileSource;
import org.apache.beam.sdk.io.hdfs.WritableCoder;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.Keys;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.Values;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.talend.components.adapter.beam.coders.LazyAvroCoder;
import org.talend.components.adapter.beam.transform.ConvertToIndexedRecord;
import org.talend.components.api.component.runtime.RuntimableRuntime;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.simplefileio.input.SimpleFileIoInputProperties;
import org.talend.components.simplefileio.runtime.coders.LazyAvroKeyWrapper;
import org.talend.daikon.properties.ValidationResult;

public class SimpleFileIoInputRuntime extends PTransform<PBegin, PCollection<IndexedRecord>> implements
        RuntimableRuntime<SimpleFileIoInputProperties> {

    /**
     * The component instance that this runtime is configured for.
     */
    private SimpleFileIoInputProperties properties = null;

    @Override
    public ValidationResult initialize(RuntimeContainer container, SimpleFileIoInputProperties properties) {
        this.properties = properties;
        return ValidationResult.OK;
    }

    @Override
    public PCollection<IndexedRecord> expand(PBegin in) {
        switch (properties.getDatasetProperties().format.getValue()) {

        case AVRO: {
            // Reuseable coder.
            LazyAvroCoder<Object> lac = LazyAvroCoder.of();

            TalendAvroHdfsFileSource source = TalendAvroHdfsFileSource.from(properties.getDatasetProperties().path.getValue(),
                    KvCoder.of(LazyAvroKeyWrapper.of(lac), WritableCoder.of(NullWritable.class))); //
            PCollection<KV<AvroKey, NullWritable>> read = in.apply(Read.from(source)) //
                    .setCoder(source.getDefaultOutputCoder());

            PCollection<AvroKey> pc1 = read.apply(Keys.<AvroKey> create());

            PCollection<Object> pc2 = pc1.apply(ParDo.of(new ExtractRecordFromAvroKey()));
            pc2 = pc2.setCoder(lac);

            PCollection<IndexedRecord> pc3 = pc2.apply(ConvertToIndexedRecord.<Object, IndexedRecord> of());

            return pc3;
        }

        case CSV: {
            CsvHdfsFileSource source = CsvHdfsFileSource.from(properties.getDatasetProperties().path.getValue(),
                    properties.getDatasetProperties().recordDelimiter.getValue());

            PCollection<KV<org.apache.hadoop.io.LongWritable, Text>> pc1 = in.apply(Read.from(source));

            PCollection<Text> pc2 = pc1.apply(Values.<Text> create());

            PCollection<String[]> pc3 = pc2.apply(ParDo.of(new ExtractCsvSplit(
                    properties.datasetRef.getReference().fieldDelimiter.getValue())));

            PCollection pc4 = pc3.apply(ConvertToIndexedRecord.<String[], IndexedRecord> of());

            return pc4;
        }

        case PARQUET: {
            LazyAvroCoder<IndexedRecord> lac = LazyAvroCoder.of();

            ParquetHdfsFileSource source = ParquetHdfsFileSource.from(properties.getDatasetProperties().path.getValue(),
                    (KvCoder) KvCoder.of(VoidCoder.of(), lac));

            PCollection<KV<Void, IndexedRecord>> read = in.apply(Read.from(source)) //
                    .setCoder(source.getDefaultOutputCoder());

            PCollection<IndexedRecord> pc1 = read.apply(Values.<IndexedRecord> create());

            return pc1;
        }

        default:
            throw new RuntimeException("To be implemented: " + properties.getDatasetProperties().format.getValue());
        }
    }

    public static class ExtractCsvSplit extends DoFn<Text, String[]> {

        public final String fieldDelimiter;

        ExtractCsvSplit(String fieldDelimiter) {
            this.fieldDelimiter = fieldDelimiter;
        }

        @DoFn.ProcessElement
        public void processElement(ProcessContext c) {
            String in = c.element().toString();
            c.output(in.split("\\Q" + fieldDelimiter + "\\E"));
        }
    }

    public static class ExtractRecordFromAvroKey extends DoFn<AvroKey, Object> {

        @DoFn.ProcessElement
        public void processElement(ProcessContext c) {
            AvroKey in = c.element();
            c.output(in.datum());
        }
    }

}
