package com.y4d.git.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler

class GitServer(private val workdir: String) {
    fun bind(port: Int) {
        val bossGroup = NioEventLoopGroup()
        val workGroup = NioEventLoopGroup()
        val bootstrap = ServerBootstrap()
        try {
            bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel::class.java)
                .option(ChannelOption.SO_BACKLOG, 100).handler(LoggingHandler(LogLevel.INFO))
                .childHandler(GitChannelInitializer(workdir))
            val sync = bootstrap.bind(port).sync()
            sync.channel().closeFuture().sync()
        } finally {
            bossGroup.shutdownGracefully()
            workGroup.shutdownGracefully()
        }

    }
}