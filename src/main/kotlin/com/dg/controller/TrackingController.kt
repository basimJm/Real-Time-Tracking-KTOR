package com.dg.controller

import com.dg.core.exception.MemberAlreadyExistException
import com.dg.model.Member
import com.dg.model.User
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class TrackingController() {
    private val members = ConcurrentHashMap<String, Member>()

    fun onJoin(userName: String, sessionId: String, socket: WebSocketSession) {
        if (members.contains(userName)) {
            throw MemberAlreadyExistException()
        }
        members[userName] =
            Member(userName = userName, sessionId = sessionId, socketSession = socket)
    }

    suspend fun emitEvent(
        role: String,
        actionType: String,
        userName: String,
        lat: Double,
        long: Double,
    ) {
        val user =
            User(role = role, actionType = actionType, userName = userName, lat = lat, long = long)
        val userJson = Json.encodeToString(user)

        members.values.forEach { member ->
            member.socketSession.send(Frame.Text(userJson))
        }
    }

    suspend fun tryDisconnect(userName: String) {
        members[userName]?.socketSession?.close()
        if (members.containsKey(userName)) {
            members.remove(userName)
        }

    }
}