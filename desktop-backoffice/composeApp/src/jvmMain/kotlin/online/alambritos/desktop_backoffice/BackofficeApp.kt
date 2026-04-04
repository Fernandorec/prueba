package online.alambritos.desktop_backoffice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private enum class BackofficeSection(val label: String) {
    DASHBOARD("Resumen"),
    REGISTER_VEHICLE("Registrar Vehiculo"),
    ENTRY("Registrar Entrada"),
    EXIT("Registrar Salida"),
    SPOTS("Espacios"),
    REPORTS("Reportes")
}

private data class SpotRow(
    val code: String,
    val type: String,
    val status: String
)

private data class TicketRow(
    val ticketId: String,
    val plate: String,
    val spotCode: String,
    val entryAt: String
)

private data class PaymentRow(
    val ticketId: String,
    val plate: String,
    val amount: String,
    val exitAt: String
)

@Composable
fun BackofficeApp() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            BackofficeShell()
        }
    }
}

@Composable
private fun BackofficeShell() {
    var section by remember { mutableStateOf(BackofficeSection.DASHBOARD) }

    val spotRows = remember { mutableStateListOf<SpotRow>() }
    val activeTickets = remember { mutableStateListOf<TicketRow>() }
    val payments = remember { mutableStateListOf<PaymentRow>() }

    Row(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(240.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Cloud Parking", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            BackofficeSection.entries.forEach { item ->
                Button(onClick = { section = item }, modifier = Modifier.fillMaxWidth()) {
                    Text(item.label)
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (section) {
                BackofficeSection.DASHBOARD -> DashboardView(
                    spots = spotRows,
                    activeTickets = activeTickets,
                    payments = payments
                )
                BackofficeSection.REGISTER_VEHICLE -> RegisterVehicleView { _, _ ->
                }
                BackofficeSection.ENTRY -> RegisterEntryView { _, _ ->
                }
                BackofficeSection.EXIT -> RegisterExitView { _ ->
                }
                BackofficeSection.SPOTS -> ParkingSpotsView(
                    spots = spotRows,
                    onCreateSpot = { _, _ ->
                    }
                )
                BackofficeSection.REPORTS -> ReportsView(payments)
            }
        }
    }
}

@Composable
private fun DashboardView(
    spots: List<SpotRow>,
    activeTickets: List<TicketRow>,
    payments: List<PaymentRow>
) {
    Text("Resumen operativo", style = MaterialTheme.typography.headlineMedium)
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        SummaryCard("Espacios", spots.size.toString())
        SummaryCard("Tickets activos", activeTickets.size.toString())
        SummaryCard("Cobros", payments.size.toString())
    }
}

@Composable
private fun RegisterVehicleView(onRegister: (String, String) -> Unit) {
    Text("Registrar Vehiculo", style = MaterialTheme.typography.headlineMedium)
    var plate by remember { mutableStateOf("") }
    var vehicleType by remember { mutableStateOf("CAR") }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = plate,
                onValueChange = { plate = it.uppercase() },
                label = { Text("Placa") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = vehicleType,
                onValueChange = { vehicleType = it.uppercase() },
                label = { Text("Tipo (CAR/MOTORCYCLE)") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = { onRegister(plate, vehicleType) }, enabled = plate.isNotBlank()) {
                Text("Registrar")
            }
        }
    }
}

@Composable
private fun RegisterEntryView(onRegister: (String, String) -> Unit) {
    Text("Registrar Entrada", style = MaterialTheme.typography.headlineMedium)
    var plate by remember { mutableStateOf("") }
    var vehicleType by remember { mutableStateOf("CAR") }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = plate,
                onValueChange = { plate = it.uppercase() },
                label = { Text("Placa") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = vehicleType,
                onValueChange = { vehicleType = it.uppercase() },
                label = { Text("Tipo (CAR/MOTORCYCLE)") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = { onRegister(plate, vehicleType) }, enabled = plate.isNotBlank()) {
                Text("Registrar entrada")
            }
        }
    }
}

@Composable
private fun RegisterExitView(onRegisterExit: (String) -> Unit) {
    Text("Registrar Salida", style = MaterialTheme.typography.headlineMedium)
    var ticketId by remember { mutableStateOf("") }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = ticketId,
                onValueChange = { ticketId = it },
                label = { Text("Ticket ID") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = { onRegisterExit(ticketId) }, enabled = ticketId.isNotBlank()) {
                Text("Registrar salida")
            }
        }
    }
}

@Composable
private fun ParkingSpotsView(spots: List<SpotRow>, onCreateSpot: (String, String) -> Unit) {
    Text("Gestion de espacios", style = MaterialTheme.typography.headlineMedium)
    var code by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("STANDARD") }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = code,
                onValueChange = { code = it.uppercase() },
                label = { Text("Codigo") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = type,
                onValueChange = { type = it.uppercase() },
                label = { Text("Tipo (STANDARD/COMPACT/MOTORCYCLE)") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = { onCreateSpot(code, type) }, enabled = code.isNotBlank()) {
                Text("Crear espacio")
            }
        }
    }

    Spacer(modifier = Modifier.width(8.dp))

    if (spots.isEmpty()) {
        Text("Sin espacios registrados")
    } else {
        spots.forEach { spot ->
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text(spot.code, fontWeight = FontWeight.Bold)
                        Text("Tipo: ${spot.type}")
                    }
                    Text(spot.status)
                }
            }
        }
    }
}

@Composable
private fun ReportsView(payments: List<PaymentRow>) {
    Text("Reportes", style = MaterialTheme.typography.headlineMedium)
    val now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Fecha: $now")
            Text("Cobros del dia: ${payments.size}")
        }
    }

    if (payments.isEmpty()) {
        Text("Sin cobros registrados")
    } else {
        payments.forEach { payment ->
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Ticket: ${payment.ticketId}")
                    Text("Placa: ${payment.plate}")
                    Text("Monto: ${payment.amount}")
                    Text("Salida: ${payment.exitAt}")
                }
            }
        }
    }
}

@Composable
private fun SummaryCard(title: String, value: String) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(title, style = MaterialTheme.typography.labelLarge)
            Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
    }
}
