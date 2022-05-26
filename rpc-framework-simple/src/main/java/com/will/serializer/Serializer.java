package com.will.serializer;

/**
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
