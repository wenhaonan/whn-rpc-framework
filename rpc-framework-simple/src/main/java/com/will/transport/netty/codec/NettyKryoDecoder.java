package com.will.transport.netty.codec;

import com.will.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 自定义解码器，负责处理"入站"消息，将消息格式转换为我们需要的业务对象
 *
 * @author haonan.wen
 * @createTime 2022/5/26 上午9:59
 */
@AllArgsConstructor
@Slf4j
public class NettyKryoDecoder extends ByteToMessageDecoder {

    private Serializer serializer;
    private Class<?> genericClass;

    /**
     * netty传输的消息长度也就是对象序列化之后对应的字节数组的大小，存在ByteBuff头部
     */
    private static final int BODY_LENGTH = 4;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 1. byteBuf中消息长度所占4字节，所以必须大于4
        if (byteBuf.readableBytes() >= BODY_LENGTH) {
            // 2. 标记当前位置，以便重置用
            byteBuf.markReaderIndex();
            // 3. 读取消息的长度
            // 注意，消息长度是encode我们自己写入的
            int dataLength = byteBuf.readInt();
            // 4. 遇到不合理直接return
            if (dataLength < 0 || byteBuf.readableBytes() < 0) {
                return;
            }
            // 5. 如果可读字节数小于消息体长度，说明消息不完整，重置readIndex
            if (byteBuf.readableBytes() < dataLength) {
                byteBuf.resetReaderIndex();
                return;
            }
            // 6. 走到这里说明没什么问题了，可以序列化了
            byte[] body = new byte[dataLength];
            byteBuf.readBytes(body);
            Object obj = serializer.deserialize(body, genericClass);
            list.add(obj);
        }
    }
}
