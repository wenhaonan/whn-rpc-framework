package com.will.serializer;

/**
 * 序列化接口，所有序列化类都要实现这个接口
 *
 * @author haonan.wen
 * @createTime 2022/5/25 下午7:37
 */
public interface Serializer {

    /**
     * 序列化
     * @param obj
     * @return
     */
    byte[] serialize(Object obj);

    /**
     * 反序列化
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
