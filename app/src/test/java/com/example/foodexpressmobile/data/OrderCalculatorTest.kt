package com.example.foodexpressmobile.data

import com.example.foodexpressmobile.model.Product
import org.junit.Assert.assertEquals
import org.junit.Test

class OrderCalculatorTest {

    @Test
    fun calculateTotal_sinVip_calculaIvaCorrectamente() {
        val products = listOf(
            Product(1, "Producto A", 10_000),
            Product(2, "Producto B", 5_000)
        )

        val totals = OrderCalculator.calculateTotal(
            products = products,
            isVip = false
        )

        assertEquals(15_000, totals.subtotal)
        assertEquals(15_000, totals.netAfterDiscount)
        assertEquals(2_850, totals.tax)      // 19% de 15000
        assertEquals(17_850, totals.total)
    }

    @Test
    fun calculateTotal_conVip_aplicaDescuento10Porciento() {
        val products = listOf(
            Product(1, "Producto A", 10_000),
            Product(2, "Producto B", 5_000)
        )

        val totals = OrderCalculator.calculateTotal(
            products = products,
            isVip = true
        )

        assertEquals(15_000, totals.subtotal)
        assertEquals(13_500, totals.netAfterDiscount) // 10% de descuento
        assertEquals(2_565, totals.tax)               // 19% de 13500
        assertEquals(16_065, totals.total)
    }

    @Test
    fun calculateTotal_carritoVacio_todoCero() {
        val totals = OrderCalculator.calculateTotal(
            products = emptyList(),
            isVip = false
        )

        assertEquals(0, totals.subtotal)
        assertEquals(0, totals.netAfterDiscount)
        assertEquals(0, totals.tax)
        assertEquals(0, totals.total)
    }
}
