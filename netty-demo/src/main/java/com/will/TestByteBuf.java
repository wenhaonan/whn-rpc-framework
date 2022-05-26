package com.will;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author haonan.wen
 * @createTime 2022/5/22 下午3:25
 */
public class TestByteBuf {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        System.out.println(buffer.getClass());
        System.out.println(buffer);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 300; i++) {
            sb.append("a");
        }
        buffer.writeBytes(sb.toString().getBytes());
        System.out.println(buffer);

    }
}
