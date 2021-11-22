package io.pleo.antaeus.models.dto

import io.pleo.antaeus.models.status.PaymentStatus
import java.time.Instant

data class Payment(
    val id: Int,
    val invoiceId: Int,
    val status: PaymentStatus,
    val happenedAt: Instant
)
