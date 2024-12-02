package com.dg.plugins

import com.dg.model.session.TrackingSession
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCallPipeline.ApplicationPhase.Plugins
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import io.ktor.util.generateNonce

fun Application.configureSecurity() {
    install(Sessions) {
        cookie<TrackingSession>("SESSIONS")
    }
    intercept(Plugins) {
        if (call.sessions.get<TrackingSession>() == null) {
            val username = call.parameters["username"] ?: "Guest"
            call.sessions.set(TrackingSession(username, generateNonce()))
        }
    }
}
