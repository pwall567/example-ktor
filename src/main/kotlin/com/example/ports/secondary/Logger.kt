package com.example.ports.secondary

interface Logger {
    fun info(block: () -> Any)
    fun debug(block: () -> Any)
    fun error(block: () -> Any)
    fun error(t: Throwable, block: () -> Any)
}
