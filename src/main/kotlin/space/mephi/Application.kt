package space.mephi

import io.ktor.application.*
import space.mephi.modules.configureCors
import space.mephi.modules.configureSerialization
import space.mephi.modules.configureStatusPages
import space.mephi.routes.configureAuthentication
import space.mephi.routes.configureChats

// start server with Netty engine and configuration form application.conf
fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

// init app modules
// this method is called when the application starts
fun Application.module() {
    configureSerialization()
    configureStatusPages()
    configureCors()
    configureAuthentication()
    configureChats()
}


