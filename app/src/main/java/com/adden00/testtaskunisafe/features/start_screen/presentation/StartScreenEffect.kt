package com.adden00.testtaskunisafe.features.start_screen.presentation

data class StartScreenState(
    val internetIsConnected: Boolean = false,
    val tokenReceived: Boolean = false,
    val needToAuth: Boolean = false
)