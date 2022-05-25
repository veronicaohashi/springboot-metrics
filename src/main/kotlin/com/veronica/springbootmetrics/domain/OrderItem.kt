package com.veronica.springbootmetrics.domain

import java.math.BigDecimal
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "order_item")
class OrderItem(
  @Id
  @Column
  val id: Long = Random().nextLong(),

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "order_id")
  val order: Order,

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "coffee_id")
  val coffee: Coffee,

  val quantity: Int
) {
  fun value(): BigDecimal = coffee.price.multiply(BigDecimal(quantity))
}