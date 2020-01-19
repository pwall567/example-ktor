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
import com.example.application.model.CustomerAccount
import com.example.ports.secondary.CustomerClient
import io.ktor.client.request.get


class CustomerClientImpl(private val client: HttpClient) : CustomerClient {

    override suspend fun listAccounts(): List<CustomerAccount> {
        val response = client.get<HttpResponse>("$CUSTOMER_SERVER_BASE_URI/customer/accounts")
        when (response.status) {
            HttpStatusCode.OK -> return response.receive()
            else -> throw Exception("Something went wrong on list accounts")
        }
    }

    override suspend fun createAccount(customHeader: String, request: CustomerAccount): AccountId {
        val response = client.post<HttpResponse>("$CUSTOMER_SERVER_BASE_URI/customer/accounts") {
            body = request
            contentType(ContentType.Application.Json)
            headers {
                set(Headers.CUSTOM_HEADER, customHeader)
            }
        }
        when (response.status) {
            HttpStatusCode.Created -> return response.receive()
            else -> throw Exception("Something went wrong on create account")
        }
    }

    companion object {
        const val CUSTOMER_SERVER_BASE_URI = "http://example"
    }

}
