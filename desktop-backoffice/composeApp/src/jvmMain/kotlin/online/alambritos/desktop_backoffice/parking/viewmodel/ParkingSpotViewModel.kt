package online.alambritos.desktop_backoffice.parking.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import online.alambritos.desktop_backoffice.parking.service.ParkingSpotService

class ParkingSpotViewModel(
    private val parkingSpotService: ParkingSpotService
) {

    var uiState by mutableStateOf(buildUiState())
        private set

    fun registerSpot(code: String) {
        try {
            parkingSpotService.registerSpot(code)
            uiState = buildUiState(
                feedback = "Espacio \"${code.trim().uppercase()}\" registrado correctamente."
            )
        } catch (e: IllegalArgumentException) {
            uiState = buildUiState(
                feedback = "Error: ${e.message}"
            )
        }
    }

    private fun buildUiState(
        feedback: String = "Ingresa el código del espacio a registrar."
    ): ParkingSpotUiState {
        val spots = try {
            parkingSpotService.getAllSpots()
        } catch (e: Exception) {
            println("ERROR en getAllSpots: ${e.message}")
            emptyList()
        }

        val availableSpots = try {
            parkingSpotService.getAvailableSpots()
        } catch (e: Exception) {
            println("ERROR en getAvailableSpots: ${e.message}")
            emptyList()
        }

        return ParkingSpotUiState(
            spots = spots,
            availableSpots = availableSpots,
            feedback = feedback
        )
    }
}