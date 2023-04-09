package com.y4d.git.cli

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

class GitCliHandler : ChannelInboundHandlerAdapter() {
    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }

    override fun channelActive(ctx: ChannelHandlerContext?) {
        super.channelActive(ctx)
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val bb = msg as ByteBuf
        val buffer = Unpooled.copiedBuffer(bb)
        ctx.writeAndFlush(buffer)
        // 一次接收
        ctx.close()
    }
}