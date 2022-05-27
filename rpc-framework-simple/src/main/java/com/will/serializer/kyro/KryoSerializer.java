package com.will.serializer.kyro;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.will.dto.RpcRequest;
import com.will.dto.RpcResponse;
import com.will.exception.SerializeException;
import com.will.serializer.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * kryo序列化类，效率很高，但是只兼容java
 * @author haonan.wen
 * @createTime 2022/5/25 下午7:39
 */
@Slf4j
public class KryoSerializer implements Serializer {


    private static final ThreadLocal<Kryo> KRYO_THREAD_LOCAL =
            ThreadLocal.withInitial(() -> {
                Kryo kryo = new Kryo();
                kryo.register(RpcResponse.class);
                kryo.register(RpcRequest.class);
                // 默认值为true,是否关闭注册行为,关闭之后可能存在序列化问题，一般推荐设置为 true
                kryo.setReferences(true);
                // 默认值为false,是否关闭循环引用，可以提高性能，但是一般不推荐设置为 true
                kryo.setRegistrationRequired(false);
                return kryo;
            });

    @Override
    public byte[] serialize(Object obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Output output = new Output(byteArrayOutputStream)) {
            Kryo kryo = KRYO_THREAD_LOCAL.get();
            kryo.writeObject(output, obj);
            KRYO_THREAD_LOCAL.remove();
            return output.toBytes();
        } catch (IOException e) {
            log.error("occur exception when serialize:", e);
            throw new SerializeException("序列化失败");
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try (ByteArrayInputStream byteArrayOutputStream = new ByteArrayInputStream(bytes);
             Input input = new Input(byteArrayOutputStream)) {
            Kryo kryo = KRYO_THREAD_LOCAL.get();
            Object o = kryo.readObject(input, clazz);
            KRYO_THREAD_LOCAL.remove();
            return clazz.cast(o);
        } catch (Exception e) {
            log.error("occur exception when deserialize:", e);
            throw new SerializeException("反序列化失败");
        }
    }
}
