package com.example.adapters.secondary

import com.example.ports.secondary.Logger

class LoggerImpl(val nativeLogger: org.slf4j.Logger) : Logger {

    override fun info(block: () -> Any) {
        if (nativeLogger.isInfoEnabled)
            nativeLogger.info(block().toString())
    }

    override fun debug(block: () -> Any) {
        if (nativeLogger.isDebugEnabled)
            nativeLogger.debug(block().toString())
    }

    override fun error(block: () -> Any) {
        if (nativeLogger.isErrorEnabled)
            nativeLogger.error(block().toString())
    }

    override fun error(t: Throwable, block: () -> Any) {
        if (nativeLogger.isErrorEnabled)
            nativeLogger.error(block().toString(), t)
    }

}
