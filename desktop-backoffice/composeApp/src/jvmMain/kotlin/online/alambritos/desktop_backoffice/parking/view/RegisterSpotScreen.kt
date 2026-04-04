package online.alambritos.desktop_backoffice.parking.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import online.alambritos.desktop_backoffice.parking.model.ParkingSpot
import online.alambritos.desktop_backoffice.parking.viewmodel.ParkingSpotUiState

@Composable
fun RegisterSpotScreen(
    uiState: ParkingSpotUiState,
    onRegisterSpot: (String) -> Unit,
    onGoBack: () -> Unit
) {
    MaterialTheme {

        var code by remember { mutableStateOf("") }
        var lastRegistered by remember { mutableStateOf<String?>(null) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .safeContentPadding()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Button(
                onClick = onGoBack,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Text("← Volver")
            }

            Text(
                text = "Registrar Espacio",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "HU-01 — Ingresa el código del espacio de parqueo a registrar.",
                style = MaterialTheme.typography.bodyLarge
            )

            OutlinedTextField(
                value = code,
                onValueChange = { code = it.uppercase() },
                label = { Text("Código del espacio") },
                placeholder = { Text("Ej: A-01, B-12") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

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
                    Text("Código: ${if (code.isEmpty()) "—" else code}")
                }
            }

            Button(
                onClick = {
                    if (code.isNotBlank()) {
                        onRegisterSpot(code)
                        lastRegistered = code.trim().uppercase()
                        code = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = code.isNotBlank()
            ) {
                Text("Registrar espacio")
            }

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
                            text = "Espacio: $registered",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Estado",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = uiState.feedback,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Espacios registrados (${uiState.spots.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    if (uiState.spots.isEmpty()) {
                        Text("Aún no hay espacios registrados.")
                    } else {
                        androidx.compose.foundation.layout.FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            uiState.spots.forEach { spot ->
                                SpotChip(spot)
                            }
                        }
                    }
                }
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
