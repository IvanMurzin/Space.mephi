package space.mephi

import space.mephi.entities.MongoDocument
import space.mephi.entities.User
import space.mephi.utils.MyRegexp
import kotlin.reflect.KProperty1
import kotlin.test.Test

class ApplicationTest {
    @Test
    fun testRoot() {
        println(Regex(MyRegexp.emailRegex).matches("lala"))
    }


}