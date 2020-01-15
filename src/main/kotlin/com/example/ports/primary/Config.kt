package com.example.ports.primary

import com.example.ports.secondary.ExampleClient

interface Config {
    val properties: Properties
    val exampleClient: ExampleClient
    val createAccountService: CreateAccountService
}
