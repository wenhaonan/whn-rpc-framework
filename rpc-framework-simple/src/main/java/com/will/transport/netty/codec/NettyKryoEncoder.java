package com.will.transport.netty.codec;

import com.will.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义编码器，处理"出站"消息，将对象转换为字节数组写入byteBuf中
 *
 * @author haonan.wen
 * @createTime 2022/5/26 上午9:59
 */
@Slf4j
@AllArgsConstructor
public class NettyKryoEncoder extends MessageToByteEncoder<Object> {

    private Serializer serializer;
    private Class<?> genericClass;

    /**
     * 将对象转换为字节码然后写入bytebuf
     *
     * @param channelHandlerContext
     * @param o
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if (genericClass.isInstance(o)) {
            // 1. 将对象转换为byte
            byte[] body = serializer.serialize(o);
            // 2. 读取消息的长度
            int dataLength = body.length;
            // 3.写入消息对应的字节数组长度，writerIndex加4
            byteBuf.writeInt(dataLength);
            byteBuf.writeBytes(body);
        }
    }
}
