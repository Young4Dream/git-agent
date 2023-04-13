package com.y4d.git.server

import com.y4d.git.cli.GitCliProto
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.protobuf.ProtobufDecoder
import io.netty.handler.codec.protobuf.ProtobufEncoder
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender

class GitChannelInitializer(private val workspace: String) :
    ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch: SocketChannel) {
        val pipeline = ch.pipeline()
        pipeline.addLast(ProtobufVarint32FrameDecoder())
            .addLast(ProtobufDecoder(GitCliProto.GitCli.getDefaultInstance()))
            .addLast(ProtobufVarint32LengthFieldPrepender())
            .addLast(ProtobufEncoder())
            .addLast(GitChannelHandler(workspace))
    }
}