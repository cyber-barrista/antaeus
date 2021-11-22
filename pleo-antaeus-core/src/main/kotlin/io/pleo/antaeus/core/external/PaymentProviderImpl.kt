package io.pleo.antaeus.core.external

import io.pleo.antaeus.core.exceptions.CurrencyMismatchException
import io.pleo.antaeus.core.exceptions.NetworkException
import io.pleo.antaeus.core.services.CustomerService
import io.pleo.antaeus.models.dto.Invoice
import kotlin.random.Random

class PaymentProviderImpl(private val customerService: CustomerService) : PaymentProvider {
    override fun charge(invoice: Invoice): Boolean {
        val customer = customerService.fetch(invoice.customerId)

        if (invoice.amount.currency != customer.currency) throw CurrencyMismatchException(invoice.id, customer.id)

        customerService
            .fetch(invoice.customerId)
            .also { if (invoice.amount.currency != it.currency) throw CurrencyMismatchException(invoice.id, it.id) }

        return performPaymentOverNetwork()
    }

    // For demonstration purposes
    @Throws(NetworkException::class)
    private fun performPaymentOverNetwork(): Boolean {
        // Whole bunch of network stuff

        return Random.nextBoolean()
    }
}
