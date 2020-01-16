package com.example.application

import com.example.application.model.AccountId
import com.example.application.model.CustomerAccount
import com.example.ports.primary.CreateAccountService
import com.example.ports.secondary.CustomerClient

class CreateAccountServiceImpl(private val customerClient: CustomerClient) : CreateAccountService {

    override suspend fun createAccount(customHeader: String, request: CustomerAccount): AccountId {
        return customerClient.createAccount(customHeader, request)
    }

}
