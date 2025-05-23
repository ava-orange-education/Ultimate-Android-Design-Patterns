package com.example.checkout.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cart.view.CartListItem
import com.example.checkout.model.PaymentMethodEnum
import com.example.checkout.viewmodel.CheckoutViewModel

@Composable
fun CheckoutSummaryScreen(
    viewModel: CheckoutViewModel? = null,
    navgateNext: () -> Unit = {}
) {
    val fullName = viewModel?.fullName?.collectAsStateWithLifecycle()
    val phoneNumber = viewModel?.phoneNumber?.collectAsStateWithLifecycle()
    val streetAddress = viewModel?.streetAddress?.collectAsStateWithLifecycle()
    val postalCodeOrZipCode = viewModel?.postalCodeOrZipCode?.collectAsStateWithLifecycle()
    val city = viewModel?.city?.collectAsStateWithLifecycle()
    val stateOrProvinceOrRegion = viewModel?.stateOrProvinceOrRegion?.collectAsStateWithLifecycle()
    val country = viewModel?.country?.collectAsStateWithLifecycle()

    val paymentMethod = viewModel?.paymentMethod?.collectAsStateWithLifecycle()
    val cardNumber = viewModel?.cardNumber?.collectAsStateWithLifecycle()
    val expirationDate = viewModel?.expirationDate?.collectAsStateWithLifecycle()
    val cvv = viewModel?.cvv?.collectAsStateWithLifecycle()

    val cartItems = viewModel?.cartItems?.collectAsStateWithLifecycle()
        ?: remember { mutableStateOf(emptyList()) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Address",
            style = MaterialTheme.typography.headlineSmall
        )
        Text("Full Name: ${fullName?.value}")
        Text("Phone Number: ${phoneNumber?.value}")
        Text("Street Address: ${streetAddress?.value}")
        Text("City: ${city?.value}")
        Text("Postal Code / ZIP Code: ${postalCodeOrZipCode?.value}")
        Text("State / Province / Region: ${stateOrProvinceOrRegion?.value}")
        Text("Country: ${country?.value}")

        Text("Payment Method : ${paymentMethod?.value}")
        if (paymentMethod?.value == PaymentMethodEnum.CREDIT_CARD.value) {
            Text("Card Number: ${cardNumber?.value}")
            Text("Expiration Date: ${expirationDate?.value}")
            Text("CVV: ${cvv?.value}")
        }

        Text("Payment: ${paymentMethod?.value}")
        cartItems.value.forEach { cartItem ->
            CartListItem(
                cartItem = cartItem,
                showClearIcon = false
            )
        }
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    viewModel?.createOrder()
                    navgateNext()
                },
            ) {
                Text(text = "Confirm Order")
            }
        }
    }
}

@Preview
@Composable
fun CheckoutSummaryScreenPreview() {
    Surface {
        CheckoutSummaryScreen()
    }
}