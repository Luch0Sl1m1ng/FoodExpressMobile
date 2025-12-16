package com.example.foodexpressmobile.data

import com.example.foodexpressmobile.model.OrderTotals
import com.example.foodexpressmobile.model.Product

object OrderCalculator {

    fun calculateSubtotal(products: List<Product>): Int =
        products.sumOf { it.price }

    fun calculateDiscount(subtotal: Int, isVip: Boolean): Int {
        return if (isVip) (subtotal * 0.10).toInt() else 0
    }

    fun calculateTax(amount: Int): Int =
        (amount * 0.19).toInt()

    fun calculateTotal(
        products: List<Product>,
        isVip: Boolean
    ): OrderTotals {
        val subtotal = calculateSubtotal(products)
        val discount = calculateDiscount(subtotal, isVip)
        val net = subtotal - discount
        val tax = calculateTax(net)
        val total = net + tax

        return OrderTotals(
            subtotal = subtotal,
            netAfterDiscount = net,
            tax = tax,
            total = total
        )
    }
}
