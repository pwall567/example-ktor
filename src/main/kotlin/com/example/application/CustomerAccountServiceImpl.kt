package com.example.application

import com.example.ports.common.AccountId
import com.example.ports.common.CustomerAccount
import com.example.ports.primary.CustomerAccountService
import com.example.ports.secondary.CustomerClient

class CustomerAccountServiceImpl(private val customerClient: CustomerClient) : CustomerAccountService {

    override suspend fun listAccounts(): List<CustomerAccount> {
        return customerClient.listAccounts()
    }

    override suspend fun createAccount(customHeader: String, request: CustomerAccount): AccountId {
        return customerClient.createAccount(customHeader, request)
    }

}
