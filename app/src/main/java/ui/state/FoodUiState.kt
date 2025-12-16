package com.example.foodexpressmobile.ui.state

import com.example.foodexpressmobile.model.OrderTotals
import com.example.foodexpressmobile.model.Product

data class FoodUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val products: List<Product> = emptyList(),
    val cart: List<Product> = emptyList(),
    val lastOrderTotals: OrderTotals? = null,
    val lastOrderId: String? = null,
    val externalAdvice: String? = null
)
