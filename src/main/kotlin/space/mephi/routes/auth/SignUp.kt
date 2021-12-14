package space.mephi.routes

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import space.mephi.database.Database
import space.mephi.entities.User
import space.mephi.entities.checkValid
import space.mephi.utils.AuthUtil
import space.mephi.utils.ForbiddenException

fun Route.post("/signup") { // route to /login
    val user = call.receive<User>()
    user.checkValid() // if user is invalid an exception occurs
    if (Database.findOne(User::email, user.email) != null) // if user already exists
        throw ForbiddenException("Такой пользователь уже существует")
    user.password = AuthUtil.generateHash(user.password) // save password hash in db
    Database.save(user) // else save them
    val token = AuthUtil.createToken(user)
    call.respond(hashMapOf("token" to token)) // respond with token
}