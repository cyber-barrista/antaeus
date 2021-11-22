package io.pleo.antaeus.core.services

import io.pleo.antaeus.core.external.PaymentProvider
import io.pleo.antaeus.data.AntaeusDal
import io.pleo.antaeus.models.status.InvoiceStatus.PENDING
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class PaymentService(
    private val dal: AntaeusDal,
    private val paymentProvider: PaymentProvider
) {
    fun payForPendingInvoices() = dal
        .fetchInvoicesByStatus(PENDING)
        .forEach {
            try {
                if (paymentProvider.charge(it)) {
                    logger.debug { "Payment for $it proceeded successfully" }
                    dal.enrollSuccessfulPayment(it.id)
                } else {
                    // TODO Needs a better handling, e.g. special PaymentStatus.IN_DEBT status which would lead to
                    //  consequent limited access to the service and a charge retry scheduled to be performed shortly
                    logger.warn { "Payment for $it failed. The customer's customerId=${it.customerId} balance is low" }
                    dal.logFailedPayment(it.id)
                }
            } catch (e: Exception) {
                // TODO Consider introducing retrying service
                //  since it might be helpful for potentially recoverable errors, e.g. NetworkException
                logger.error(e) { "Error during $it invoice payment. It will be left unpaid" }
                dal.logFailedPayment(it.id)
            }
        }
}
