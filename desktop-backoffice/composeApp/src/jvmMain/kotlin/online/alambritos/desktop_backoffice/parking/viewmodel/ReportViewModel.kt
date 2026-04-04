package online.alambritos.desktop_backoffice.parking.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import online.alambritos.desktop_backoffice.parking.service.ReportService
import java.time.LocalDate

class ReportViewModel(
    private val reportService: ReportService
) {

    var uiState by mutableStateOf(ReportUiState())
        private set

    fun generateReport(date: LocalDate) {
        val report = reportService.buildDailyReport(date)
        val total = reportService.calculateDailyRevenue(date)

        uiState = ReportUiState(
            report = report,
            totalRevenue = total,
            feedback = "Reporte generado para ${date}."
        )
    }

    fun clearReport() {
        uiState = ReportUiState()
    }
}