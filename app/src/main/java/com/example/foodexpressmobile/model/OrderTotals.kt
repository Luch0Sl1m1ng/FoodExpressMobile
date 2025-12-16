package com.example.foodexpressmobile.model

data class OrderTotals(
    val subtotal: Int,
    val netAfterDiscount: Int,
    val tax: Int,
    val total: Int
)
