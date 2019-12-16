package com.zw.im.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 自定义 channel handler
 *
 * @author zw
 * @date 2019/12/13
 */
public class CustomerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("customer channel注册");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("customer channel注销");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("customer channel活跃");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("customer channel 断开连接");
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 读取数据完毕");
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("customer channel 用户事件触发");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel可写事件更改");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("捕获channel异常");
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler移除");
        super.handlerRemoved(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
        Channel currentChannel = ctx.channel();
        if (msg instanceof HttpRequest) {
//            当前访问客户端的远程地址
            System.out.println(currentChannel.remoteAddress());
            ByteBuf helloNetty = Unpooled.copiedBuffer("Hello Netty", CharsetUtil.UTF_8);
//            构建一个返回的response
            DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, helloNetty);
            defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, helloNetty.readableBytes());
            ctx.writeAndFlush(defaultFullHttpResponse);
        }
    }
}
