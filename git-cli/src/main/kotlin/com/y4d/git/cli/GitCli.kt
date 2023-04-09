package com.y4d.git.cli

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import picocli.CommandLine
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters
import java.util.concurrent.Callable

class GitCli : ChannelInboundHandlerAdapter(), Callable<Int> {
    // 0..*表示包含了所有参数
    @Parameters(index = "0..*")
    lateinit var path: String

    @Option(names = ["-c", "--cmd"], required = true)
    lateinit var cmd: String

    @Option(names = ["-h", "--host"], required = false)
    var host = "127.0.0.1"

    @Option(names = ["-p", "--port"], required = false)
    var port = 12306

    lateinit var gitCliProto: GitCliProto

    override fun channelActive(ctx: ChannelHandlerContext) {
        ctx.writeAndFlush(gitCliProto)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cli = GitCli()
            // 注入参数
            val line = CommandLine(cli)
            val exitCode = line.execute(*args)
            println(exitCode)
        }
    }

    override fun call(): Int {
        val client = Client()
        client.boot(this)

        return 1;
    }

}