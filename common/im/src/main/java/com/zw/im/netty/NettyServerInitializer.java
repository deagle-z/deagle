package com.zw.im.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 *
 * @author zw
 * @date 2019/12/13
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
//        获取channel
        ChannelPipeline pipeline = ch.pipeline();
//        将 channel绑定多个 handler， handler 会对 数据进行处理
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        pipeline.addLast("customer", new CustomerHandler());
    }
}
