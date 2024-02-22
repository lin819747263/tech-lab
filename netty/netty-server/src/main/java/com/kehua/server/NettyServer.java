package com.kehua.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;


public class NettyServer {

    private ServerBootstrap serverBootstrap;

    private EventLoopGroup boss = new NioEventLoopGroup();
    private EventLoopGroup worker = new NioEventLoopGroup();

    public NettyServer(){
        serverBootstrap = new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>(){
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
//                        ByteBuf delimiter = Unpooled.copiedBuffer("hello".getBytes());
//                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(20*1024, 0, 1));
//                        ch.pipeline().addLast(new LoggerHandler());
                        ch.pipeline().addLast(new ByteArrayDecoder());
                        ch.pipeline().addLast(new ByteArrayEncoder());
                        ch.pipeline().addLast(new NettyHandler());
                    }
                });
    }




    public void start() throws InterruptedException {
        try {
            ChannelFuture fr = serverBootstrap.bind(2568).sync().addListener(future -> {
                System.out.println("服务端启动成功");
            });

            fr.channel().closeFuture().sync();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
