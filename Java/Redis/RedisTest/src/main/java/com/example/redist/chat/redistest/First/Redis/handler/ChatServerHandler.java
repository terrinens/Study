package com.example.redist.chat.redistest.First.Redis.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ChatServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String message = (String) msg;
        log.info("서버 채널 : {}", message);
        ctx.channel().parent().writeAndFlush(
                "[" + ctx.channel().remoteAddress() + "]: " + message + "\n"
        );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
       log.error(cause.getMessage(), cause);
    }
}
