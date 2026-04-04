package online.alambritos.desktop_backoffice.parking.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import online.alambritos.desktop_backoffice.parking.viewmodel.ReportUiState
import java.time.LocalDate

@Composable
fun ReportScreen(
    uiState: ReportUiState,
    onGenerateReport: (LocalDate) -> Unit,
    onClear: () -> Unit,
    onGoBack: () -> Unit
) {
    MaterialTheme {

        // Campos de fecha separados para Compose Desktop (no hay DatePicker nativo en todas las versiones)
        var day by remember { mutableStateOf("") }
        var month by remember { mutableStateOf("") }
        var year by remember { mutableStateOf("") }
        var dateError by remember { mutableStateOf<String?>(null) }

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
                text = "Reporte de Ingresos",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "HU-08 — Ingresa una fecha para generar el reporte diario de ingresos.",
                style = MaterialTheme.typography.bodyLarge
            )

            // Selector de fecha manual (día / mes / año)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = day,
                    onValueChange = { if (it.length <= 2) day = it },
                    label = { Text("Día") },
                    placeholder = { Text("01") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = month,
                    onValueChange = { if (it.length <= 2) month = it },
                    label = { Text("Mes") },
                    placeholder = { Text("01") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = year,
                    onValueChange = { if (it.length <= 4) year = it },
                    label = { Text("Año") },
                    placeholder = { Text("2025") },
                    modifier = Modifier.weight(2f),
                    singleLine = true
                )
            }

            dateError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Botón "Hoy" para rellenar la fecha actual rápido
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        val today = LocalDate.now()
                        day   = today.dayOfMonth.toString().padStart(2, '0')
                        month = today.monthValue.toString().padStart(2, '0')
                        year  = today.year.toString()
                        dateError = null
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Text("Hoy")
                }

                Button(
                    onClick = {
                        dateError = null
                        try {
                            val date = LocalDate.of(year.toInt(), month.toInt(), day.toInt())
                            onGenerateReport(date)
                        } catch (e: Exception) {
                            dateError = "Fecha inválida. Verifica día, mes y año."
                        }
                    },
                    modifier = Modifier.weight(2f),
                    enabled = day.isNotBlank() && month.isNotBlank() && year.isNotBlank()
                ) {
                    Text("Generar reporte")
                }

                Button(
                    onClick = {
                        day = ""; month = ""; year = ""
                        dateError = null
                        onClear()
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Text("Limpiar")
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
                    verticalArrangement = Arrangement.spacedBy(6.dp)
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

            if (uiState.report.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Reporte generado — Total: \$%.2f".format(uiState.totalRevenue),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = uiState.report,
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }
    }
}