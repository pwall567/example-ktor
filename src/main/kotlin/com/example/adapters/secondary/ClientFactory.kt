package com.example.adapters.secondary

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JsonFeature

import net.pwall.json.ktor.client.jsonKtorClient

internal object ClientFactory {

    fun createHttpClient() =
            HttpClient(Apache) {
                install(JsonFeature) {
                    jsonKtorClient {}
                }
            }

}
