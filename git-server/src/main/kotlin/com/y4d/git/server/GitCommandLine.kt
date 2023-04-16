package com.y4d.git.server

import java.nio.file.Paths
import java.util.*
import kotlin.io.path.exists
import kotlin.io.path.inputStream
import kotlin.system.exitProcess

class GitCommandLine {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val properties = Properties()
            val path = Paths.get(".install4j/response.varfile")
            val exists = path.exists()
            if (!exists) {
                System.err.println("cannot find varfile!")
                exitProcess(1)
            }
            try {
                val inputStream = path.inputStream()
                properties.load(inputStream)
                for (p in properties) {
                    println("${p.key}:${p.value}")
                }
                val property = properties.getProperty("port")
                val workdir = properties.getProperty("workdir")
                GitServer(workdir).bind(property.toInt())
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}