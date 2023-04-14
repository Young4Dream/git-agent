package com.y4d.git.cli

import com.y4d.git.server.GitServerProto
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

class GitChannelHandler(private val cli: GitCliProto.GitCli) : SimpleChannelInboundHandler<GitServerProto.GitServer>() {

    override fun channelActive(ctx: ChannelHandlerContext?) {
        ctx?.writeAndFlush(cli)
    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: GitServerProto.GitServer?) {
        println("success: ${msg?.success}")
        println("message: ${msg?.response}")
        // 一次接收
        ctx?.close()
    }
}