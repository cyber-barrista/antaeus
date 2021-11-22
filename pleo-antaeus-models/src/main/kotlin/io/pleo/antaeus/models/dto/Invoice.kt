package io.pleo.antaeus.models.dto

import io.pleo.antaeus.models.status.InvoiceStatus

data class Invoice(
    val id: Int,
    val customerId: Int,
    val amount: Money,
    val status: InvoiceStatus
)
