package online.alambritos.desktop_backoffice.parking.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import online.alambritos.desktop_backoffice.parking.repository.TicketRepository
import online.alambritos.desktop_backoffice.parking.service.FareCalculatorService

class FareViewModel(
    private val ticketRepository: TicketRepository,
    private val fareCalculatorService: FareCalculatorService
) {

    var uiState by mutableStateOf(FareUiState())
        private set

    fun calculateFare(spotCode: String) {
        if (spotCode.isBlank()) {
            uiState = uiState.copy(
                feedback = "Error: el código del espacio no puede estar vacío."
            )
            return
        }

        val ticket = ticketRepository.findOpenBySpotCode(spotCode).orElse(null)

        if (ticket == null) {
            uiState = uiState.copy(
                spotCode = spotCode,
                breakdown = "",
                feedback = "No se encontró un ticket activo para el espacio \"${spotCode.uppercase()}\"."
            )
            return
        }

        val breakdown = fareCalculatorService.buildBreakdown(ticket)
        val amount = fareCalculatorService.calculate(ticket)

        uiState = FareUiState(
            spotCode = spotCode,
            breakdown = breakdown,
            feedback = "Tarifa calculada para ${ticket.vehiclePlate}: \$%.2f".format(amount)
        )
    }

    fun clearResult() {
        uiState = FareUiState()
    }
}