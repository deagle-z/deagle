package com.zw.im.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author zw
 * @date 2019/12/13
 */
public class NettyServer {
    public static void main(String[] args) {
//        定义两个线程池
//        主线程池 接受客户端的连接 不做任何操作
        NioEventLoopGroup mainExecutors = new NioEventLoopGroup();
//        从线程组 主线程组会把任务转给 次要线程
        NioEventLoopGroup workEventors = new NioEventLoopGroup();
        try {
//            服务启动类 自动分配任务 mainExcutors 分配给 workExcutors channel 接受 进过handler
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(mainExecutors, workEventors)
//                    设置NIO双向渠道
                    .channel(NioServerSocketChannel.class)
//                    设置子处理器 每一个channel有多个handler共同组成管道 NettyServerInitalizer 继承自 ChannelInboundHandlerAdapter
                    .childHandler(new NettyServerInitializer());
//            启动
            ChannelFuture sync = serverBootstrap.bind(8888).sync();
//            关闭
//            获取某个客户端对应的channel，并设置异步方式
            sync.channel().closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            mainExecutors.shutdownGracefully();
            workEventors.shutdownGracefully();
        }
    }
}
