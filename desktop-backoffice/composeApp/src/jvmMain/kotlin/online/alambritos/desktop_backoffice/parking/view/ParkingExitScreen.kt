package online.alambritos.desktop_backoffice.parking.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import online.alambritos.desktop_backoffice.parking.model.ParkingSpot
import online.alambritos.desktop_backoffice.parking.model.Ticket
import online.alambritos.desktop_backoffice.parking.viewmodel.ParkingExitUiState
import online.alambritos.desktop_backoffice.parking.viewmodel.VehicleEntry
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.shape.RoundedCornerShape

private val dateTimeFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

@Composable
fun ParkingExitScreen(
    uiState: ParkingExitUiState,
    onRegisterExit: (String) -> Unit,
    onGoToRegister: () -> Unit
) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .safeContentPadding()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            Button(
                onClick = onGoToRegister,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir a registrar vehículo")
            }

            Text(
                text = "Salida de vehiculos",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = "Vista en Kotlin, estado en ViewModel y negocio en Java para que cada capa quede clara.",
                style = MaterialTheme.typography.bodyLarge,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                SummaryCard(
                    title = "Tickets activos",
                    value = uiState.activeTickets.size.toString(),
                    modifier = Modifier.weight(1f),
                )
                SummaryCard(
                    title = "Espacios libres",
                    value = uiState.availableSpotCount.toString(),
                    modifier = Modifier.weight(1f),
                )
            }

            InfoCard(title = "Estado", body = uiState.feedback)

            InfoCard(title = "Espacios") {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    uiState.parkingSpots.forEach { spot ->
                        SpotChip(spot)
                    }
                }
            }

            InfoCard(title = "Tickets activos") {
                if (uiState.activeTickets.isEmpty()) {
                    Text("No hay vehiculos pendientes de salida.")
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        uiState.activeTickets.forEach { ticket ->
                            TicketCard(ticket = ticket) {
                                onRegisterExit(ticket.id)
                            }
                        }
                    }
                }
            }

            // 🆕 SECCIÓN NUEVA — Vehículos ingresados desde HU-02
            InfoCard(title = "Vehículos ingresados (HU-02)") {
                if (uiState.registeredVehicles.isEmpty()) {
                    Text("Aún no se han registrado vehículos.")
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        uiState.registeredVehicles.forEach { entry ->
                            VehicleEntryCard(entry)
                        }
                    }
                }
            }

            InfoCard(title = "Ultimas salidas") {
                if (uiState.completedTickets.isEmpty()) {
                    Text("Aun no hay salidas registradas.")
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        uiState.completedTickets.forEach { ticket ->
                            val exitLabel = ticket.exitTimestamp.formatOrPlaceholder()
                            Text(
                                text = "${ticket.vehiclePlate} salio de ${ticket.parkingSpotCode} a las " +
                                        "$exitLabel (${ticket.calculateStayMinutes()} min)",
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun VehicleEntryCard(entry: VehicleEntry) {
    val icon = if (entry.vehicleType == "CAR") "🚗" else "🏍️"
    val typeLabel = if (entry.vehicleType == "CAR") "Carro (Car)" else "Moto (Motorcycle)"

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "$icon  ${entry.plate}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Tipo: $typeLabel",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Entró: ${entry.entryTime}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun SummaryCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(title, style = MaterialTheme.typography.labelLarge)
            Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun InfoCard(title: String, body: String) {
    InfoCard(title = title) {
        Text(body)
    }
}

@Composable
private fun InfoCard(title: String, content: @Composable () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            content()
        }
    }
}

@Composable
private fun TicketCard(ticket: Ticket, onRegisterExit: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = "${ticket.vehiclePlate} · ${ticket.parkingSpotCode}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Text("Entrada: ${ticket.entryTimestamp.format(dateTimeFormatter)}")
            Button(onClick = onRegisterExit) {
                Text("Registrar salida")
            }
        }
    }
}

@Composable
private fun SpotChip(spot: ParkingSpot) {
    val backgroundColor = if (spot.isOccupied) {
        MaterialTheme.colorScheme.tertiaryContainer
    } else {
        MaterialTheme.colorScheme.primaryContainer
    }
    Card(
        shape = RoundedCornerShape(999.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
    ) {
        Text(
            text = "${spot.code}: ${if (spot.isOccupied) "ocupado" else "libre"}",
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
        )
    }
}

private fun LocalDateTime?.formatOrPlaceholder(): String {
    return this?.format(dateTimeFormatter) ?: "-"
}