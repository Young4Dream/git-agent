package com.y4d.git.cli

import io.netty.bootstrap.Bootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel

class GitServer(private val cli: GitCliProto.GitCli) {

    fun boot() {
        val group = NioEventLoopGroup()
        val bootstrap = Bootstrap()
        try {
            bootstrap.group(group).channel(NioSocketChannel::class.java)
                .handler(GitChannelInitializer(cli))
//            发起异步连接操作
            val sync = bootstrap.connect(cli.host, cli.port).sync()
//            等待客户端链路关闭
            sync.channel().closeFuture().sync()
        } finally {
//            优雅退出
            group.shutdownGracefully()
        }
    }
}