package space.mephi.entities

import space.mephi.utils.ForbiddenException
import space.mephi.utils.MyRegexp
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String,
    var password: String,
    val name: String,
    val surname: String,
    val patronymic: String,
    val birthDateUnix: Long,
    val group: String
) : MongoDocument()

fun User.checkValid() {
    if (!Regex(MyRegexp.emailRegex).matches(email)) // check email with my regexp
        throw ForbiddenException("Email введен некорректно")
    if (password.length > 16 || password.length < 6){
        throw ForbiddenException("Пароль должен быть от 6 до 16 символов")
    }
}


