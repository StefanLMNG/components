package org.talend.components.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.avro.Schema;

public class SchemaUtil {

    public static String REJECT_FIELD_INPUT = "input";
    public static String REJECT_FIELD_ERROR_MESSAGE = "errorMessage";

    public static Schema createRejectSchema(Schema originalSchema, String rejectSchemaName) {
        Schema newSchema = Schema.createRecord(rejectSchemaName, originalSchema.getDoc(), originalSchema.getNamespace(),
                originalSchema.isError());
        Schema.Field inputField = new Schema.Field(REJECT_FIELD_INPUT, originalSchema, null, (Object) null);
        Schema.Field errorMessageField = new Schema.Field(REJECT_FIELD_ERROR_MESSAGE, Schema.create(Schema.Type.STRING),
                null, (Object) null);
        newSchema.setFields(Arrays.asList(inputField, errorMessageField));

        for (Map.Entry<String, Object> entry : originalSchema.getObjectProps().entrySet()) {
            newSchema.addProp(entry.getKey(), entry.getValue());
        }

        return newSchema;
    }

    public static List<String> getFieldNames(Schema schema) {
        List<String> fieldNames = new ArrayList<>();
        for (Schema.Field f : schema.getFields()) {
            fieldNames.add(f.name());
        }
        return fieldNames;
    }
}
