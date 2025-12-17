package com.example.foodexpressmobile.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foodexpressmobile.model.Product
import com.example.foodexpressmobile.ui.state.FoodUiState

@Composable
fun ProductListScreen(
    state: FoodUiState,
    onToggleProduct: (Product) -> Unit,
    onGoToSummary: () -> Unit,
    onReload: () -> Unit
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "FoodExpress - Menú",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Seleccionados: ${state.cart.size}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (state.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            state.error?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                TextButton(
                    onClick = onReload,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text("Reintentar")
                }
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.products) { product ->
                    val isInCart = state.cart.any { it.id == product.id }
                    ProductItem(
                        product = product,
                        isSelected = isInCart,
                        onClick = { onToggleProduct(product) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                onClick = onGoToSummary,
                enabled = state.cart.isNotEmpty()
            ) {
                Text("Ver resumen (${state.cart.size})")
            }
        }
    }
}

@Composable
private fun ProductItem(
    product: Product,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "$${product.price}", style = MaterialTheme.typography.bodyMedium)
            }
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onClick() }
            )
        }
    }
}
