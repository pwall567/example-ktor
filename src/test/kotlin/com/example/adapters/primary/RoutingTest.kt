package com.example.adapters.primary

import kotlin.test.Test
import kotlin.test.assertEquals

import io.ktor.application.Application
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.routing.routing
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication

import io.mockk.coEvery
import io.mockk.mockk

import net.pwall.json.parseJSON
import net.pwall.json.stringifyJSON

import com.example.adapters.secondary.Headers
import com.example.ports.common.AccountId
import com.example.ports.common.CustomerAccount
import com.example.ports.primary.Config
import com.example.ports.primary.CustomerAccountService
import com.example.ports.primary.Properties
import com.example.ports.secondary.CustomerClient

class RoutingTest {

    @Test fun `should route to CustomerAccountService`() {
        withTestApplication(Application::testApp) {
            val customHeader = "custom header"
            val createAccountRequest = CustomerAccount(AccountId(88), "dummy")
            coEvery {
                TestConfig.customerAccountService.createAccount(customHeader, createAccountRequest)
            } returns AccountId(88)
            val testCall = handleRequest(HttpMethod.Post, "/customer/accounts") {
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
    override val customerClient: CustomerClient = mockk()
    override val customerAccountService: CustomerAccountService = mockk()
}

fun Application.testApp() {

    commonConfig()

    routing {
        appRouting(TestConfig)
    }

}
