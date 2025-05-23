package com.example.authentication.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.authentication.viewmodel.AuthContract

@Composable
fun AuthScreen(
    uiState: AuthUiState,
    contract: AuthContract? = null,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (uiState.isLoggedIn) {
            Image(
                imageVector = Icons.Outlined.Person,
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Text(
                text = "Welcome, ${uiState.username}!",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { contract?.logout(uiState.username) }) {
                Text("Logout")
            }
        } else LoginScreen(contract)
    }
}

@Composable
fun LoginScreen(
    contract: AuthContract? = null
) {
//    var inputUsername by remember { mutableStateOf("") }
//    var inputPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
//        TextField(
//            value = inputUsername,
//            onValueChange = { inputUsername = it },
//            label = { Text("Username") })
//        TextField(
//            value = inputPassword,
//            onValueChange = { inputPassword = it },
//            label = { Text("Password") })
//        Button(onClick = { contract?.login(inputUsername, inputPassword) }) {
//            Text("Login")
//        }
//        Text("Please enter your credentials.")
        val johnDoe = "john_doe" to "John Doe"
        val janeDoe = "jane_doe" to "Jane Doe"
        val users = listOf(johnDoe, janeDoe)
        users.forEach {
            Button(
                onClick = { contract?.login(it.first, it.first) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text("Login as ${it.second}")
            }
        }
    }
}

class LoggedInProvider : PreviewParameterProvider<Boolean> {
    override val values = listOf(false, true).asSequence()
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview(
    @PreviewParameter(LoggedInProvider::class) isLoggedIn: Boolean
) {
    AuthScreen(
        uiState = AuthUiState(
            username = "John",
            isLoggedIn = isLoggedIn
        )
    )
}

