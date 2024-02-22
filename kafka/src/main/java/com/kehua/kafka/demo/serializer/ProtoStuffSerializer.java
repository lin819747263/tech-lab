package com.kehua.kafka.demo.serializer;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Serializer;

/**
 * @author B-Magician on 2022/9/7 10:55
 */
public class ProtoStuffSerializer<T> implements Serializer<T> {

    private final Schema<T> schema;

    public ProtoStuffSerializer() {
        schema = null;
    }

    public ProtoStuffSerializer(Class<T> target) {
        schema = RuntimeSchema.getSchema(target);
    }

    @Override
    @SuppressWarnings("unchecked")
    public byte[] serialize(String topic, T data) {
        byte[] byteData;
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.MIN_BUFFER_SIZE);
        try {
            Schema<T> tSchema;
            if (schema == null) {
                tSchema = RuntimeSchema.getSchema((Class<T>) data.getClass());
            } else {
                tSchema = schema;
            }
            byteData = ProtostuffIOUtil.toByteArray(data, tSchema, buffer);
        } finally {
            buffer.clear();
        }
        return byteData;
    }
}
