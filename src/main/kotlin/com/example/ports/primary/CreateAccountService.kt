package com.example.ports.primary

import com.example.application.model.AccountId
import com.example.application.model.CustomerAccount

interface CreateAccountService {
    suspend fun listAccounts(): List<CustomerAccount>
    suspend fun createAccount(customHeader: String, request: CustomerAccount): AccountId
}
