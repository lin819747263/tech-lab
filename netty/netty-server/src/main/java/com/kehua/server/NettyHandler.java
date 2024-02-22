package com.kehua.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class NettyHandler extends SimpleChannelInboundHandler<byte[]>{

    Map<String, Channel> map = new HashMap<>();


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("新的连接已注册");
    }



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        map.put(ctx.channel().id().asLongText(), ctx.channel());
        log.info("新的连接已连接");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
        System.out.println(Arrays.toString(msg));
        ctx.channel().writeAndFlush(new byte[]{(byte)3, (byte)6, (byte)6 ,(byte)6});
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("传输出错:", cause);
    }
}
