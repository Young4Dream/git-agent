package com.y4d.git.cli

import io.netty.bootstrap.Bootstrap
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.protobuf.ProtobufDecoder
import io.netty.handler.codec.protobuf.ProtobufEncoder
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender

class Client : ChannelInitializer<NioSocketChannel>() {

    fun boot(cli: GitCli) {
        val group = NioEventLoopGroup()
        val bootstrap = Bootstrap()
        bootstrap.group(group).channel(NioSocketChannel().javaClass)
            .handler(cli)
    }

    fun start() {
        val boss = NioEventLoopGroup()
        val worker = NioEventLoopGroup()
        val server = ServerBootstrap();
        server.group(boss, worker).childHandler(this)
    }

    override fun initChannel(ch: NioSocketChannel) {
        ch.pipeline()
            .addLast(ProtobufVarint32FrameDecoder())
            .addLast(ProtobufDecoder(GitCliProto.GitCli.getDefaultInstance()))
            .addLast(ProtobufVarint32LengthFieldPrepender())
            .addLast(ProtobufEncoder())
            .addLast(GitCliHandler())
    }
}