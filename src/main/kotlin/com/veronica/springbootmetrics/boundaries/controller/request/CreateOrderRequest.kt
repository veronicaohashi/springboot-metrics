package com.veronica.springbootmetrics.boundaries.controller.request

data class CreateOrderRequest (
  val personName: String,
  val items: List<CoffeeRequest>,
)

data class CoffeeRequest(
  val type: String,
  val quantity: Int
)