package com.example.adapters.secondary

import com.example.ports.secondary.Logger
import org.slf4j.LoggerFactory
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.companionObject

class LoggerDelegate<C: Any> : ReadOnlyProperty<C, Logger> {

    private var logger: Logger = dummyLogger

    override fun getValue(thisRef: C, property: KProperty<*>): Logger {
        if (logger === dummyLogger)
            logger = LoggerImpl(LoggerFactory.getLogger(thisRef.javaClass.let { jc ->
                jc.enclosingClass?.takeIf { it.kotlin.companionObject?.java == jc } ?: jc
            }))
        return logger
    }

    companion object {
        val dummyLogger: Logger = LoggerImpl(LoggerFactory.getLogger(LoggerDelegate::class.java))
    }

}
