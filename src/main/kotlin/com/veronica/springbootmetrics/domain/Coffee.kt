package com.veronica.springbootmetrics.domain

import java.math.BigDecimal
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "coffee")
class Coffee (
  @Id
  @Column
  val id: Long = Random().nextLong(),

  @Enumerated(EnumType.STRING)
  val type: CoffeeType,

  val price: BigDecimal
)