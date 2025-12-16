package com.example.foodexpressmobile.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodexpressmobile.ui.screens.OrderSummaryScreen
import com.example.foodexpressmobile.ui.screens.ProductListScreen
import com.example.foodexpressmobile.ui.viewmodel.FoodViewModel

@Composable
fun FoodExpressApp(
    viewModel: FoodViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "products"
    ) {
        composable("products") {
            ProductListScreen(
                state = viewModel.uiState,
                onToggleProduct = { product ->
                    viewModel.toggleProductInCart(product)
                },
                onGoToSummary = {
                    navController.navigate("summary")
                },
                onReload = {
                    viewModel.loadProducts()
                }
            )
        }

        composable("summary") {
            OrderSummaryScreen(
                state = viewModel.uiState,
                onBack = { navController.popBackStack() },
                onConfirmOrder = { isVip ->
                    viewModel.confirmOrder(isVip)
                }
            )
        }
    }
}
