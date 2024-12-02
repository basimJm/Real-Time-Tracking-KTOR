package com.dg

import com.dg.di.mainModule
import com.dg.plugins.configureMonitoring
import com.dg.plugins.configureRouting
import com.dg.plugins.configureSecurity
import com.dg.plugins.configureSerialization
import com.dg.plugins.configureSockets
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(Koin) {
        modules(mainModule)
    }
    configureMonitoring()
    configureSockets()
    configureSerialization()
    configureSecurity()
    configureRouting()
}
