package com.dg.plugins

import com.dg.controller.TrackingController
import com.dg.routes.trackingSocket
import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val trackingController by inject<TrackingController>()
    routing {
        trackingSocket(trackingController)
    }
}
