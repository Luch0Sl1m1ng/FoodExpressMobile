package com.example.foodexpressmobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodexpressmobile.data.FoodRepository
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
            try {
                uiState = uiState.copy(isLoading = true, error = null)
                val products = repository.loadProductsFromApi()
                uiState = uiState.copy(
                    products = products,
                    isLoading = false
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = "Error al cargar productos: ${e.message}"
                )
            }
        }
    }

    fun toggleProductInCart(product: Product) {
        val currentCart = uiState.cart.toMutableList()
        if (currentCart.any { it.id == product.id }) {
            currentCart.removeAll { it.id == product.id }
        } else {
            currentCart.add(product)
        }
        uiState = uiState.copy(cart = currentCart)
    }

    fun confirmOrder(isVip: Boolean) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true, error = null)
                val response = repository.sendOrder(uiState.cart, isVip)
                uiState = uiState.copy(
                    isLoading = false,
                    externalAdvice = response.message
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = "Error al enviar pedido: ${e.message}"
                )
            }
        }
    }
}
