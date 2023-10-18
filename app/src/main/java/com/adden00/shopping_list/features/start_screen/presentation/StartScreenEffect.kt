package com.adden00.shopping_list.features.start_screen.presentation

data class StartScreenState(
    val internetIsConnected: Boolean = false,
    val tokenReceived: Boolean = false,
    val needToAuth: Boolean = false
)