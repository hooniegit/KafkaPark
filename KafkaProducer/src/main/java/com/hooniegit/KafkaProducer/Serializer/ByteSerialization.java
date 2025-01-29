package com.hooniegit.KafkaProducer.Serializer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.Serializable;

/**
 * Data Serialization Class
 *
 * <Usage>
 * - Serialize T To Byte[]
 * - De-Serialize Byte[] To T
 */
public class ByteSerialization extends KryoSerialization {

    /**
     * Serialize T To Byte[]
     * @param <T>
     * @param object
     * @return
     * @throws Exception
     */
    public <T extends Serializable> byte[] serializeToBytes(T object) throws Exception {
        ByteBuf buffer = serialize(object);
        try {
            int length = buffer.readableBytes();
            byte[] byteArray = new byte[length];
            buffer.readBytes(byteArray);
            return byteArray;
        } finally {
            buffer.release();
        }
    }

    /**
     * De-Serialize byte[] To T
     * @param <T>
     * @param byteArray
     * @return
     * @throws Exception
     */
    public <T> T deserializeFromBytes(byte[] byteArray) throws Exception {
        ByteBuf buffer = Unpooled.wrappedBuffer(byteArray);
        return deserialize(buffer);
    }
}
