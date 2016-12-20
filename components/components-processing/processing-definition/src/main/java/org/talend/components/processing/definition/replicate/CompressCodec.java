package org.talend.components.processing.definition.replicate;

public enum CompressCodec {
    SNAPPY ("SNAPPY", "org.apache.spark.io.SnappyCompressionCodec"),
    LZ4 ("LZ4", "org.apache.spark.io.LZ4CompressionCodec"),
    LZF ("LZF", "org.apache.spark.io.LZFCompressionCodec");

    private final String name;

    private final String value;

    private CompressCodec(String s, String v) {
        name = s;
        value = v;
    }

    public String toString() {
        return this.name;
    }

    public String getValue(){
        return this.value;
    }
}
