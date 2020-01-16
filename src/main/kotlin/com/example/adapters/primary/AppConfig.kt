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
import com.example.adapters.secondary.CustomerClientImpl
import com.example.application.CreateAccountServiceImpl
import com.example.ports.primary.Config
import com.example.ports.primary.CreateAccountService
import com.example.ports.primary.Properties
import com.example.ports.secondary.CustomerClient

@KtorExperimentalAPI
object AppConfig : Config {

    override val properties: Properties
        get() = ktorProperties
    override val customerClient: CustomerClient = CustomerClientImpl(ClientFactory.createHttpClient())
    override val createAccountService: CreateAccountService = CreateAccountServiceImpl(customerClient)

    private lateinit var ktorProperties: KtorProperties

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

        println(properties["app.startMessage"] ?: "Starting...")

    }

}
