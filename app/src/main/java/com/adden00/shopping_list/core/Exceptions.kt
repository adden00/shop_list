package com.adden00.shopping_list.core

class FalseSuccessResponseException(override val message: String): RuntimeException(message)
class TokenIsNullException(override val message: String): RuntimeException(message)
class CouldNotCreateAccountException(override val message: String): RuntimeException(message)

