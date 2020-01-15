package com.example.adapters.secondary

import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.response.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

import com.example.application.model.AccountId
import com.example.application.model.ExampleAccount
import com.example.ports.secondary.ExampleClient


class ExampleClientImpl(private val client: HttpClient) : ExampleClient {

    override suspend fun createAccount(customHeader: String, request: ExampleAccount): AccountId {
        val response = client.post<HttpResponse>("$EXAMPLE_SERVER_BASE_URI/example/accounts") {
            body = request
            contentType(ContentType.Application.Json)
            headers {
                set(Headers.CUSTOM_HEADER, customHeader)
            }
        }
        when (response.status) {
            HttpStatusCode.Created -> return response.receive()
            else -> throw Exception("Something wrong with server")
        }
    }

    companion object {
        const val EXAMPLE_SERVER_BASE_URI = "http://example"
    }

}
