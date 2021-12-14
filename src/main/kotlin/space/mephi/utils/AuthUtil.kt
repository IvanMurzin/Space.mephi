package space.mephi.utils

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate
import space.mephi.Config
import space.mephi.entities.User
import java.util.*

object AuthUtil {
    private val algorithm = Algorithm.HMAC256(Config.JWT_SECRET)
    private val bCrypt = BCrypt.withDefaults()
    private val bVerifier = BCrypt.verifyer()

    fun createToken(user: User): String = JWT.create() // create jwt token that stores email, name and surname
        .withIssuer(Config.JWT_ISSUER)
        .withSubject(user.id)
        .withClaim("email", user.email)
        .withClaim("name", user.name)
        .withClaim("surname", user.surname)
        .withExpiresAt(Date(System.currentTimeMillis() + Config.JWT_LIFETIME_H.toInt() * 3600000))
        .sign(algorithm)


    fun generateHash(source: String): String =
         bCrypt.hashToString(Config.BCRYPT_COAST.toInt(), "${Config.BCRYPT_SALT}+$source".toCharArray())


    fun verifyHash(candidate: String, hash: String): Boolean {
        return try {
            bVerifier.verify("${Config.BCRYPT_SALT}+$candidate".toCharArray(), hash).verified
        } catch (e: Exception) {
            false
        }
    }
}