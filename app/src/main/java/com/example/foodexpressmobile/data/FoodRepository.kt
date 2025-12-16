package com.example.foodexpressmobile.data

import com.example.foodexpressmobile.model.Product
import kotlinx.coroutines.delay

class FoodRepository {

    suspend fun getProducts(): List<Product> {
        delay(500)
        return listOf(
            Product(1, "Hamburguesa Clásica", 8990),
            Product(2, "Papas Fritas Grandes", 3990),
            Product(3, "Coca Cola Mediana", 2290),
            Product(4, "Pizza Personal", 7990),
            Product(5, "Ensalada César", 5490)
        )
    }

    suspend fun sendOrder(
        selected: List<Product>,
        isVip: Boolean
    ): String {
        delay(500)
        return "PED-${System.currentTimeMillis()}"
    }

    suspend fun getExternalAdvice(): String {
        delay(300)
        return "Recuerda siempre confirmar la dirección antes de enviar tu pedido."
    }
}
