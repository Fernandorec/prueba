package online.alambritos.desktop_backoffice

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import online.alambritos.desktop_backoffice.BackofficeApp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Cloud Parking - Backoffice",
    ) {
        BackofficeApp()
    }
}
