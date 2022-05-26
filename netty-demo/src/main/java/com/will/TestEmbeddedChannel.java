package com.will;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author haonan.wen
 * @createTime 2022/5/22 下午3:19
 */
@Slf4j
public class TestEmbeddedChannel {
    public static void main(String[] args) {
        ChannelInboundHandlerAdapter h1 = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                log.debug("h1");
                ByteBuf byteBuf = (ByteBuf) msg;
                String name = byteBuf.toString(Charset.defaultCharset());
                // 将数据传递给下一个handler
                // ctx.fireChannelRead(msg);
                super.channelRead(ctx, name);
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(h1);
    }
}
