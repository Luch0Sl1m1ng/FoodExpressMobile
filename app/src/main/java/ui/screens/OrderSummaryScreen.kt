package com.example.foodexpressmobile.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foodexpressmobile.data.OrderCalculator
import com.example.foodexpressmobile.ui.state.FoodUiState

@Composable
fun OrderSummaryScreen(
    state: FoodUiState,
    onBack: () -> Unit,
    onConfirmOrder: (Boolean) -> Unit
) {
    var isVip by remember { mutableStateOf(false) }

    Scaffold { padding ->
        val totals = OrderCalculator.calculateTotal(
            products = state.cart,
            isVip = isVip
        )

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = onBack) {
                    Text("< Atrás")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Resumen del pedido",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (state.cart.isEmpty()) {
                Text("No hay productos seleccionados.")
            } else {


                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(state.cart) { product ->
                        Text("- ${product.name}: $${product.price}")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Cliente VIP")
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = isVip,
                        onCheckedChange = { isVip = it }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))


                Text("Subtotal: $${totals.subtotal}")
                Text("Neto con descuento: $${totals.netAfterDiscount}")
                Text("IVA (19%): $${totals.tax}")
                Text(
                    text = "TOTAL: $${totals.total}",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(16.dp))


                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onConfirmOrder(isVip) }
                ) {
                    Text("Confirmar pedido")
                }


                if (state.externalAdvice != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Consejo del día: ${state.externalAdvice}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
