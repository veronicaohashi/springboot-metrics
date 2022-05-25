package com.veronica.springbootmetrics.infrastructure.repository

import com.veronica.springbootmetrics.domain.Order
import com.veronica.springbootmetrics.domain.OrderItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderItemRepository : JpaRepository<OrderItem, Long>