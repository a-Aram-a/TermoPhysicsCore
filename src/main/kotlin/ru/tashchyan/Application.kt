package ru.tashchyan

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.tashchyan.plugins.*

fun main() {
    embeddedServer(Netty, port = 8081, host = "localhost") {
        configureRouting()
        configureSecurity()
        configureSerialization()
    }.start(wait = true)
}
