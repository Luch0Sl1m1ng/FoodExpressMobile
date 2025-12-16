package com.example.foodexpressmobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodexpressmobile.data.FoodRepository
import com.example.foodexpressmobile.data.OrderCalculator
import com.example.foodexpressmobile.model.Product
import com.example.foodexpressmobile.ui.state.FoodUiState
import kotlinx.coroutines.launch

class FoodViewModel(
    private val repository: FoodRepository = FoodRepository()
) : ViewModel() {

    var uiState by mutableStateOf(FoodUiState())
        private set

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            try {
                val products = repository.getProducts()
                uiState = uiState.copy(
                    isLoading = false,
                    products = products
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = "Error al cargar el menú: ${e.message}"
                )
            }
        }
    }

    fun toggleProductInCart(product: Product) {
        val currentCart = uiState.cart.toMutableList()
        val index = currentCart.indexOfFirst { it.id == product.id }

        if (index >= 0) {
            currentCart.removeAt(index)
        } else {
            currentCart.add(product)
        }

        uiState = uiState.copy(cart = currentCart)
    }

    fun confirmOrder(isVip: Boolean) {
        viewModelScope.launch {
            if (uiState.cart.isEmpty()) return@launch

            uiState = uiState.copy(isLoading = true, error = null)

            try {
                val orderId = repository.sendOrder(uiState.cart, isVip)
                val advice = repository.getExternalAdvice()
                val totals = OrderCalculator.calculateTotal(uiState.cart, isVip)

                uiState = uiState.copy(
                    isLoading = false,
                    lastOrderId = orderId,
                    lastOrderTotals = totals,
                    externalAdvice = advice
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = "Error al confirmar el pedido: ${e.message}"
                )
            }
        }
    }
}
