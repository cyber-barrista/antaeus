package io.pleo.antaeus.models.dto

import java.math.BigDecimal

data class Money(
    val value: BigDecimal,
    val currency: Currency
)
