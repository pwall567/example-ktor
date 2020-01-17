package com.example.adapters.primary

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond

import net.pwall.json.ktor.jsonKtor

fun Application.commonConfig() {

    install(StatusPages) {
        exception<IllegalArgumentException> { call.respond(HttpStatusCode.BadRequest) }
        exception<Throwable> { call.respond(HttpStatusCode.InternalServerError) }
    }

    install(ContentNegotiation) {
        jsonKtor {}
    }

}
