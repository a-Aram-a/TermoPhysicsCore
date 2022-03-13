package ru.tashchyan.plugins

import io.ktor.http.*
import io.ktor.server.sessions.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import ru.tashchyan.Auth
import ru.tashchyan.database.DbController
import ru.tashchyan.objects.User

fun Application.configureSecurity() {
    install(Sessions) {
        cookie<User>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    routing {
        post("/auth/register") {
            val params = call.receiveParameters()
            val login = params["login"] ?: ""
            val password = params["password"] ?: ""
            val name = params["name"] ?: ""
            try {
                DbController.createUser(login, password, name)
                call.response.status(HttpStatusCode.OK)
            } catch (e: Exception) {
                call.response.status(HttpStatusCode(400, e.message.toString()))
                return@post;
            }
        }

        post("/auth/login") {
            val params = call.receiveParameters()
            val login = params["login"] ?: ""
            val password = params["password"] ?: ""
            try {
                val user = DbController.getUserByLoginAndPassword(login, password)
                if(user != null) {
                    val token = Auth.generateToken()
                    Auth.addNewSession(token, user)
                    call.response.cookies.append("token", token)
                    call.respond(user)
                } else {
                    throw Exception("Wrong login or password")
                }
            } catch(e: Exception) {
                call.response.status(HttpStatusCode(400, e.message.toString()))
            }
        }
    }
}
