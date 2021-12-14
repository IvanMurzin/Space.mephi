package space.mephi.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import space.mephi.Config
import space.mephi.database.Database
import space.mephi.entities.Credentials
import space.mephi.entities.User
import space.mephi.entities.checkValid
import space.mephi.utils.AuthUtil
import space.mephi.utils.AuthUtil.createToken
import space.mephi.utils.ForbiddenException


fun Application.configureAuthentication() {
    // init jwt authentication logic
    install(Authentication) {
        jwt("auth-jwt") {
            this.realm
            verifier( // init my own verifier
                JWT
                    .require(Algorithm.HMAC256(Config.JWT_SECRET))
                    .withIssuer(Config.JWT_ISSUER)
                    .build()
            )
            validate { credential -> // create validation function
                if (credential.payload.getClaim("email").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }


    routing {

        get("/") {
            call.respond("Here we go again")
        }

        post("/login") { // route to /login
            val user = call.receive<User>()
            user.checkValid() // if user is invalid an exception occurs
            if (Database.findOne(User::email, user.email) != null) // if user already exists
                throw ForbiddenException("Такой пользователь уже существует")
            user.password = AuthUtil.generateHash(user.password) // save password hash in db
            Database.save(user) // else save them
            val token = createToken(user)
            call.respond(hashMapOf("token" to token)) // respond with token
        }

        post("/signin") {  // route to /signin
            val credentials = call.receive<Credentials>()
            credentials.checkValid() // if user is invalid an exception occurs
            val trueUser = Database.findOne(User::email, credentials.email) // if there is no one user with this email
                ?: throw ForbiddenException("Такого пользователя не существует")
            if (!AuthUtil.verifyHash(credentials.password, trueUser.password)) // if wrong password
                throw ForbiddenException("Неверный пароль")
            val token = createToken(trueUser) // create a token with trueUser because the user info is stored in the jwt
            call.respond(hashMapOf("token" to token))
        }
    }
}
