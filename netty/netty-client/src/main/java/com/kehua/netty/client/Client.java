package com.kehua.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

public class Client {

    private Bootstrap bootstrap;

    private Channel channel;

    public Client(SocketAddress address){
        bootstrap = new Bootstrap()
                .channel(NioSocketChannel.class)
                .remoteAddress(address)
                .group(new NioEventLoopGroup())
                .handler(new ChannelInitializer<SocketChannel>(){
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(20*1024, 0, 1));
                        ch.pipeline().addLast(new ByteArrayDecoder());
                        ch.pipeline().addLast(new ByteArrayEncoder());
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
    }

    public void start(){
        try {
            ChannelFuture future = bootstrap.connect().addListener(future1 -> {
                if(future1.isSuccess()){
                    System.out.println("客户端连接成功");
                }else {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("客户端连接重新连接中。。。。。");
                    start();
                }
            });

            channel = future.channel();
            future.channel().closeFuture();
        }catch (Exception e){

        }finally {

        }
    }
}
