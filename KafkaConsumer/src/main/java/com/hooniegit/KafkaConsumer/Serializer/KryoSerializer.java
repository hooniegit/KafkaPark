package com.hooniegit.KafkaConsumer.Serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 데이터 객체를 byte[] 타입으로 직렬화합니다.<br>
 * - Xerializer (https://github.com/hooniegit/Xerializer) 패키지를 사용하고 있습니다.<br>
 * - Update Date : 25.02.11
 */

public class KryoSerializer {

    private static final ObjectPool<Kryo> kryoPool;

    static {
        GenericObjectPoolConfig<Kryo> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(10);
        kryoPool = new GenericObjectPool<>(new BasePooledObjectFactory<Kryo>() {
            @Override
            public Kryo create() {
                Kryo kryo = new Kryo();
                kryo.setClassLoader(Thread.currentThread().getContextClassLoader());
                return kryo;
            }
    
            @Override
            public PooledObject<Kryo> wrap(Kryo kryo) {
                return new DefaultPooledObject<>(kryo);
            }
        }, config);
    }

    public static <T> byte[] serialize(T object) throws Exception {
        Kryo kryo = null;
        try {
            kryo = kryoPool.borrowObject();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Output output = new Output(byteArrayOutputStream);

            kryo.writeClassAndObject(output, object);

            output.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Serialization failed", e);
        } finally {
            if (kryo != null) {
                kryoPool.returnObject(kryo);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] bytes) throws Exception {
        Kryo kryo = null;
        try {
            kryo = kryoPool.borrowObject();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            Input input = new Input(byteArrayInputStream);

            return (T) kryo.readClassAndObject(input);
        } catch (Exception e) {
            throw new RuntimeException("Deserialization failed", e);
        } finally {
            if (kryo != null) {
                kryoPool.returnObject(kryo);
            }
        }
    }


}