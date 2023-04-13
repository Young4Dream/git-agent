package com.y4d.git.cli

import com.y4d.git.server.GitServerProto
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

class GitChannelHandler(private val cli: GitCliProto.GitCli) : ChannelInboundHandlerAdapter() {
    @Deprecated("Deprecated in Java")
    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }

    override fun channelActive(ctx: ChannelHandlerContext?) {
        ctx?.writeAndFlush(cli)
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val bb = msg as GitServerProto.GitServer
        println("success: ${bb.success}")
        println("message: ${bb.response}")
        // 一次接收
        ctx.close()
    }
}