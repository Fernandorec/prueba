package online.alambritos.desktop_backoffice.persistence

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.Persistence
import java.net.URI
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

object JpaProvider {
    private const val UNIT_NAME = "desktop-backoffice"
    private val entityManagerFactory: EntityManagerFactory by lazy {
        Persistence.createEntityManagerFactory(UNIT_NAME, buildProperties())
    }

    fun createEntityManager(): EntityManager = entityManagerFactory.createEntityManager()

    private fun buildProperties(): Map<String, Any> {
        val props = mutableMapOf<String, Any>()
        val env = System.getenv()

        val jdbcUrl = env["SPRING_DATASOURCE_URL"] ?: buildJdbcFromDatabaseUrl(env["DATABASE_URL"])
        val username = env["SPRING_DATASOURCE_USERNAME"]
        val password = env["SPRING_DATASOURCE_PASSWORD"]

        if (!jdbcUrl.isNullOrBlank()) {
            props["jakarta.persistence.jdbc.url"] = jdbcUrl
        }
        if (!username.isNullOrBlank()) {
            props["jakarta.persistence.jdbc.user"] = username
        }
        if (!password.isNullOrBlank()) {
            props["jakarta.persistence.jdbc.password"] = password
        }

        props["jakarta.persistence.jdbc.driver"] = "org.postgresql.Driver"
        props["hibernate.hbm2ddl.auto"] = "update"
        props["hibernate.dialect"] = "org.hibernate.dialect.PostgreSQLDialect"
        props["hibernate.show_sql"] = "false"
        props["hibernate.format_sql"] = "false"

        return props
    }

    private fun buildJdbcFromDatabaseUrl(databaseUrl: String?): String? {
        if (databaseUrl.isNullOrBlank()) {
            return null
        }
        return try {
            val uri = URI.create(databaseUrl)
            if (uri.scheme != "postgres" && uri.scheme != "postgresql") {
                return null
            }
            val host = uri.host ?: return null
            val port = if (uri.port > 0) ":${uri.port}" else ""
            val path = uri.path ?: return null
            val query = uri.query

            val jdbc = StringBuilder("jdbc:postgresql://")
                .append(host)
                .append(port)
                .append(path)

            if (!query.isNullOrBlank()) {
                jdbc.append("?").append(query)
            }

            applyUserInfo(uri.userInfo)

            jdbc.toString()
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    private fun applyUserInfo(userInfo: String?) {
        if (userInfo.isNullOrBlank()) return
        val parts = userInfo.split(":", limit = 2)
        val username = decode(parts[0])
        val password = if (parts.size > 1) decode(parts[1]) else null
        if (!username.isNullOrBlank()) {
            System.setProperty("SPRING_DATASOURCE_USERNAME", username)
        }
        if (!password.isNullOrBlank()) {
            System.setProperty("SPRING_DATASOURCE_PASSWORD", password)
        }
    }

    private fun decode(value: String?): String? =
        value?.let { URLDecoder.decode(it, StandardCharsets.UTF_8) }
}
