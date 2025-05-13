class ObserverViewModel: ViewModel() {
    // Observable state
    private val _text: MutableState<String> = mutableStateOf("Hello, Observer!")
    val text: State<String> = _text

    // Method to update the state
    fun updateText(newText: String) {
        _text.value = newText
    }
}

@Composable
fun ObserverComposable(
    viewModel: ObserverViewModel = viewModel()
) {
    // Observes the state changes in ViewModel
    val textState = viewModel.text.value

    // the Text composable is automatically updated when the state changes
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = textState)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                viewModel.updateText("Hello again, Observer!")
            }
        ) {
            Text("Update Text")
        }
    }
}
