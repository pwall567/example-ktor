package com.example.adapters.primary

import kotlin.test.Test
import kotlin.test.assertEquals

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication

import io.mockk.coEvery
import io.mockk.mockk

import net.pwall.json.ktor.jsonKtor
import net.pwall.json.parseJSON
import net.pwall.json.stringifyJSON

import com.example.adapters.secondary.Headers
import com.example.application.model.AccountId
import com.example.application.model.ExampleAccount
import com.example.ports.primary.Config
import com.example.ports.primary.CreateAccountService
import com.example.ports.primary.Properties
import com.example.ports.secondary.ExampleClient

class RoutingTest {

    @Test fun `should route to CreateAccountService`() {
        withTestApplication(Application::testApp) {
            val customHeader = "custom header"
            val createAccountRequest = ExampleAccount(AccountId(88), "dummy")
            coEvery {
                TestConfig.createAccountService.createAccount(customHeader, createAccountRequest)
            } returns AccountId(88)
            val testCall = handleRequest(HttpMethod.Post, "/example/accounts") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                addHeader(Headers.CUSTOM_HEADER, customHeader)
                setBody(createAccountRequest.stringifyJSON())
            }
            assertEquals(88, testCall.response.content?.parseJSON<AccountId>()?.id)
        }
    }

}

object TestConfig : Config {
    override val properties: Properties = Properties.EmptyProperties
    override val exampleClient: ExampleClient = mockk()
    override val createAccountService: CreateAccountService = mockk()
}

fun Application.testApp() {

    install(StatusPages) {
        exception<IllegalArgumentException> { call.respond(HttpStatusCode.BadRequest) }
        exception<Throwable> { call.respond(HttpStatusCode.InternalServerError) }
    }

    install(ContentNegotiation) {
        jsonKtor {}
    }

    routing {
        appRouting(TestConfig)
    }

}
