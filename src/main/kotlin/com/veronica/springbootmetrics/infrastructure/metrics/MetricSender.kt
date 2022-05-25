package com.veronica.springbootmetrics.infrastructure.metrics

import com.veronica.springbootmetrics.domain.Order
import com.veronica.springbootmetrics.domain.OrderItem
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class MetricSender(
  @Value("\${app.monitoring.prefix}") private val prefix: String,
  private val meterRegistry: MeterRegistry
) {
  private val logger = LoggerFactory.getLogger(this.javaClass)

  fun requestCount(tags: Map<String, String>) {
    try {
      Counter.builder("$prefix.${Metric.REQUEST_COUNT.label}")
        .description("Number of requests per endpoint")
        .tags(tags.map { Tag.of(it.key, it.value) })
        .register(meterRegistry)
        .increment()
    } catch (exception: Exception) {
      handleError(Metric.REQUEST_COUNT, tags, exception)
    }

//    rate(spring_boot_metrics_request_count_total[5m])
  }

  fun orderCount(tags: Map<String, String>) {
    try {
      Counter.builder("$prefix.${Metric.ORDER_COUNT.label}")
        .description("Number of orders by status")
        .tags(tags.map { Tag.of(it.key, it.value) })
        .register(meterRegistry)
        .increment()
    } catch (exception: Exception) {
      handleError(Metric.ORDER_COUNT, tags, exception)
    }
  }

  fun orderSize(items: List<OrderItem>) {
    try {
        Gauge.builder("$prefix.${Metric.ORDER_SIZE.label}") { items.map{it.quantity}.reduce{ acc, it -> acc + it} }
          .description("Size of order")
          .register(meterRegistry)
    } catch (exception: Exception) {
      handleError(Metric.ORDER_SIZE, null, exception)
    }
  }

  fun orderAmount(amount: BigDecimal) {
    try {
      Gauge.builder("$prefix.${Metric.ORDER_AMOUNT.label}") { amount }
        .description("Size of order")
        .register(meterRegistry)
    } catch (exception: Exception) {
      handleError(Metric.ORDER_AMOUNT, null, exception)
    }
  }

  private fun handleError(metric: Metric, tags: Map<String, String>?, error: Throwable) {
    val customMessage = "Error sending metric '${metric.label}' with tags=${tags}"
    logger.error("$customMessage: ${error.localizedMessage}", error)
  }
}

enum class Metric(val label: String) {
  REQUEST_COUNT("request_count"),
  ORDER_COUNT("order_count"),
  ORDER_SIZE("order_size"),
  ORDER_AMOUNT("order_amount")
}