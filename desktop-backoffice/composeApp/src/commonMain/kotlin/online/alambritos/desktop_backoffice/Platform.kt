package online.alambritos.desktop_backoffice

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform