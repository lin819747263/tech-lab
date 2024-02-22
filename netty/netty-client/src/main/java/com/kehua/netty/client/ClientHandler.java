package com.kehua.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;


@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<byte[]> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i = 0 ; i < 50; i++){
            ctx.channel().writeAndFlush(new byte[]{(byte)3, (byte)2, (byte)1 ,(byte)4});
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
        System.out.println(Arrays.toString(msg));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("传输出错", cause);
    }
}
