package com.example.application.logging

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.companionObject

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class LoggerDelegate<R: Any> : ReadOnlyProperty<R, Logger> {

    private var logger: Logger = dummyLogger

    override fun getValue(thisRef: R, property: KProperty<*>): Logger {
        if (logger === dummyLogger)
            logger = getLogger(thisRef.javaClass.let { c ->
                c.enclosingClass?.takeIf { it.kotlin.companionObject?.java == c } ?: c
            })
        return logger
    }

    companion object {
        val dummyLogger: Logger = LoggerFactory.getLogger(LoggerDelegate::class.java)
    }

}

fun getLogger(forClass: Class<*>): Logger = LoggerFactory.getLogger(forClass)

inline fun Logger.debug(block: () -> Any) {
    if (isDebugEnabled)
        debug(block().toString())
}

inline fun Logger.error(block: () -> Any) {
    if (isErrorEnabled)
        error(block().toString())
}

inline fun Logger.error(t: Throwable, block: () -> Any) {
    if (isErrorEnabled)
        error(block().toString(), t)
}

inline fun Logger.info(block: () -> Any) {
    if (isInfoEnabled)
        info(block().toString())
}

inline fun Logger.info(t: Throwable, block: () -> Any) {
    if (isInfoEnabled)
        info(block().toString(), t)
}

inline fun Logger.trace(block: () -> Any) {
    if (isTraceEnabled)
        trace(block().toString())
}

inline fun Logger.warn(block: () -> Any) {
    if (isWarnEnabled)
        warn(block().toString())
}

inline fun Logger.warn(t: Throwable, block: () -> Any) {
    if (isWarnEnabled)
        warn(block().toString(), t)
}
