package com.veronica.springbootmetrics.boundaries.controller

import com.veronica.springbootmetrics.application.services.OrderService
import com.veronica.springbootmetrics.boundaries.controller.request.CancelOrderRequest
import com.veronica.springbootmetrics.boundaries.controller.request.CreateOrderRequest
import com.veronica.springbootmetrics.boundaries.controller.response.CreateOrderResponse
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
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController(
  private val metricSender: MetricSender,
  private val orderService: OrderService
) {
  private val logger = LoggerFactory.getLogger(this.javaClass)

  @PostMapping
  fun create(
    @RequestBody request: CreateOrderRequest
  ): ResponseEntity<CreateOrderResponse> {
    logger.info("Received a request create an order with ${request.items.size} for ${request.personName}")
    metricSender.requestCount(mapOf("uri" to "/orders"))
    val order = orderService.create(request)

    return CreateOrderResponse(order).let(ResponseEntity.status(HttpStatus.CREATED)::body)
  }


  @PostMapping("{id}/cancel")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  fun cancel(
    @PathVariable id: String,
    @RequestBody request: CancelOrderRequest
  ) {
    logger.info("Received a request cancel the order $id")
    metricSender.requestCount(mapOf("uri" to "/orders/{id}/cancel"))

    orderService.cancel(id.toLong(), request)
  }
}