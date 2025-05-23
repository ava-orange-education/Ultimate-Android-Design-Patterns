@Composable
fun BiometricAuthentication(
    onAuthSuccess: () -> Unit,
    onAuthError: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Get the main executor and create a BiometricPrompt instance
    val executor = ContextCompat.getMainExecutor(context)
    val biometricPrompt = remember {
        BiometricPrompt(
            context as FragmentActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onAuthSuccess()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onAuthError()
                }
            }
        )
    }

    // Create a PromptInfo instance to configure the biometric prompt
    val promptInfo = remember {
        PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Verify your identity")
            .setNegativeButtonText("Cancel")
            .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            .build()
    }

    // Use a DisposableEffect to observe the lifecycle and authenticate when the activity resumes
    // This ensures that the biometric prompt is shown when the activity is in the foreground
    // and is automatically dismissed when the activity is paused or destroyed
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                biometricPrompt.authenticate(promptInfo)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

// Usage

val biometricAuth = BiometricAuthentication(
    onAuthSuccess = {
        // Notify the user the message "Authentication successful!"
    },
    onAuthError = {
        // Notify the user the message "Authentication failed."
    }
)
