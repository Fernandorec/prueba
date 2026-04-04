package online.alambritos.desktop_backoffice.parking

import androidx.compose.runtime.*
import online.alambritos.desktop_backoffice.parking.repository.InMemoryParkingRepository
import online.alambritos.desktop_backoffice.parking.service.ParkingExitService
import online.alambritos.desktop_backoffice.parking.view.ParkingExitScreen
import online.alambritos.desktop_backoffice.parking.view.RegistrarVehiculoScreen
import online.alambritos.desktop_backoffice.parking.viewmodel.ParkingExitViewModel

@Composable
fun ParkingExitApp() {

    val viewModel = remember {
        ParkingExitViewModel(
            parkingExitService = ParkingExitService(
                InMemoryParkingRepository(),
            ),
        )
    }

    var screen by remember { mutableStateOf("EXIT") }

    when (screen) {

        "EXIT" -> ParkingExitScreen(
            uiState = viewModel.uiState,
            onRegisterExit = viewModel::registerExit,
            onGoToRegister = {
                screen = "REGISTER"
            }
        )

        "REGISTER" -> RegistrarVehiculoScreen(
            onRegister = { plate, type ->
                viewModel.registerVehicle(plate, type) // ✅ llama al ViewModel
                screen = "EXIT" // vuelve a la pantalla principal
            },
            onGoBack = {
                screen = "EXIT" // vuelve sin registrar
            }
        )
    }
}