package com.example.ports.secondary

import com.example.application.model.AccountId
import com.example.application.model.ExampleAccount

interface ExampleClient {
    suspend fun createAccount(customHeader: String, request: ExampleAccount): AccountId
}
