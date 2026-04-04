package online.alambritos.desktop_backoffice.parking.viewmodel

data class ReportUiState(
    val report: String = "",
    val totalRevenue: Double = 0.0,
    val feedback: String = "Selecciona una fecha para generar el reporte."
)