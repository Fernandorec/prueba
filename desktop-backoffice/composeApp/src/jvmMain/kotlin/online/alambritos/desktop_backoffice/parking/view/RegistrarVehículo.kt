package online.alambritos.desktop_backoffice.parking.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RegistrarVehiculoScreen(
    onRegister: (String, String) -> Unit,
    onGoBack: () -> Unit // 🆕 para volver a ParkingExitScreen
) {
    MaterialTheme {

        var plate by remember { mutableStateOf("") }
        var vehicleType by remember { mutableStateOf("CAR") }
        var lastRegistered by remember { mutableStateOf<String?>(null) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .safeContentPadding()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // 🔙 Botón volver
            Button(
                onClick = onGoBack,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Text("← Volver a salidas")
            }

            // Título
            Text(
                text = "Registrar Vehículo",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "HU-02 — Ingresa la placa y selecciona el tipo de vehículo.",
                style = MaterialTheme.typography.bodyLarge
            )

            // Campo de placa
            OutlinedTextField(
                value = plate,
                onValueChange = { plate = it.uppercase() },
                label = { Text("Placa") },
                placeholder = { Text("Ej: ABC-123") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Selección de tipo
            Text(
                text = "Tipo de vehículo",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Botón Carro
                Button(
                    onClick = { vehicleType = "CAR" },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (vehicleType == "CAR")
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (vehicleType == "CAR")
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Text("🚗  Carro")
                }

                // Botón Moto
                Button(
                    onClick = { vehicleType = "MOTORCYCLE" },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (vehicleType == "MOTORCYCLE")
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (vehicleType == "MOTORCYCLE")
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Text("🏍️  Moto")
                }
            }

            // Resumen antes de registrar
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Resumen",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text("Placa: ${if (plate.isEmpty()) "—" else plate}")
                    Text("Tipo: ${if (vehicleType == "CAR") "Carro (Car)" else "Moto (Motorcycle)"}")
                }
            }

            // Botón registrar
            Button(
                onClick = {
                    if (plate.isNotBlank()) {
                        onRegister(plate, vehicleType)
                        lastRegistered = "${ if (vehicleType == "CAR") "🚗 Carro" else "🏍️ Moto" } — $plate"
                        plate = "" // limpia el campo después de registrar
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = plate.isNotBlank()
            ) {
                Text("Registrar vehículo")
            }

            // Confirmación visual después de registrar
            lastRegistered?.let { registered ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "✅ Último registrado",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = registered,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}