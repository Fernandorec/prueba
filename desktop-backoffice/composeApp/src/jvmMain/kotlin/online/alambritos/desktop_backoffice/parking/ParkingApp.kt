package online.alambritos.desktop_backoffice.parking

import androidx.compose.runtime.*
import online.alambritos.desktop_backoffice.parking.repository.ParkingSpotRepository
import online.alambritos.desktop_backoffice.parking.repository.TicketRepository
import online.alambritos.desktop_backoffice.parking.service.FareCalculatorService
import online.alambritos.desktop_backoffice.parking.service.ParkingSpotService
import online.alambritos.desktop_backoffice.parking.service.ReportService
import online.alambritos.desktop_backoffice.parking.view.FareScreen
import online.alambritos.desktop_backoffice.parking.view.RegisterSpotScreen
import online.alambritos.desktop_backoffice.parking.view.ReportScreen
import online.alambritos.desktop_backoffice.parking.viewmodel.FareViewModel
import online.alambritos.desktop_backoffice.parking.viewmodel.ParkingSpotViewModel
import online.alambritos.desktop_backoffice.parking.viewmodel.ReportViewModel

@Composable
fun ParkingApp() {

    // Repositorios compartidos entre ViewModels
    val spotRepository   = remember { ParkingSpotRepository() }
    val ticketRepository = remember { TicketRepository() }

    // ViewModels con sus servicios
    val spotViewModel = remember {
        ParkingSpotViewModel(
            parkingSpotService = ParkingSpotService(spotRepository)
        )
    }

    val fareViewModel = remember {
        FareViewModel(
            ticketRepository     = ticketRepository,
            fareCalculatorService = FareCalculatorService()
        )
    }

    val reportViewModel = remember {
        ReportViewModel(
            reportService = ReportService(ticketRepository, FareCalculatorService())
        )
    }

    var screen by remember { mutableStateOf("SPOTS") }

    when (screen) {
        "SPOTS" -> RegisterSpotScreen(
            uiState         = spotViewModel.uiState,
            onRegisterSpot  = spotViewModel::registerSpot,
            onGoBack        = { screen = "SPOTS" }
        )
        "FARE" -> FareScreen(
            uiState          = fareViewModel.uiState,
            onCalculateFare  = fareViewModel::calculateFare,
            onClear          = fareViewModel::clearResult,
            onGoBack         = { screen = "SPOTS" }
        )
        "REPORT" -> ReportScreen(
            uiState          = reportViewModel.uiState,
            onGenerateReport = reportViewModel::generateReport,
            onClear          = reportViewModel::clearReport,
            onGoBack         = { screen = "SPOTS" }
        )
    }
}