package com.example.adapters.primary

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.request.header
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post

import com.example.adapters.secondary.Headers
import com.example.application.model.ExampleAccount
import com.example.ports.primary.Config

fun Routing.appRouting(config: Config) {

    post("/example/accounts") {
        val customHeader = call.requireHeader(Headers.CUSTOM_HEADER)
        // invoke service with parameter from request body
        val request = call.receive<ExampleAccount>()
        call.respond(config.createAccountService.createAccount(customHeader, request))
    }

}

fun ApplicationCall.requireHeader(headerName: String) =
        requireNotNull(request.header(headerName)) { "Missing $headerName header" }
