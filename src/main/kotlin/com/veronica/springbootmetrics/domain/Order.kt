package com.veronica.springbootmetrics.domain

import java.math.BigDecimal
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "orders")
class Order(
  @Id
  @Column
  val id: Long = Random().nextLong(),

  @Column(name = "person_name")
  val personName: String,

  @Enumerated(EnumType.STRING)
  var status: OrderStatus,

  var amount: BigDecimal? = null,

  @OneToMany(mappedBy = "order")
  val items: MutableList<OrderItem> = mutableListOf(),

  @Column(name = "cancellation_reason")
  var cancellationReason: String? = null
) {

  fun calculateTotalAmount() = apply {
    this.amount = items.map { it.value() }.reduce { acc, value -> acc + value }
  }

  fun addItems(items: List<OrderItem>) = apply { this.items.addAll(items) }

  fun finished() { this.status = OrderStatus.FINISHED }

  fun cancel(reason: String?) {
    this.cancellationReason = reason
    this.status = OrderStatus.CANCELED
  }
}