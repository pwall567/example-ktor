package com.example.ports.primary

import com.example.ports.common.AccountId
import com.example.ports.common.CustomerAccount

interface CustomerAccountService {
    suspend fun listAccounts(): List<CustomerAccount>
    suspend fun createAccount(customHeader: String, request: CustomerAccount): AccountId
}
