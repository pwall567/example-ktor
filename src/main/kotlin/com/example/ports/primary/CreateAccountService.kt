package com.example.ports.primary

import com.example.application.model.AccountId
import com.example.application.model.ExampleAccount

interface CreateAccountService {
    suspend fun createAccount(customHeader: String, request: ExampleAccount): AccountId
}
