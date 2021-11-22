package io.pleo.antaeus.core.services

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.pleo.antaeus.core.external.PaymentProvider
import io.pleo.antaeus.data.AntaeusDal
import io.pleo.antaeus.models.dto.Currency.EUR
import io.pleo.antaeus.models.dto.Invoice
import io.pleo.antaeus.models.dto.Money
import io.pleo.antaeus.models.status.InvoiceStatus.PENDING
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class PaymentServiceTest {
    private val targetInvoice = Invoice(
        id = 1,
        customerId = 1,
        amount = Money(value = BigDecimal.valueOf(1), currency = EUR),
        status = PENDING
    )

    private val dal = mockk<AntaeusDal> {
        every { fetchInvoicesByStatus(PENDING) } returns listOf(targetInvoice)
        every { enrollSuccessfulPayment(any()) } answers {}
        every { logFailedPayment(any()) } answers {}
    }

    @Test
    fun `will enroll payment down to the DB if it's successful`() {
        val paymentProvider = mockk<PaymentProvider> { every { charge(any()) } returns true }

        PaymentService(dal = dal, paymentProvider = paymentProvider).payForPendingInvoices()

        verify { dal.enrollSuccessfulPayment(targetInvoice.id) }
        verify(inverse = true) { dal.logFailedPayment(any()) }
    }

    @Test
    fun `will log failed payment down to the DB if it's unsuccessful`() {
        val paymentProvider = mockk<PaymentProvider> { every { charge(any()) } returns false }

        PaymentService(dal = dal, paymentProvider = paymentProvider).payForPendingInvoices()

        verify { dal.logFailedPayment(targetInvoice.id) }
        verify(inverse = true) { dal.enrollSuccessfulPayment(any()) }
    }

    @Test
    fun `will log failed payment down to the DB if it's erroneous`() {
        val paymentProvider = mockk<PaymentProvider> { every { charge(any()) } throws Exception("Ops") }

        PaymentService(dal = dal, paymentProvider = paymentProvider).payForPendingInvoices()

        verify { dal.logFailedPayment(targetInvoice.id) }
        verify(inverse = true) { dal.enrollSuccessfulPayment(any()) }
    }
}
