package com.y4d.git.cli

import picocli.CommandLine
import picocli.CommandLine.*
import java.net.InetAddress
import java.util.concurrent.Callable

@Command(name = "rgc", aliases = ["remote git client"])
class GitCommandLine : Callable<Int> {
    // 0..*表示包含了所有参数
    @Parameters
    lateinit var path: String

    @Option(names = ["-c", "--cmd"], required = true)
    lateinit var cmd: String

    @Option(names = ["-h", "--host"], required = false)
    var host = "127.0.0.1"

    @Option(names = ["-p", "--port"], required = false)
    var port = 12306

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cli = GitCommandLine()
            // 注入参数
            val line = CommandLine(cli)
            line.execute(*args)
        }
    }

    override fun call(): Int {
        val build =
            GitCliProto.GitCli.newBuilder().setUserId(System.getProperty("user.name"))
                .setUserIp(InetAddress.getLocalHost().hostAddress).setCmd(cmd).setPath(path)
                .setHost(host).setPort(port).build()
        GitServer(build).boot()
        // 默认执行成功
        return 0;
    }
}