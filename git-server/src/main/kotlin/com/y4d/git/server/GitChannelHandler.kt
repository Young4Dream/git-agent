package com.y4d.git.server

import com.y4d.git.cli.GitCliProto
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader

class GitChannelHandler(private val workspace: String, private val gitHome: String) : ChannelInboundHandlerAdapter() {
    private val builder = StringBuilder()
    private val serverBuilder = GitServerProto.GitServer.newBuilder()

    @Deprecated("Deprecated in Java")
    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause?.printStackTrace()
        ctx?.close()
    }

    override fun channelActive(ctx: ChannelHandlerContext?) {
        builder.clear()
    }

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        val gitCli = msg as GitCliProto.GitCli
        val value = !gitCli.cmd.startsWith("git")
        // 仅仅支持git命令
        val cmd = if (value) "$gitHome/bin/git ${gitCli.cmd}" else "$gitHome/bin/${gitCli.cmd}"
        val process =
            Runtime.getRuntime().exec(cmd, emptyArray<String>(), File(workspace + gitCli.path))
        val thread = Thread(GitReader(process.inputStream, builder))
        thread.start()
        val reader = BufferedReader(InputStreamReader(process.errorStream))
        var errMsg = reader.readLine()
        while (errMsg != null) {
            builder.append(errMsg).append(System.lineSeparator())
            errMsg = reader.readLine()
        }
        process.waitFor()
        val exitValue = process.exitValue()
        serverBuilder.setResponse(builder.toString()).setSuccess(exitValue == 0)
        ctx?.writeAndFlush(serverBuilder.build())
    }
}

class GitReader(private val stream: InputStream, private val builder: StringBuilder) : Runnable {
    override fun run() {
        val reader = BufferedReader(InputStreamReader(stream))
        var msg = reader.readLine()
        while (msg != null) {
            builder.append(msg).append(System.lineSeparator())
            msg = reader.readLine()
        }
    }
}