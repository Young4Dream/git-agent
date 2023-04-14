package com.y4d.git.cli

import com.y4d.git.server.GitServerProto
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.protobuf.ProtobufDecoder
import io.netty.handler.codec.protobuf.ProtobufEncoder
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender


class GitChannelInitializer(private val cli: GitCliProto.GitCli): ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch: SocketChannel) {
        val p = ch.pipeline()
        p.addLast(ProtobufVarint32FrameDecoder())
        p.addLast(ProtobufDecoder(GitServerProto.GitServer.getDefaultInstance()))
        p.addLast(ProtobufVarint32LengthFieldPrepender())
        p.addLast(ProtobufEncoder())
        p.addLast(GitChannelHandler(cli))
    }

}