package com.example.adapters.secondary

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.expect
import kotlinx.coroutines.runBlocking

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.features.json.JsonFeature
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.fullPath
import io.ktor.http.headersOf

import net.pwall.json.ktor.client.jsonKtorClient
import net.pwall.json.stringifyJSON

import com.example.ports.common.AccountId
import com.example.ports.common.CustomerAccount
import com.example.ports.secondary.CustomerClient

class CustomerClientImplTest {

    private lateinit var httpClient: HttpClient
    private lateinit var customerClient: CustomerClient

    private val customHeader = "qwerty"
    private val account = CustomerAccount(AccountId(1), "Mr Dummy")

    private val standardHeaders = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())

    @BeforeTest fun setUp() {
        httpClient = HttpClient(MockEngine) {
            install(JsonFeature) {
                jsonKtorClient {}
            }
            engine {
                addHandler { request ->
                    when (request.url.fullPath) {
                        "/customer/accounts" ->
                            when (request.method) {
                                HttpMethod.Get ->
                                    respond(listOf(account).stringifyJSON(), HttpStatusCode.OK, standardHeaders)
                                HttpMethod.Post ->
                                    respond(account.id.stringifyJSON(), HttpStatusCode.Created, standardHeaders)
                                else -> error("Unhandled method ${request.method}")
                            }
                        else -> error("Unhandled ${request.url.fullPath}")
                    }
                }
            }
        }
        customerClient = CustomerClientImpl(httpClient)
    }

    @Test fun `should return list of accounts`() {
        // when
        val response = runBlocking {
            customerClient.listAccounts()
        }

        // then
        expect (listOf(account)) {
            response
        }
    }

    @Test fun `should send createAccount POST rest request to example-adapter`() {
        val createAccountRequest = CustomerAccount(AccountId(27), "xxxxx")

        // when
        val response = runBlocking {
            customerClient.createAccount(
                    customHeader = customHeader,
                    request = createAccountRequest)
        }

        // then
        expect(account.id) {
            response
        }

    }

}
