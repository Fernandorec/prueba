package online.alambritos.desktop_backoffice.parking.viewmodel

import online.alambritos.desktop_backoffice.parking.model.ParkingSpot

data class ParkingSpotUiState(
    val spots: List<ParkingSpot> = emptyList(),
    val availableSpots: List<ParkingSpot> = emptyList(),
    val feedback: String = "Ingresa el código del espacio a registrar."
)