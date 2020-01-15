package com.example.adapters.secondary

import com.example.application.model.AccountId
import com.example.application.model.ExampleAccount
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.features.json.JsonFeature
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.fullPath
import io.ktor.http.headersOf

import net.pwall.json.ktor.client.jsonKtorClient
import net.pwall.json.stringifyJSON

import com.example.ports.secondary.ExampleClient

class TermDepositsClientImplTest {

    private lateinit var httpClient: HttpClient
    private lateinit var exampleClient: ExampleClient

    private val customHeader = "qwerty"
//    private val bankingAccountDetail = BankingAccountDetail(
//            accountId = RandomString.randomString(),
//            accountNumber = RandomString.randomNumericString(),
//            bsb = RandomString.randomNumericString(6),
//            maskedNumber = ValidationUtil.maskNumber((RandomString.randomString())),
//            displayName = RandomString.randomString(),
//            openStatus = BankingAccountDetail.OpenStatus.OPEN,
//            productCategory = BankingProductCategory.TERM_DEPOSITS,
//            productName = RandomString.randomString(),
//            features = emptyList()
//    )
    private val account = ExampleAccount(AccountId(1), "Mr Dummy")

    private val standardHeaders = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())

    @BeforeTest fun setUp() {
        httpClient = HttpClient(MockEngine) {
            install(JsonFeature) {
                jsonKtorClient {}
            }
            engine {
                addHandler { request ->
                    when (request.url.fullPath) {
                        "/example/accounts" ->
                            respond(account.id.stringifyJSON(), HttpStatusCode.Created, standardHeaders)
                        else -> error("Unhandled ${request.url.fullPath}")
                    }
                }
            }
        }
        exampleClient = ExampleClientImpl(httpClient)
    }

    @Test fun `should send createAccount POST rest request to example-adapter`() {
        val createAccountRequest = ExampleAccount(AccountId(27), "xxxxx")

        // when
        val response = runBlocking {
            exampleClient.createAccount(
                    customHeader = customHeader,
                    request = createAccountRequest)
        }

        // then
        assertEquals(createAccountRequest.id, response)

    }

}
