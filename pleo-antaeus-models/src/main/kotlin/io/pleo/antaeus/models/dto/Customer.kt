package io.pleo.antaeus.models.dto

import io.pleo.antaeus.models.dto.Currency

data class Customer(
    val id: Int,
    val currency: Currency
)
