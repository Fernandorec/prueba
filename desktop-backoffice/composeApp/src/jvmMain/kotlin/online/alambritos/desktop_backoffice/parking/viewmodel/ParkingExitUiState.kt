package online.alambritos.desktop_backoffice.parking.viewmodel

import online.alambritos.desktop_backoffice.parking.model.ParkingSpot
import online.alambritos.desktop_backoffice.parking.model.Ticket

data class VehicleEntry(
    val plate: String,
    val vehicleType: String,
    val entryTime: String
)

data class ParkingExitUiState(
    val activeTickets: List<Ticket> = emptyList(),
    val completedTickets: List<Ticket> = emptyList(),
    val parkingSpots: List<ParkingSpot> = emptyList(),
    val availableSpotCount: Int = 0,
    val feedback: String = "Selecciona un ticket activo para registrar la salida.",
    val registeredVehicles: List<VehicleEntry> = emptyList()
)