package com.example.application

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking

import io.mockk.coEvery
import io.mockk.mockk

import com.example.ports.common.AccountId
import com.example.ports.common.CustomerAccount
import com.example.ports.secondary.CustomerClient

class CreateAccountServiceImplTest {

    private lateinit var createAccountService: CreateAccountServiceImpl
    private lateinit var customerClient: CustomerClient

    private val customHeader = "dummy custom header"
    private val accountDetail = CustomerAccount(AccountId(777), "Q")

    @BeforeTest fun setUp() {
        customerClient = mockk()
        createAccountService = CreateAccountServiceImpl(customerClient)
    }

    @Test fun `should return account details when account is successfully created`() {
        // given
        val createAccountRequest = accountDetail
        val responseEntity = AccountId(777)
        coEvery {
            customerClient.createAccount(customHeader, createAccountRequest)
        } returns responseEntity

        // when
        val result = runBlocking {
            createAccountService.createAccount(customHeader = customHeader,
                    request = createAccountRequest
            )
        }

        // then
        assertEquals(responseEntity, result)
    }

}
