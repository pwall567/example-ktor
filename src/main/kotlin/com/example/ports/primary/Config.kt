package com.example.ports.primary

import com.example.ports.secondary.CustomerClient

interface Config {
    val properties: Properties
    val customerClient: CustomerClient
    val customerAccountService: CustomerAccountService
}
