package com.kehua.kafka.demo.serializer;

import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Deserializer;

/**
 * @author B-Magician on 2022/9/7 10:59
 */
public class ProtoStuffDeserializer<T> implements Deserializer<T> {

    private final Schema<T> schema;

    public ProtoStuffDeserializer(Class<T> target) {
        this.schema = RuntimeSchema.getSchema(target);
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        T t = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(data, t, schema);
        return t;
    }

}
