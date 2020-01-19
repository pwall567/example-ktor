package com.example.ports.secondary

import com.example.application.model.AccountId
import com.example.application.model.CustomerAccount

interface CustomerClient {
    suspend fun listAccounts(): List<CustomerAccount>
    suspend fun createAccount(customHeader: String, request: CustomerAccount): AccountId
}
