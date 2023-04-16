package com.y4d.git.server

class GitCommandLine {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            GitServer().bind()
        }
    }
}