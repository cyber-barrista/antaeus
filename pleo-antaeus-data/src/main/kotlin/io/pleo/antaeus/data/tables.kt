/*
    Defines database tables and their schemas.
    To be used by `AntaeusDal`.
 */

package io.pleo.antaeus.data

import io.pleo.antaeus.models.status.InvoiceStatus
import io.pleo.antaeus.models.status.PaymentStatus
import org.jetbrains.exposed.sql.CurrentDateTime
import org.jetbrains.exposed.sql.Table

object PaymentTable : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val invoiceId = reference("invoice_id", CustomerTable.id)
    val status = text("status").check { it inList stringValues<PaymentStatus>() }
    val happenedAt = datetime("happened_at").defaultExpression(CurrentDateTime())
}

object InvoiceTable : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val currency = varchar("currency", 3)
    val value = decimal("value", 1000, 2)
    val customerId = reference("customer_id", CustomerTable.id)
    val status = text("status").check { it.inList(stringValues<InvoiceStatus>()) }
}

object CustomerTable : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val currency = varchar("currency", 3)
}

private inline fun <reified T : Enum<T>> stringValues() = enumValues<T>().map { it.name }
