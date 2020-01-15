package com.example.application

import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

import io.mockk.coEvery
import io.mockk.mockk

import com.example.application.model.AccountId
import com.example.application.model.ExampleAccount
import com.example.ports.secondary.ExampleClient

class CreateAccountServiceImplTest {

    private lateinit var createAccountService: CreateAccountServiceImpl
    private lateinit var exampleClient: ExampleClient

    private val customHeader = "dummy custom header"
    private val accountDetail = ExampleAccount(AccountId(777), "Q")

    @BeforeTest fun setUp() {
        exampleClient = mockk()
        createAccountService = CreateAccountServiceImpl(exampleClient)
    }

    @Test fun `should return account details when term deposit account is successfully created`() {
        // given
        val createAccountRequest = accountDetail
        val responseEntity = AccountId(777)
        coEvery {
            exampleClient.createAccount(customHeader, createAccountRequest)
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
