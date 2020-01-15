package com.example.adapters.secondary

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JsonFeature

import net.pwall.json.ktor.client.jsonKtorClient

object ClientFactory {

    val exampleHttpClient: HttpClient by lazy {
        HttpClient(Apache) {
            install(JsonFeature) {
                jsonKtorClient {}
            }
        }
    }

}
