package com.veronica.springbootmetrics.boundaries.controller.response

import com.veronica.springbootmetrics.domain.Coffee
import com.veronica.springbootmetrics.domain.Order
import com.veronica.springbootmetrics.domain.OrderItem
import java.math.BigDecimal
import java.util.UUID

class CreateOrderResponse (
  private val order: Order
) {
  val id: Long = order.id
  val personName: String = order.personName
  val totalAmount: BigDecimal = order.amount!!
  val items: List<CoffeeResponse> = order.items.map { CoffeeResponse(it) }
}

data class CoffeeResponse (
  private val item: OrderItem
) {
  val type: String = item.coffee.type.name
  val quantity: Int = item.quantity
  val price: BigDecimal = item.coffee.price
}