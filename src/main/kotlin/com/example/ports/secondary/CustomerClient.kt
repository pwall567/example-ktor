package com.example.ports.secondary

import com.example.ports.common.AccountId
import com.example.ports.common.CustomerAccount

interface CustomerClient {
    suspend fun listAccounts(): List<CustomerAccount>
    suspend fun createAccount(customHeader: String, request: CustomerAccount): AccountId
}
