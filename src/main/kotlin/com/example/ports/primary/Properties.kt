package com.example.ports.primary

interface Properties {

    operator fun get(string: String): String?

    object EmptyProperties : Properties {
        override fun get(string: String): String? = null
    }

}
