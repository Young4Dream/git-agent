package com.y4d.git.server

import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import java.util.concurrent.Callable

@Command(name = "rgs", aliases = ["git remote server"])
class GitCommandLine : Callable<Int> {
    @Option(names = ["-w", "--workdir"], required = true)
    private lateinit var workdir: String

    @Option(names = ["-p", "--port"], required = false)
    private var port = 12306

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val commandLine = CommandLine(GitCommandLine())
            commandLine.execute(*args)
        }
    }

    override fun call(): Int {
        GitServer(workdir).bind(port)
        return 0
    }
}