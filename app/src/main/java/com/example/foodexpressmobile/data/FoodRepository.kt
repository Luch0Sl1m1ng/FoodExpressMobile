package com.example.foodexpressmobile.data

import com.example.foodexpressmobile.model.Product
import com.example.foodexpressmobile.network.ApiClients
import com.example.foodexpressmobile.network.OrderItemDto
import com.example.foodexpressmobile.network.OrderRequestDto
import com.example.foodexpressmobile.network.OrderResponseDto

class FoodRepository {
    suspend fun loadProductsFromApi(): List<Product> {
        return ApiClients.foodApi.getProducts()
    }
    suspend fun sendOrder(
        cart: List<Product>,
        isVip: Boolean
    ): OrderResponseDto {

        val items = cart.map { product ->
            OrderItemDto(
                productId = product.id,
                quantity = 1
            )
        }

        val request = OrderRequestDto(
            items = items,
            isVip = isVip
        )

        return ApiClients.foodApi.createOrder(request)
    }


    suspend fun loadExternalAdvice(): String {
        val response = ApiClients.adviceApi.getAdvice()

        return response.slip.advice
    }
}
