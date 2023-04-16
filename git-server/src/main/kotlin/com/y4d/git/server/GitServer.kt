package com.y4d.git.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.exists
import kotlin.io.path.inputStream
import kotlin.system.exitProcess

class GitServer {
    fun bind() {
        val properties = Properties()
        val path = Paths.get(".install4j/response.varfile")
        val exists = path.exists()
        if (!exists) {
            System.err.println("cannot find varfile!")
            exitProcess(1)
        }
        val bossGroup = NioEventLoopGroup()
        val workGroup = NioEventLoopGroup()

        try {
            val inputStream = path.inputStream()
            properties.load(inputStream)
            for (p in properties) {
                println("${p.key}:${p.value}")
            }
            val port = properties.getProperty("port")
            val workdir = properties.getProperty("workdir")
            val gitHome = properties.getProperty("git_home")
            val bootstrap = ServerBootstrap()
            bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel::class.java)
                .option(ChannelOption.SO_BACKLOG, 100).handler(LoggingHandler(LogLevel.INFO))
                .childHandler(GitChannelInitializer(workdir, gitHome))
            val sync = bootstrap.bind(port.toInt()).sync()
            sync.channel().closeFuture().sync()
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            bossGroup.shutdownGracefully()
            workGroup.shutdownGracefully()
        }

    }
}