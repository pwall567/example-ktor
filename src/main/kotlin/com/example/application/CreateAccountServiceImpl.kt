package com.example.application

import com.example.application.model.AccountId
import com.example.application.model.ExampleAccount
import com.example.ports.primary.CreateAccountService
import com.example.ports.secondary.ExampleClient

class CreateAccountServiceImpl(private val exampleClient: ExampleClient) :
        CreateAccountService {

    override suspend fun createAccount(customHeader: String, request: ExampleAccount): AccountId {
        return exampleClient.createAccount(customHeader, request)
    }

}
