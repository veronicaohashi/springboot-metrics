package com.veronica.springbootmetrics.application.services

import com.veronica.springbootmetrics.boundaries.controller.request.CancelOrderRequest
import com.veronica.springbootmetrics.boundaries.controller.request.CreateOrderRequest
import com.veronica.springbootmetrics.domain.CoffeeType
import com.veronica.springbootmetrics.domain.Order
import com.veronica.springbootmetrics.domain.OrderItem
import com.veronica.springbootmetrics.domain.OrderStatus
import com.veronica.springbootmetrics.infrastructure.metrics.Metric
import com.veronica.springbootmetrics.infrastructure.metrics.MetricSender
import com.veronica.springbootmetrics.infrastructure.repository.CoffeeRepository
import com.veronica.springbootmetrics.infrastructure.repository.OrderItemRepository
import com.veronica.springbootmetrics.infrastructure.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OrderService(
  private val coffeeRepository: CoffeeRepository,
  private val orderRepository: OrderRepository,
  private val orderItemRepository: OrderItemRepository,
  private val metricSender: MetricSender
) {
  private val logger = LoggerFactory.getLogger(this.javaClass)

  fun create(request: CreateOrderRequest): Order {
    val order = orderRepository.save(
      Order(
        personName = request.personName,
        status = OrderStatus.PROCESSING,
      )
    )

    val items: List<OrderItem> = request.items.map { coffeeRequest ->
      coffeeRepository.findByType(CoffeeType.valueOf(coffeeRequest.type))?.let {
        OrderItem(
          coffee = it,
          order = order,
          quantity = coffeeRequest.quantity
        )
      } ?: throw RuntimeException("Coffee ${coffeeRequest.type} not found")
    }

    orderItemRepository.saveAll(items)

    order.addItems(items)
    order.calculateTotalAmount()
    order.finished()

    return orderRepository.save(order).also {
      logger.info("Order ${it.id} created for ${it.personName}")
      metricSender.orderCount(mapOf("status" to it.status.name))
      metricSender.orderSize(items = it.items)
      metricSender.orderAmount(amount = it.amount!!)
    }

  }

  fun cancel(id: Long, request: CancelOrderRequest): Order {
    val order = orderRepository.findByIdOrNull(id) ?: throw RuntimeException("Coffee $id not found")

    order.cancel(request.reason)

    return orderRepository.save(order).also {
      logger.info("Order ${it.id} canceled")
      metricSender.orderCount(mapOf("status" to order.status.name))
    }
  }
}