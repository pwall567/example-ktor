package com.example

import io.ktor.application.Application
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI

import com.example.adapters.primary.KtorProperties
import com.example.adapters.primary.appRouting
import com.example.adapters.primary.commonConfig
import com.example.adapters.secondary.ClientFactory
import com.example.adapters.secondary.CustomerClientImpl
import com.example.adapters.secondary.LoggerDelegate
import com.example.application.CustomerAccountServiceImpl
import com.example.ports.primary.Config
import com.example.ports.primary.CustomerAccountService
import com.example.ports.primary.Properties
import com.example.ports.secondary.CustomerClient

@KtorExperimentalAPI
object AppConfig : Config {

    override val properties: Properties
        get() = ktorProperties
    override val customerClient: CustomerClient = CustomerClientImpl(ClientFactory.createHttpClient())
    override val customerAccountService: CustomerAccountService = CustomerAccountServiceImpl(customerClient)

    private lateinit var ktorProperties: KtorProperties

    val log by LoggerDelegate()

    @Suppress("unused")
    fun Application.module() {

        ktorProperties = KtorProperties(environment)

        commonConfig()

        routing {
            appRouting(AppConfig)
        }

        log.info { properties["app.startMessage"] ?: "Starting..." }

    }

}
