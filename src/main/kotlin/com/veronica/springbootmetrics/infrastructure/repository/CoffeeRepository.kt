package com.veronica.springbootmetrics.infrastructure.repository

import com.veronica.springbootmetrics.domain.Coffee
import com.veronica.springbootmetrics.domain.CoffeeType
import com.veronica.springbootmetrics.domain.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CoffeeRepository : JpaRepository<Coffee, Long> {
  fun findByType(type: CoffeeType): Coffee?
}