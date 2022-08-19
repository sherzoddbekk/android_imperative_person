package com.example.android_imperative.network

object Server {

    val IS_TESTER = true
    val SERVER_DEVELOPMENT = "https://www.episodate.com/"
    val SERVER_PRODUCTION = "https://www.episodate.com/"

    init {
        System.loadLibrary("keys")
    }

    external fun getDevelopment(): String
    external fun getProduction(): String

}