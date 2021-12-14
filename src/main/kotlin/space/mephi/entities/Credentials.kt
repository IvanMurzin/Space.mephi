package space.mephi.entities

import kotlinx.serialization.Serializable
import space.mephi.utils.ForbiddenException
import space.mephi.utils.MyRegexp


@Serializable
data class Credentials(
    val email: String,
    val password: String,
) : MongoDocument()

fun Credentials.checkValid() {
    if (!Regex(MyRegexp.emailRegex).matches(email)) // check email with my regexp
        throw ForbiddenException("Email введен некорректно")
    if (password.length > 16 || password.length < 6) {
        throw ForbiddenException("Пароль должен быть от 6 до 16 символов")
    }
}
