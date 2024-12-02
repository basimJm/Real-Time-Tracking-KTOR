package com.dg.routes

import com.dg.controller.TrackingController
import com.dg.core.exception.MemberAlreadyExistException
import com.dg.model.User
import com.dg.model.session.TrackingSession
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.json.Json

fun Route.trackingSocket(trackingController: TrackingController) {
    webSocket("/tracking-socket") {
        val session = call.sessions.get<TrackingSession>()
        if (session == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session"))
            return@webSocket
        }
        try {
            trackingController.onJoin(
                userName = session.userName,
                sessionId = session.sessionId,
                socket = this
            )
            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    val receivedText = frame.readText()
                    val event = Json.decodeFromString<User>(receivedText)

                    trackingController.emitEvent(
                        userName = session.userName,
                        actionType = event.actionType,
                        lat = event.lat,
                        long = event.long,
                        role = event.role
                    )
                }
            }

        } catch (e: MemberAlreadyExistException) {
            call.respond(HttpStatusCode.Conflict)
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            trackingController.tryDisconnect(session.userName)
        }
    }
}

