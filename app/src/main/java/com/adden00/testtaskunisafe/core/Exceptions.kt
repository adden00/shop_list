package com.adden00.testtaskunisafe.core

class FalseSuccessResponseException(override val message: String): RuntimeException(message)
class TokenIsNullException(override val message: String): RuntimeException(message)