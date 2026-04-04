package online.alambritos.desktop_backoffice.parking.viewmodel

data class FareUiState(
    val spotCode: String = "",
    val breakdown: String = "",
    val feedback: String = "Ingresa el código del espacio para calcular la tarifa."
)