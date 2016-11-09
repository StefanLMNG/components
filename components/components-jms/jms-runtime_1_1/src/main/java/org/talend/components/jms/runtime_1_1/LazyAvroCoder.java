package org.talend.components.jms.runtime_1_1;


import org.apache.avro.Schema;

import org.apache.avro.generic.IndexedRecord;

import org.apache.beam.sdk.coders.Coder;
import org.apache.beam.sdk.coders.CoderException;
import org.apache.beam.sdk.coders.CoderProvider;
import org.apache.beam.sdk.coders.StandardCoder;

import org.apache.beam.sdk.values.TypeDescriptor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

public class LazyAvroCoder<T extends IndexedRecord> extends StandardCoder<T> {
    /*public static final CoderProvider PROVIDER = new CoderProvider() {
        public <T> Coder<T> getCoder(TypeDescriptor<T> typeDescriptor) {
            Class rawType = typeDescriptor.getRawType();
            return LazyAvroCoder.of("testeee");
        }
    };*/

    private final String id;
    private final transient Schema schema;

    public static <T extends IndexedRecord> LazyAvroCoder of(String id) {
        return new LazyAvroCoder(id);
    }

    protected LazyAvroCoder(String id) {
        this.id = id;
        this.schema = null;
        System.out.println("terefer");
    }

    @Override public void encode(T value, OutputStream outputStream, Context context) throws CoderException, IOException {
        System.out.println("testAHAHAHA");
        //value.getSchema()
    }

    @Override public T decode(InputStream inputStream, Context context) throws CoderException, IOException {
        System.out.println("ppkpêrohnvezhui");
        return null;
    }

    @Override public List<? extends Coder<?>> getCoderArguments() {
        return null;
    }

    @Override public void verifyDeterministic() throws NonDeterministicException {

    }
    public Schema getSchema() {
        return this.schema;
    }

}
