package online.alambritos.desktop_backoffice.parking.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import online.alambritos.desktop_backoffice.parking.service.ParkingExitService
import online.alambritos.desktop_backoffice.parking.model.VehicleFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ParkingExitViewModel(
    private val parkingExitService: ParkingExitService,
) {
    // 🆕 vehicleEntries PRIMERO, antes de buildUiState()
    private val vehicleEntries = mutableListOf<VehicleEntry>()

    var uiState by mutableStateOf(buildUiState())
        private set

    fun registerExit(ticketId: String) {
        val updatedTicket = parkingExitService.registerExit(ticketId)
        val feedback = buildString {
            append("Salida registrada para ${updatedTicket.vehiclePlate}. ")
            append("Tiempo de permanencia: ${updatedTicket.calculateStayMinutes()} min. ")
            append("Espacio ${updatedTicket.parkingSpotCode} liberado.")
        }
        uiState = buildUiState(feedback)
    }

    fun registerVehicle(plate: String, type: String) {
        val vehicle = VehicleFactory.create(plate, type)
        println("Instancia creada: $vehicle")

        val entry = VehicleEntry(
            plate = vehicle.plate,
            vehicleType = vehicle.getType(),
            entryTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        )

        vehicleEntries.add(entry)
        uiState = buildUiState(
            feedback = "Vehículo ${vehicle.plate} registrado como ${vehicle.getType()}"
        )
    }

    private fun buildUiState(
        feedback: String = "Selecciona un ticket activo para registrar la salida.",
    ): ParkingExitUiState {
        val activeTickets = try {
            parkingExitService.getActiveTickets() ?: emptyList()
        } catch (e: Exception) {
            println("ERROR en activeTickets: ${e.message}")
            emptyList()
        }

        val completedTickets = try {
            parkingExitService.getCompletedTickets() ?: emptyList()
        } catch (e: Exception) {
            println("ERROR en completedTickets: ${e.message}")
            emptyList()
        }

        val parkingSpots = try {
            parkingExitService.getParkingSpots() ?: emptyList()
        } catch (e: Exception) {
            println("ERROR en parkingSpots: ${e.message}")
            emptyList()
        }

        return ParkingExitUiState(
            activeTickets = activeTickets,
            completedTickets = completedTickets,
            parkingSpots = parkingSpots,
            availableSpotCount = parkingExitService.getAvailableSpotCount(),
            feedback = feedback,
            registeredVehicles = ArrayList(vehicleEntries ?: mutableListOf())
        )
    }
}