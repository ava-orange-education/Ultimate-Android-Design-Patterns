package com.example.checkout.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.checkout.model.PaymentMethodEnum
import com.example.checkout.viewmodel.CheckoutViewModel

@Composable
fun CheckoutAddInfoScreen(
    viewModel: CheckoutViewModel? = null,
    navgateNext: () -> Unit = {}
) {
    val fullName = viewModel?.fullName?.collectAsStateWithLifecycle()
        ?: remember { mutableStateOf("") }
    val phoneNumber = viewModel?.phoneNumber?.collectAsStateWithLifecycle()
        ?: remember { mutableStateOf("") }
    val streetAddress = viewModel?.streetAddress?.collectAsStateWithLifecycle()
        ?: remember { mutableStateOf("") }
    val postalCodeOrZipCode = viewModel?.postalCodeOrZipCode?.collectAsStateWithLifecycle()
        ?: remember { mutableStateOf("") }
    val city = viewModel?.city?.collectAsStateWithLifecycle()
        ?: remember { mutableStateOf("") }
    val stateOrProvinceOrRegion = viewModel?.stateOrProvinceOrRegion?.collectAsStateWithLifecycle()
        ?: remember { mutableStateOf("") }
    val country = viewModel?.country?.collectAsStateWithLifecycle()
        ?: remember { mutableStateOf("") }

    val paymentMethod = viewModel?.paymentMethod?.collectAsStateWithLifecycle()
        ?: remember { mutableStateOf("") }
    val cardNumber = viewModel?.cardNumber?.collectAsStateWithLifecycle()
        ?: remember { mutableStateOf("") }
    val expirationDate = viewModel?.expirationDate?.collectAsStateWithLifecycle()
        ?: remember { mutableStateOf("") }
    val cvv = viewModel?.cvv?.collectAsStateWithLifecycle()
        ?: remember { mutableStateOf("") }

    var isFormValid = fullName.value.isNotEmpty() && phoneNumber.value.isNotEmpty()
            && streetAddress.value.isNotEmpty() && city.value.isNotEmpty()
            && postalCodeOrZipCode.value.isNotEmpty() && stateOrProvinceOrRegion.value.isNotEmpty()
            && country.value.isNotEmpty() && paymentMethod.value.isNotEmpty()
    if (paymentMethod.value == PaymentMethodEnum.CREDIT_CARD.value) {
        isFormValid = isFormValid && viewModel?.validateCardNumber(cardNumber.value) == true
                && viewModel.validateExpirationDate(expirationDate.value) == true
                && viewModel.validateCvv(cvv.value) == true
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Address",
            style = MaterialTheme.typography.headlineMedium
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = fullName.value,
            onValueChange = { viewModel?.updateFullName(it) },
            label = { Text("Full Name") },
            placeholder = { Text("e.g. John Doe") }
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = phoneNumber.value,
            onValueChange = { viewModel?.updatePhoneNumber(it) },
            label = { Text("Phone Number") },
            placeholder = { Text("e.g. +1 123 456 7890") }
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = streetAddress.value,
            onValueChange = { viewModel?.updateStreetAddress(it) },
            label = { Text("Street Address") },
            placeholder = { Text("e.g. 123 Elm Street") }
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = city.value,
            onValueChange = { viewModel?.updateCity(it) },
            label = { Text("City") },
            placeholder = { Text("e.g. New York") }
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = postalCodeOrZipCode.value,
            onValueChange = { viewModel?.updatePostalCodeOrZipCode(it) },
            label = { Text("Postal Code / ZIP Code") },
            placeholder = { Text("e.g. 10001") }
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = stateOrProvinceOrRegion.value,
            onValueChange = { viewModel?.updateStateOrProvinceOrRegion(it) },
            label = { Text("State / Province / Region") },
            placeholder = { Text("e.g. California") }
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = country.value,
            onValueChange = { viewModel?.updateCountry(it) },
            label = { Text("Country") },
            placeholder = { Text("e.g. United States") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Payment Method",
            style = MaterialTheme.typography.headlineMedium
        )
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            PaymentMethodEnum.entries.forEachIndexed { index, item ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = PaymentMethodEnum.entries.size
                    ),
                    onClick = {
                        viewModel?.updatePaymentMethod(item.value)
                    },
                    selected = item.value == paymentMethod.value,
                    label = { Text(item.value) }
                )
            }
        }
        if (paymentMethod.value == PaymentMethodEnum.CREDIT_CARD.value) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = TextFieldValue(
                    text = cardNumber.value,
                    selection = TextRange(cardNumber.value.length)
                ),
                onValueChange = { viewModel?.updateCardNumber(it.text) },
                label = { Text("Card Number") },
                placeholder = { Text("e.g. 1234 5678 9012 3456") },
                isError = viewModel?.validateCardNumber(cardNumber.value) == false,
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = expirationDate.value,
                onValueChange = { viewModel?.updateExpirationDate(it) },
                label = { Text("Expiration Date") },
                placeholder = { Text("e.g. 12/25") },
                isError = viewModel?.validateExpirationDate(expirationDate.value) == false,
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = cvv.value,
                onValueChange = { viewModel?.updateCvv(it) },
                label = { Text("CVV") },
                placeholder = { Text("e.g. 123") },
                isError = viewModel?.validateCvv(cvv.value) == false
            )
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = navgateNext,
                enabled = isFormValid
            ) {
                Text(text = "Next")
            }
        }
    }
}

@Preview
@Composable
fun CheckoutAddInfoScreenPreview() {
    Surface {
        CheckoutAddInfoScreen()
    }
}