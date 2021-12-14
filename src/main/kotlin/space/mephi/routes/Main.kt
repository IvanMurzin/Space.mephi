package space.mephi.routes

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*

fun Application.configureChats() {
    routing {
        authenticate("auth-jwt") {

        }
    }
}