package com.example.adapters.primary

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI

import net.pwall.json.ktor.jsonKtor

import com.example.adapters.secondary.ClientFactory
import com.example.adapters.secondary.ExampleClientImpl
import com.example.application.CreateAccountServiceImpl
import com.example.application.logging.LoggerDelegate
import com.example.application.logging.info
import com.example.ports.primary.Config
import com.example.ports.primary.CreateAccountService
import com.example.ports.primary.Properties
import com.example.ports.secondary.ExampleClient

@KtorExperimentalAPI
object AppConfig : Config {

    override val properties: Properties
        get() = ktorProperties
    override val exampleClient: ExampleClient = ExampleClientImpl(ClientFactory.exampleHttpClient)
    override val createAccountService: CreateAccountService = CreateAccountServiceImpl(exampleClient)

    private lateinit var ktorProperties: KtorProperties

    private val log by LoggerDelegate()

    @Suppress("unused")
    fun Application.module() {

        ktorProperties = KtorProperties(environment)

        install(StatusPages) {
            exception<IllegalArgumentException> { call.respond(HttpStatusCode.BadRequest) }
            exception<Throwable> { call.respond(HttpStatusCode.InternalServerError) }
        }

        install(ContentNegotiation) {
            jsonKtor {}
        }

        routing {
            appRouting(AppConfig)
        }

        log.info { properties["app.startMessage"] ?: "Starting..." }

    }

}