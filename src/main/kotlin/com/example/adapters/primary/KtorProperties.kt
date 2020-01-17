package com.example.adapters.primary

import io.ktor.application.ApplicationEnvironment
import io.ktor.util.KtorExperimentalAPI

import com.example.ports.primary.Properties

@KtorExperimentalAPI
internal class KtorProperties(private val environment: ApplicationEnvironment) :
        Properties {

    override fun get(string: String): String? {
        return environment.config.propertyOrNull(string)?.getString()
    }

}
