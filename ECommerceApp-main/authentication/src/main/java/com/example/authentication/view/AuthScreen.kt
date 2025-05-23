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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.authentication.viewmodel.AuthViewModel

@Composable
fun AuthScreen() {
    val viewModel: AuthViewModel = hiltViewModel()
    val authToken by viewModel.authToken.collectAsStateWithLifecycle()
    val username by viewModel.username.collectAsStateWithLifecycle()

    var inputUsername by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (authToken != null && username != null) {
            Image(
                imageVector = Icons.Outlined.Person,
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Text(
                text = "Welcome, ${username!!}!",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.logout(username!!) }) {
                Text("Logout")
            }
        } else {
            TextField(value = inputUsername, onValueChange = { inputUsername = it }, label = { Text("Username") })
            TextField(value = inputPassword, onValueChange = { inputPassword = it }, label = { Text("Password") })
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.login(inputUsername, inputPassword) }) {
                Text("Login")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Please enter your credentials.")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    AuthScreen()
}

